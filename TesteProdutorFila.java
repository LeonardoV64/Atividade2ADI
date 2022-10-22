package com.example.jms;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection("leo", "leo"); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("LOG");
		
		MessageProducer producer = session.createProducer(fila);
		
		Destination topico = (Destination) context.lookup("TOPIC");
		
		MessageProducer producer2 = session.createProducer(topico);
		
		Random random = new Random(System.currentTimeMillis());
		
		

		
		List<String> erros = new ArrayList<String>();
		
		erros.add("ERR");
		erros.add("WARN");
		erros.add("DEBUG");
		erros.add("");
		
		List<String> feriados = new ArrayList<String>();
		
		feriados.add("DIA DAS CRIANÇAS");
		feriados.add("NATAL");
		feriados.add("PASCOA");
		feriados.add("DIA DOS PAIS");
		
		for(int i = 0; i < 1000; i++) {
		int prioridade = 0;
			
		int feriado = 0;
		int numero = random.nextInt(4);	
		
		
		String msg = (String) erros.get(numero);
		
		if(msg == "ERR") {
			prioridade = 9;
		}else if(msg == "DEBUG") {
			prioridade = 4;
		}else if(msg == "WARN") {
			prioridade = 1;
		}else {
			feriado = 1;
		}
		
		if(feriado == 0 ) {
			Message message = session.createTextMessage(msg + "| Apache ActiveMQ 5.12.0 (localhost, ID:Mac-mini-de-IFSP.local-49701-1443131721783-0:1) is starting");
			producer.send(message,DeliveryMode.NON_PERSISTENT,prioridade,300000);
		}else {
			int numero2 = random.nextInt(4);
			String msg2 = (String) feriados.get(numero2);
			Message message2 = session.createTextMessage(msg2 + "| FERIADO |Apache ActiveMQ 5.12.0 (localhost, ID:Mac-mini-de-IFSP.local-49701-1443131721783-0:1) is starting");
			producer2.send(message2);
		}
		}
		
				
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}