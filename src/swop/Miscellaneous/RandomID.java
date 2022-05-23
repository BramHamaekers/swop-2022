package swop.Miscellaneous;

import java.util.Random;

/**
 * Generates ids for car orders
 */
public final class RandomID {

	/**
	 * TODO: refactor this
	 * A method for generating random sequence off symbols
	 * @param size of the generated string
	 * @return String of random symbols
	 */
	public static String generateRandomID(int size) {

		 int leftLim1 = 48;
		 int rightLim1 = 57;
		 int leftLim2 = 65; 
		 int rightLim2 = 90;
		 int leftLim3 = 97; 
		 int rightLim3 = 122;
		 Random r = new Random();
		 StringBuilder ID = new StringBuilder();
		 for(int i = 0; i < size; i++) {
			 int a = r.nextInt(3);
			 int val = 0;
			 switch(a) {
			 case 0: val =  r.nextInt(leftLim1, rightLim1+1);
			 case 1: val =  r.nextInt(leftLim2, rightLim2+1);
			 case 2: val =  r.nextInt(leftLim3, rightLim3+1);
			 }
			 ID.append((char) val);
			 
		 }
		 return ID.toString();

	}

}
