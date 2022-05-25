package swop.Miscellaneous;

import java.util.Random;

/**
 * Generates ids for car orders
 */
public final class RandomID {

	/**
	 * A method for generating random sequence off symbols
	 * @param size of the generated string
	 * @return String of random symbols
	 */
	public static String generateRandomID(int size) {

		int[] indexB =  {48,57,65,90,97,122};
		 Random r = new Random();
		 StringBuilder ID = new StringBuilder();
		 for(int i = 0; i < size; i++) {
			 int a = r.nextInt(3);
			 int val =  r.nextInt(indexB[a*2], indexB[(a*2)+1]);
			 ID.append((char) val);
			 
		 }
		 return ID.toString();

	}

}
