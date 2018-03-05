import java.util.*;

public class Notification {
	private Date date;
	public enum NotificationType { ADD, REMOVE, MODIFY };
	private NotificationType type;
	private Integer departmentID;
	private Integer productID;
	
	public Notification(NotificationType type, Integer departmentID, Integer productID) {
		date = new Date();
		this.departmentID = departmentID;
		this.productID = productID;
		this.type = type;
	}
	
	public NotificationType getNotificationType() {
		return type;
	}
	
	public Integer getDepartmentID() {
		return departmentID;
	}
	
	public Integer getProductID() {
		return productID;
	}
	
	public Date getDate() {
		return date;
	}
}
