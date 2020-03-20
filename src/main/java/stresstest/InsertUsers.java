package stresstest;

import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class InsertUsers extends BaseInsert{

	public void insert() {
		
		HttpPost httpPut = new HttpPost(getEndpointAddress("DATAV2")+"/api/messages/messageReaded");
		StringEntity entity = new StringEntity(objectMapper.writeValueAsString(messageReadedVM),Charset.forName("UTF-8"));
		httpPut.setEntity(entity);(entity);
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		//httpPut.setHeader("Authorization", "Bearer "+token);
		String content = sendRequest(httpPut);
		//Message message = objectMapper.readValue(content, Message.class);
		
		
	}
	
}
