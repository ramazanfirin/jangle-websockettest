package websockettest;

public class SendMessageVm {


	String targetUsername;
	

	String message;
	
	String messageUuid;
	
	
	
	
	public SendMessageVm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public SendMessageVm(String targetUsername, String message) {
		super();
		this.targetUsername = targetUsername;
		this.message = message;
	}



	public String getTargetUsername() {
		return targetUsername;
	}

	public void setTargetUsername(String targetUsername) {
		this.targetUsername = targetUsername;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
}
