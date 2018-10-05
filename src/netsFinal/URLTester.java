package netsFinal;

import java.util.ArrayList;

/**
 * This class will use the URLGetter and connect to websites.
 * @author swapneel
 *
 */
public class URLTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		URLGetter website = new URLGetter("https://www.facebook.com/underthebutton/posts/10155186075145855");
		
		website.printStatusCode();
		
		ArrayList<String> page = website.getContents();
		
		for (String line : page) {
			System.out.println(line);
		}

	}

}

