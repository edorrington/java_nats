package nats.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import nats.Session;

public class Sub {

	public static void main(String[] args) throws Exception {
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		Session session = Session.connect(new Properties());
		session.start();

		System.out.println("Listening on : " + args[0]);
		session.subscribe(args[0], session.new EventHandler() {
			public void execute(Object o) {
				System.out.println("Received update : " + (String)o);
			}
		});
		
		System.out.println("\nPress enter to exit.");
		bufferedReader.readLine();
		
		session.flush();
		session.stop();
		
		System.exit(0);
	}
}