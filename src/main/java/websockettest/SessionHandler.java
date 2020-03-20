package websockettest;


import java.lang.reflect.Type;
import java.util.Date;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public class SessionHandler implements StompSessionHandler{

	public Type getPayloadType(StompHeaders headers) {
		// TODO Auto-generated method stub
		return String.class;
	}

	public void handleFrame(StompHeaders headers, Object payload) {
		if(payload!=null)  
			System.out.println(payload);
		else
			System.out.println(headers.getFirst("message"));
	}

	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		System.out.println("bağlantı sağlandı."+session.getSessionId());
	}

	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		String a = new String (payload);
		System.err.println("error1="+exception.getMessage());
		
	}

	public void handleTransportError(StompSession session, Throwable exception) {
		System.err.println("error2="+new Date());
		exception.printStackTrace();
	}

}
