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

public class TestLocalJetty {

	
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
		
		
		String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU1MTEzMzM4M30.UgViEzuZqK6tPaUS9zv7QF2QXOtfSVfDDvwItdEtJ7bYhwbsLSC5xF1ZfoBUxZ8e2tY5t5lsKiTLyKm6ksM_Zw";
		StompSession session = getSession(adminToken, "admin", stompClient);
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

		
		String admin2Token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTU1MTEzMzQyMX0.rwocETjktK8PhbqPzu2rom2Xv1Pq1WrRGF6PGwyH0yiYUwo-labKkZRBSlDbYRwVqEyOhTJXuQ4CN26r2B1QUA";
		StompSession session2 = getSession(admin2Token, "user", stompClient);
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

		testSendMessageSuccess(session,"user");
		//testMessageNotOnline(session);
		//testNotAllowed(session);
		
		new Scanner(System.in).nextLine(); // Don't close immediately.
	}
	
	
	
	private static void testSendMessageSuccess(StompSession session,String username) {
		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message");
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
		String URL2 = "http://localhost:8080/echo?access_token="+token;
		
		StompSessionHandler sessionHandler2 = new SessionHandler();
		StompSession session2 = stompClient.connect(URL2, sessionHandler2).get();
		
		return session2;
	}
	
}