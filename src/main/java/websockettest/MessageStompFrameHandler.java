package websockettest;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

public class MessageStompFrameHandler implements StompFrameHandler{
	
	
	public MessageStompFrameHandler() {
		super();
	}

	@Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return MessageWrapper.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
       
    	System.out.println(o);
    }
}
