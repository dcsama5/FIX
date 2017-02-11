package com.fix.FIXApp;

import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import quickfix.field.*;
import quickfix.fix42.*;
import quickfix.fix42.Message;
import quickfix.*;
import quickfix.MessageFactory;

public class FixApplicationTest {

	private Initiator initiator = mock(Initiator.class);
	private SessionSettings settings = mock(SessionSettings.class);
	private SessionID session = mock(SessionID.class);
	
	@Before
	public void Login() throws InterruptedException {
		
		FixApplication application = new FixApplication();
		InputStream fi = getClass().getResourceAsStream("/settings.txt");
		//SessionSettings settings = new SessionSettings(fi);
		MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logfactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        //initiator = new SocketInitiator(application, storeFactory, settings, logfactory, messageFactory);
        when(initiator.isLoggedOn()).thenReturn(true);
        if(initiator.isLoggedOn()) {
        	//showGUI or use cmdLine
        }
	}
	/**
	 * Will automatically throw a SessionNotFound exception. Will probably need to mock 
	 * the static Session.sendToTarget() call.
	 * 
	 * @throws SessionNotFound
	 */
	@Test
	public void SendNewSingleOrderUnitTest() throws SessionNotFound {
		Message message = prepareMessage();
		Session.sendToTarget(message, session);
	}
	
	@Test
	public void test0Infront() {
		int val = 035;
		System.out.println(val);
	}

	private static Message prepareMessage() {
		NewOrderSingle qro = new NewOrderSingle();
		qro.set(new Symbol("YHOO"));
        qro.set(new HandlInst('2'));
        qro.set(new Side('1'));
        qro.set(new OrdType('1'));
        qro.set(new ClOrdID("1"));
        qro.set(new TransactTime(new Date()));
        qro.set(new Text("fill my order"));
        return qro;
	}
}
