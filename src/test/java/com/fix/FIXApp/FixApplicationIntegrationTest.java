package com.fix.FIXApp;



import java.io.InputStream;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix42.Logon;
import quickfix.fix42.Message;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.Logout;
import quickfix.fix42.Quote;

public class FixApplicationIntegrationTest {
	
	private Initiator initiator;
	private static SessionID sessionID;
	private static boolean setup = false;
	@Before
	public void Login() throws ConfigError, InterruptedException, SessionNotFound {
		if(setup) {
			return;
		}
		FixApplication application = new FixApplication();
		InputStream fi = getClass().getResourceAsStream("/settings.txt");
		SessionSettings settings = new SessionSettings(fi);
		MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logfactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();
        initiator = new SocketInitiator(application, storeFactory, settings, logfactory, messageFactory);
        initiator.start();
        Thread.sleep(3000);
        sessionID = initiator.getSessions().get(0);
        if(initiator.isLoggedOn()) {
        	System.out.println("yes");
        }
		setup = true;
	}
	
	@AfterClass
	public static void Logout() throws SessionNotFound {
		
		Message logout = new Logout();
		Session.sendToTarget(logout, sessionID);
	}
	@Ignore
	@Test
	public void RequestStockQuote() throws SessionNotFound, InterruptedException {
		Quote quote = new Quote();
		quote.set(new QuoteID("1776"));
		quote.set(new Symbol("ABB"));
		Session.sendToTarget(quote, sessionID);
		Thread.sleep(5000);
	}
	
	@Test
	public void SendNewSingleOrderUnitTest() throws SessionNotFound, InterruptedException {
		Message message = prepareNewSingleOrderMessage();
		Session.sendToTarget(message, sessionID);
		Thread.sleep(5000);
	}
	
	@Test
	@Ignore
	public void TestMarketDataRequest() throws SessionNotFound, InterruptedException {
		MarketDataRequest request = new MarketDataRequest();
		request.set(new MDReqID("22"));
		request.set(new SubscriptionRequestType('1'));
		request.set(new MarketDepth('1'));
		request.set(new NoMDEntryTypes(3));
		MarketDataRequest.NoMDEntryTypes group = new MarketDataRequest.NoMDEntryTypes();
		group.set(new MDEntryType('0'));
		group.set(new MDEntryType('1'));
		group.set(new MDEntryType('2'));
		request.addGroup(group);
		MarketDataRequest.NoRelatedSym symbols = new MarketDataRequest.NoRelatedSym();
		symbols.set(new Symbol("GOOG"));
		request.addGroup(symbols);
		Session.sendToTarget(request, sessionID);
		Thread.sleep(20000);
	}
	
	
	private static Message prepareNewSingleOrderMessage() {
		NewOrderSingle qro = new NewOrderSingle();
		qro.set(new Symbol("ABB"));
        qro.set(new HandlInst('2'));
        qro.set(new Side('1'));
        qro.set(new OrdType('1'));
        qro.set(new ClOrdID("1"));
        qro.set(new TransactTime(new Date()));
        qro.set(new Text("fill my order"));
        return qro;
	}
}
