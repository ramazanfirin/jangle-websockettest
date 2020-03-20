package websockettest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestLocal2 {
    
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {

		test2();
		
		
	}
	
	
	public static void test2() throws InterruptedException, ExecutionException, JsonProcessingException {
		WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<Transport>(1);
		transports.add(new WebSocketTransport(simpleWebSocketClient));
		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		//stompClient.setMessageConverter(new StringMessageConverter());
		
		
		String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNTYyMTQxNDg4fQ.YUuCSmmnLbR8qDJWI8D8k6HL4puqlGZIG8yIrbwa7F2BnB3Y4-MoTZX7mSSm_hb8zlVRMukx-dZAW-ise3RvkQ";
		StompSession session = getSession(adminToken, "testuser1", stompClient);
		session.subscribe("/user/exchange/amq.direct/chat.message", new StompFrameHandler() {
			   
			ObjectMapper objectMapper = new ObjectMapper();
			
			public Type getPayloadType(StompHeaders headers) {
		        return MessageWrapper.class;
		    }

		    public void handleFrame(StompHeaders headers,Object payload) {
		    	MessageWrapper asd = (MessageWrapper)payload;
		    	try {
					System.out.println("admin e geldi "+objectMapper.writeValueAsString(asd));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});

		
		String admin2Token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlcjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNTYyMTQxNjkxfQ.XKf5mcEN0bBcvkSA4-TrhKqO3YPEt2yuR-euxkY_2-pT6TtSM3D8QCs1Qe6Wwj8cmYCGqM-DMFSid6jCQEwc5A";
		StompSession session2 = getSession(admin2Token, "testuser2", stompClient);
		session2.subscribe("/user/exchange/amq.direct/chat.message", new StompFrameHandler() {
		   
			ObjectMapper objectMapper = new ObjectMapper();
			public Type getPayloadType(StompHeaders headers) {
		        return MessageWrapper.class;
		    }

		    public void handleFrame(StompHeaders headers,Object payload) {
		    	
		    	MessageWrapper asd = (MessageWrapper)payload;
		    	try {
					System.out.println("user e geldi "+objectMapper.writeValueAsString(asd));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});

		testSendMessageSuccess(session,"testuser2");
		//testMessageNotOnline(session);
		//testNotAllowed(session);
		
		//testMessageReaded(session2);
		System.out.println("bitti");
		
		new Scanner(System.in).nextLine(); // Don't close immediately.
	}
	
	private static void testMessageReaded(StompSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		MessageReadedVM messageReadedVM = new MessageReadedVM();
    	messageReadedVM.setUuid("8341bde7-3395-4b09-a611-8ceb994f97f2");
		session.send("/chat.messageReaded.information",messageReadedVM);
	}
	
	private static void testSendMessageSuccess(StompSession session,String username) {
		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message -- 25.05.2010- 08:2");
    	sendMessageVm.setTargetUsername(username);
		session.send("/chat.private."+username, sendMessageVm);
	}
	
	private static void testMessageNotOnline(StompSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message");
    	sendMessageVm.setTargetUsername("26379695542204137812");
		session.send("/chat.private."+"26379695542204137812", sendMessageVm);
	}
	
	private static void testNotAllowed(StompSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message");
    	sendMessageVm.setTargetUsername("26379695542204137812");
		session.send("/chat.private."+"26379695542204137812", sendMessageVm);
	}
	
	
	public static StompSession getSession(String token, String username,WebSocketStompClient stompClient) throws InterruptedException, ExecutionException {
		String URL2 = "http://176.33.14.111:8080/websocket?access_token="+token;
		
		StompSessionHandler sessionHandler2 = new SessionHandler();
		StompSession session2 = stompClient.connect(URL2, sessionHandler2).get();
		
		return session2;
	}
	
}