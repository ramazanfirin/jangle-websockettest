package stresstest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	    
	    String value = "2019-08-27T11:13:36.169Z";
	    simpleDateFormat.parse(value);
	}
	
}
