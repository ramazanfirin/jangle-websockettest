package stresstestt;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidTest {
public static void main(String[] args) {
	UUID uuid = UUID.randomUUID();
	  long l = ByteBuffer.wrap("2e8ac7c7-341f-417b-9384-5c51c50b227c".getBytes()).getLong();
	  String a = Long.toString(l, Character.MAX_RADIX);
	  System.out.println(a);
}
}
