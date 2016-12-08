package com.admin1.purchaseorder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@RestController
@RequestMapping(value = "/api/v1/order")
public class PurchaseOrderController {
	
	@Autowired
	PurchaseOrderRepository purchaseOrderRepository;
	/**
	 * http://localhost:8080/api/v1/order/purchase?userName=JoeO&qty=1&showId=123
	 * @param servletRequest
	 * @param userName
	 * @param qty
	 * @param showId
	 * @return
	 */
	@RequestMapping(value= "/purchase", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
	  public PurchaseOrder purchase(HttpServletRequest servletRequest,@RequestParam(value="userName")String userName,
                 @RequestParam(value = "qty") Long qty,@RequestParam(value = "showId") Long showId) {
	
		PurchaseOrder order=purchaseOrderRepository.save(new PurchaseOrder(userName,qty,showId,OrderStatus.COMPLETE));
		return order;
		
	}
	/**
	 * Cancels order
	 * http://localhost:8080/api/v1/order/cancelAll?transactionId=7&userName=JoeO&qty=5&showId=123
	 * @param servletRequest
	 * @param transactionId
	 * @param userName
	 * @param qty
	 * @param showId
	 * @return
	 */
	@RequestMapping(value= "/cancelAll", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
	  public PurchaseOrder cancelAll(HttpServletRequest servletRequest,@RequestParam(value="transactionId")Long transactionId,@RequestParam(value="userName")String userName,
               @RequestParam(value = "qty") Long qty,@RequestParam(value = "showId") Long showId) {
		PurchaseOrder order=purchaseOrderRepository.findOne(transactionId);
		order.setQty(0L);
		order.setStatus(OrderStatus.CANCELLED);
		PurchaseOrder cancelledOrder=purchaseOrderRepository.save(order);
		
		return cancelledOrder;
		
	}
	/**
	 * Partial order cancelled
	 * http://localhost:8080/api/v1/order/cancel?transactionId=7&userName=JoeO&qty=5&showId=123
	 * @param servletRequest
	 * @param transactionId
	 * @param userName
	 * @param qty
	 * @param showId
	 * @return
	 * need to do in tranactions and add rollback
	 */
	@RequestMapping(value= "/cancel", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
	  public PurchaseOrder cancel(HttpServletRequest servletRequest,@RequestParam(value="transactionId")Long transactionId,@RequestParam(value="userName")String userName,
             @RequestParam(value = "qty") Long qty,@RequestParam(value = "showId") Long showId) {
		PurchaseOrder order=purchaseOrderRepository.findOne(transactionId);
		if(qty<=order.getQty()&&qty>0){
		order.setQty(qty);
		order.setStatus(OrderStatus.MODIFIED);
		PurchaseOrder modifiedOrder=purchaseOrderRepository.save(order);
		return modifiedOrder;
		}else{
			PurchaseOrder errorOrder=new PurchaseOrder();
			errorOrder.setStatus(OrderStatus.ERROR);
			errorOrder.setMsg("Qty can not be greater than ordered Qty or less than 0 try again");
			return errorOrder;
		}
		
		
		
	}
	/**
	 * test total with different quantities http://localhost:8080/api/v1/order/exchange?transactionId=3&userName=JoeO&qty=4&showIdFrom=456&showIdTo=789
	 * test duplicate shows http://localhost:8080/api/v1/order/exchange?transactionId=3&userName=JoeO&qty=4&showIdFrom=456&showIdTo=456
	 * @param servletRequest
	 * @param transactionId
	 * @param userName
	 * @param qty
	 * @param showIdFrom
	 * @param showIdTo
	 * @return
	 * need to do in tranactions and add rollback
	 */
	@RequestMapping(value= "/exchange", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
	  public List<PurchaseOrder> exchange(HttpServletRequest servletRequest,@RequestParam(value="transactionId")Long transactionId,@RequestParam(value="userName")String userName,
             @RequestParam(value = "qty") Long qty,@RequestParam(value = "showIdFrom") Long showIdFrom,@RequestParam(value = "showIdTo") Long showIdTo) {
		//PurchaseOrder order=new PurchaseOrder(userName,qty,showIdFrom,OrderStatus.MODIFIED);
		List<PurchaseOrder> ordersList=new ArrayList<PurchaseOrder>();
		PurchaseOrder order=purchaseOrderRepository.findOne(transactionId);
			if(!showIdFrom.equals(showIdTo)){
		if(qty<order.getQty()&&!showIdFrom.equals(showIdTo)){
			Long newQty=order.getQty()-qty;
			order.setQty(newQty);
			order.setStatus(OrderStatus.MODIFIED);
			PurchaseOrder modifiedOrder=purchaseOrderRepository.save(order);
			ordersList.add(modifiedOrder);
			PurchaseOrder neworder=new PurchaseOrder(userName,qty,showIdTo,OrderStatus.COMPLETE);
			ordersList.add(neworder);
		}else if(qty==order.getQty()){ 
			order.setQty(0L);
			order.setStatus(OrderStatus.CANCELLED);
			PurchaseOrder modifiedOrder=purchaseOrderRepository.save(order);
			ordersList.add(modifiedOrder);
			PurchaseOrder neworder=purchaseOrderRepository.save(new PurchaseOrder(userName,qty,showIdTo,OrderStatus.COMPLETE));
			ordersList.add(neworder);
			
		}else {
			PurchaseOrder errorOrder=new PurchaseOrder();
			errorOrder.setStatus(OrderStatus.ERROR);
			errorOrder.setMsg("Error Processing order;");
			ordersList.add(errorOrder);
		}
		}else{
			PurchaseOrder errorOrder=new PurchaseOrder();
			errorOrder.setStatus(OrderStatus.ERROR);
			errorOrder.setMsg("Error Show Ids can not be the same;");
			ordersList.add(errorOrder);
		}
		
		
		return ordersList;
		
	}
	
	@RequestMapping(value= "/search", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public List<PurchaseOrder>  searchEvents( final WebRequest webRequest){ 
		
		List<PurchaseOrder> poList=new ArrayList<PurchaseOrder>();
		 //String keyword = webRequest.getParameter("keyword");
		 String showIdFrom = webRequest.getParameter("showIdFrom");
		 String showIdTo = webRequest.getParameter("showIdTo");
	
		 System.out.println(showIdFrom);
		 if(showIdFrom!=null &&showIdTo!=null ){
			 poList=purchaseOrderRepository.retrievePORange( Long.valueOf(showIdFrom), Long.valueOf(showIdTo));
			 
		 }else if(showIdFrom!=null &&showIdTo==null ){
			 poList=purchaseOrderRepository.retrievePOFrom(Long.valueOf(showIdFrom));
		 }else if(showIdFrom==null &&showIdTo!=null ){
			 poList=purchaseOrderRepository.retrievePOTo(Long.valueOf(showIdTo));
		 }
	
System.out.println("poList.size()"+poList.size());
	 return poList;
}
}
