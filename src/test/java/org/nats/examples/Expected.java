package org.nats.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.nats.*;

public class Expected {

	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = Connection.connect(new Properties());

		System.out.println("Listening on : " + args[0]);
		System.out.println("Will timeout in 10 seconds unless 2 messages are received");
		Integer sid = conn.subscribe(args[0], new MsgHandler() {
			int received = 0;
			public void execute() {
				received++;
				System.out.println("Message received : " + received);
			}
		});
		Properties opt = new Properties();
		opt.put("expected", new Integer(2));
		conn.timeout(sid, 10, opt, new MsgHandler() {
			public void execute(Object o) {
				System.out.println("Timeout waiting for a message!");
			}
		});

		System.out.println("\nPress enter to exit.");
		bufferedReader.readLine();
		
		conn.close();
		System.exit(0);
	}
}
