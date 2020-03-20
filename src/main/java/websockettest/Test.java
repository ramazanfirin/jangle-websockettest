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

public class Test {

	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {

		String as = "{\"isSuccess\":false,\"errorCode\":1,\"errorDescription\":\"MESSAGE_SEND_NOT_ALLOWED\",\"message\":null}";
		ObjectMapper objectMapper = new ObjectMapper();
		MessageWrapper asd= objectMapper.readValue(as, MessageWrapper.class);
		
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
		
		
		String user1 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlcjE0IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTU1OTk5MDkzMH0.isD2LfewJPqZzUC2swrb0JCd2W99iwMKyZdpKQDEJ84kyyX9KUrYj_ahSbA_QZ2XIw6ACqb_KebPKmTh-9WN_g";
		StompSession session = getSession(user1, "testuser14", stompClient);
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

		
		String user2 ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU1OTk5MDg4N30.-uJtefd8_pDcsNRTPR9eFv8bF8aHcmPB4IsqQ8uP_WfkjS6PeJl-TQeDeW0CaRCxSj-ifi_NjbyGCzkyWGQa8g";
		StompSession session2 = getSession(user2, "admin", stompClient);
		session2.subscribe("/user/exchange/amq.direct/chat.message", new StompFrameHandler() {
		   
			public Type getPayloadType(StompHeaders headers) {
		        return MessageWrapper.class;
		    }

		    public void handleFrame(StompHeaders headers,Object payload) {
		    	
		      //  System.out.println("usera geldi "+(String) payload);
		        System.out.println("usera e geldi ");
		    }
		});
		
		
		
		//session.send("/user/user/exchange/amq.direct/chat.message", "4411111111112");
		
		//session.send("/user/admin/exchange/amq.direct/chat.message", "4sdfdsdfsfsa411111111112");
//		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message");
    	sendMessageVm.setTargetUsername("admin");
		//session.send("/chat.private."+"admin", objectMapper.writeValueAsString(sendMessageVm));
		session.send("/chat.private."+"admin", sendMessageVm);
//		
//		System.out.println("bitti");
		
		//testMessageNotAllowed(session);
		testMessageNotOnline(session);
		
		new Scanner(System.in).nextLine(); // Don't close immediately.
	}
	
	private static void testMessageNotAllowed(StompSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message");
    	sendMessageVm.setTargetUsername("admin");
		session.send("/chat.private."+"admin", sendMessageVm);
	}
	
	private static void testMessageNotOnline(StompSession session) {
		ObjectMapper objectMapper = new ObjectMapper();
		SendMessageVm sendMessageVm = new SendMessageVm();
    	sendMessageVm.setMessage("test message");
    	sendMessageVm.setTargetUsername("26379695542204137812");
		session.send("/chat.private."+"26379695542204137812", sendMessageVm);
	}
	
	private static void testSendMessage(StompSession session) {
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