package swop.Database;

import java.util.Random;
import java.util.stream.IntStream;

public final class RandomID {

	public static String random(int size) {
		 int leftLim1 = 48; 
		 int rightLim1 = 57;
		 int leftLim2 = 65; 
		 int rightLim2 = 90;
		 int leftLim3 = 97; 
		 int rightLim3 = 122;
		 Random r = new Random();
		 String ID = "";
		 for(int i = 0; i < size; i++) {
			 int a = r.nextInt(3);
			 int val = 0;
			 switch(a) {
			 case 0: val =  r.nextInt(leftLim1, rightLim1+1);
			 case 1: val =  r.nextInt(leftLim2, rightLim2+1);
			 case 2: val =  r.nextInt(leftLim3, rightLim3+1);
			 }
			 ID += Character.toString((char) val);
			 
		 }
		 return ID;

	}

}
