package com.fix.FIXApp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import quickfix.*;
/**
 *
 * @author ameer
 */
public class FixApplication implements Application {
    
    MessageCracker cracker = new ApplicationMessageCracker44();
    
    public void onCreate(SessionID sid) {
        System.out.println("session created");
    }

    public void onLogon(SessionID sid) {
        System.out.println("logged in");
    }

    public void onLogout(SessionID sid) {
    	System.out.println("logged out");
    }

    public void toAdmin(Message msg, SessionID sid) {
    }

    public void fromAdmin(Message msg, SessionID sid) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
    }

    public void toApp(Message msg, SessionID sid) throws DoNotSend {
    }

    public void fromApp(Message msg, SessionID sid) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
            System.out.println("Received Message");
            cracker.crack(msg, sid);
            
    }
    
}
