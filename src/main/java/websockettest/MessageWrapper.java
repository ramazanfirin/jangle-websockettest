package websockettest;

import java.io.Serializable;
import java.util.Date;

/**
 * A Message.
 */

public class MessageWrapper implements Serializable {

  
    private Boolean isSuccess = true;
    
    private Long errorCode = 0l;
    
    private String errorDescription="";
    
    private String messageUuid ;
    
    private String message;
    
    private String messageType;
    
    private String sourceusername;
    
    private Date date;

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getMessageUuid() {
		return messageUuid;
	}

	public void setMessageUuid(String messageUuid) {
		this.messageUuid = messageUuid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSourceusername() {
		return sourceusername;
	}

	public void setSourceusername(String sourceusername) {
		this.sourceusername = sourceusername;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
    
  
}
