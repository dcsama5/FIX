package com.fix.FIXApp;

import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.MessageCracker;
import quickfix.SessionID;
import quickfix.field.*;



public class ApplicationMessageCracker44 extends MessageCracker {
    
    public void onMessage(quickfix.fix44.Logon message, SessionID sessionID) {
        System.out.println("calling onMessage");
        //message.
    }
    
    public void onMessage(quickfix.fix44.QuoteRequest message, SessionID sessionID) {
        System.out.println("recieved quote request");
        System.out.println(message.toString());
    }
    
    public void onMessage(quickfix.fix44.ExecutionReport message, SessionID sessionID) throws FieldNotFound {
    	LastPx lastpx = new LastPx();
    	LastQty lastshares = new LastQty();
    	
    	message.get(lastpx);
    	message.get(lastshares);
    }
    
    public void onMessage(quickfix.fix44.MarketDataSnapshotFullRefresh message, SessionID sessionID) throws FieldNotFound {
    	int noEntries = message.getNoMDEntries().getValue();
    	Symbol Symbol = message.getSymbol();
    	System.out.print(Symbol.getValue());
    	
    	quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries group = new
    			quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries();
    	MDEntryPx MDEntryPx = new MDEntryPx();
    	MDEntryType MDEntryType = new MDEntryType();
    	MDEntrySize MDEntrySize = new MDEntrySize();
    	
    	for(int i=1; i<=noEntries; i++) {
    		message.getGroup(i, group);
    		group.get(MDEntryPx);
    		System.out.print("\t"+MDEntryPx);
    	}
    	
    	System.out.println();
    }
    public void onMessage(quickfix.fix44.Quote message, SessionID sessionID) throws FieldNotFound {
    	OfferPx price = new OfferPx();
    	message.get(price);
    	
    	System.out.println(price);
    }
    
    public void onMessage(quickfix.fix44.MarketDataRequest message, SessionID sessionID) throws FieldNotFound {
    	NoRelatedSym num = new NoRelatedSym();
    	message.get(num);
    	
    	System.out.println(num);
    }
}

