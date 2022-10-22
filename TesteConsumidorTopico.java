package com.example.jms;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;


public class TesteConsumidorTopico {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
		
		Message message = consumer.receive();
		
		System.out.println("Mensagem: " + message);

		

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}
}