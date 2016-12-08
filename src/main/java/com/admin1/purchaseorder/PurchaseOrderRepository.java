package com.admin1.purchaseorder;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {

	
	public final static String EVENTID_RANGE = "SELECT po FROM PurchaseOrder po "+
            "WHERE po.showId >= :showIdFrom and po.showId<= :showIdTo and po.status in('"+OrderStatus.COMPLETE+"','"+OrderStatus.MODIFIED+"') order by po.transactionId desc";
	public final static String EVENTID_FROM = "SELECT po FROM PurchaseOrder po "+
            "WHERE po.showId>= :showIdFrom  and po.status in('C','M') order by po.transactionId desc";
	public final static String EVENTID_TO = "SELECT po FROM PurchaseOrder po "+
            "WHERE  po.showId<= :showIdTo and po.status in('C','M') order by po.transactionId desc";

	 @Query(EVENTID_RANGE)
     public List<PurchaseOrder> retrievePORange(@Param("showIdFrom") Long showIdFrom ,@Param("showIdTo") Long showIdTo);
	 @Query(EVENTID_FROM)
     public List<PurchaseOrder> retrievePOFrom(@Param("showIdFrom") Long showIdFrom );
	 @Query(EVENTID_TO)
     public List<PurchaseOrder> retrievePOTo(@Param("showIdTo") Long showIdto );
	List<PurchaseOrder> findByUserName(String userName);


}
