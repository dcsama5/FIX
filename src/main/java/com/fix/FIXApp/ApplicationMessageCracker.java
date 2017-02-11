package com.fix.FIXApp;

import quickfix.FieldNotFound;
import quickfix.MessageCracker;
import quickfix.SessionID;
import quickfix.field.*;

public class ApplicationMessageCracker extends MessageCracker {
    
    public void onMessage(quickfix.fix42.Logon message, SessionID sessionID) {
        System.out.println("calling onMessage");
        //message.
    }
    
    public void onMessage(quickfix.fix42.QuoteRequest message, SessionID sessionID) {
        System.out.println("recieved quote request");
        System.out.println(message.toString());
    }
    
    public void onMessage(quickfix.fix42.ExecutionReport message, SessionID sessionID) throws FieldNotFound {
    	LastPx lastpx = new LastPx();
    	LastShares lastshares = new LastShares();
    	
    	message.get(lastpx);
    	message.get(lastshares);
    }
    
    public void onMessage(quickfix.fix42.Quote message, SessionID sessionID) throws FieldNotFound {
    	OfferPx price = new OfferPx();
    	message.get(price);
    	
    	System.out.println(price);
    }
    
    public void onMessage(quickfix.fix42.MarketDataRequest message, SessionID sessionID) throws FieldNotFound {
    	NoRelatedSym num = new NoRelatedSym();
    	message.get(num);
    	
    	System.out.println(num);
    }
}

