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

public class TestLocal {

	
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
		
		
		String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZWRlIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTU3ODA2NzU2MX0.GdlAu7gi717rthvU2x8a4Efdw03Mpjz_3g5eq6gqnR8gBa-xuo862u5f2ppNGzO8IXWqAlTfOmWPGbJDmFaGRA";
		StompSession session = getSession(adminToken,  stompClient);
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

		
		String admin2Token ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZmlyaW4iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNTc4MDY3NTA1fQ.za7SlGGrRVRP5W9SSrgTvB2nX0ZoTOTnfV-UjuU2CV3Yr9iqmEQ4eevGFliCELBMKt3zBKcPrq2hbwQbbX9NlQ";
		StompSession session2 = getSession(admin2Token, stompClient);
		session2.subscribe("/user/admin/exchange/amq.direct/chat.message", new StompFrameHandler() {
		   
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

		testSendMessageSuccess(session,"rfirin");
		 //testMessageReaded(session,"admin");
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
	
	private static void testMessageReaded(StompSession session,String username) {
		ObjectMapper objectMapper = new ObjectMapper();
		MessageReadedVM messageReadedVM = new MessageReadedVM();
    	messageReadedVM.setUuid("uuidsdfd");
		session.send("/chat.messageReaded.information",messageReadedVM);
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
	
	
	public static StompSession getSession(String token, WebSocketStompClient stompClient) throws InterruptedException, ExecutionException {
		String URL2 = "http://ec2-52-11-218-57.us-west-2.compute.amazonaws.com:8080/websocket?access_token="+token;
		
		StompSessionHandler sessionHandler2 = new SessionHandler();
		StompSession session2 = stompClient.connect(URL2, sessionHandler2).get();
		
		return session2;
	}
	
}