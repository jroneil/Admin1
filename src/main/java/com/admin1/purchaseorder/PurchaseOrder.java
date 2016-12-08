// tag::sample[]
package com.admin1.purchaseorder;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long transactionId;
    private String userName;
    private Long qty;
	private Long showId;
	private String status;
	private String msg;

    protected PurchaseOrder() {}

    public PurchaseOrder(String userName, Long qty, Long showId,String status) {
		super();
		this.userName = userName;
		this.qty = qty;
		this.showId = showId;
		this.status=status;
	}

 /*   @Override
    public String toString() {
        return String.format(
                "Order2[id=%d, userName='%s', qty='%d', showId='%d']",
                id, userName, qty,showId);
    }*/

// end::sample[]

	
	

	public Long getQty() {
		return qty;
	}

	public Long getShowId() {
		return showId;
	}

	public String getStatus() {
		return status;
	}

	public String getUserName() {
		return userName;
	}
	
	 public String toString() {
	      StringBuilder result = new StringBuilder();
	      String newLine = System.getProperty("line.separator");

	      result.append( this.getClass().getName() );
	      result.append( " Object {" );
	      result.append(newLine);

	      //determine fields declared in this class only (no fields of superclass)
	      Field[] fields = this.getClass().getDeclaredFields();

	      //print field names paired with their values
	      for ( Field field : fields  ) {
	        result.append("  ");
	        try {
	          result.append( field.getName() );
	          result.append(": ");
	          //requires access to private field:
	          result.append( field.get(this) );
	        } catch ( IllegalAccessException ex ) {
	          System.out.println(ex);
	        }
	        result.append(newLine);
	      }
	      result.append("}");

	      return result.toString();
	    }

	

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

