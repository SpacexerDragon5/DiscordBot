package botter.tests;

import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
		 CleverParser r = new CleverParser();
		 System.out.println("Ibit");
		r.init();
		System.out.println("CLeverbots antwort: "+r.sendAI("Hallo"));
	}

}
