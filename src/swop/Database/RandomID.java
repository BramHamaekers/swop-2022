package swop.Database;

import java.util.Random;

public final class RandomID {

	public static String random(int i) {
		 int leftLim = 48; 
		 int rightLim = 122;
		 Random random = new Random();

		 String string = random.ints(leftLim, rightLim)
		      .limit(i)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();
		return string;    
	}

}
