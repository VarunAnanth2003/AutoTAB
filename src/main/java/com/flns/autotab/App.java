package com.flns.autotab;

import java.awt.Point;

/**
 * Auto TABBER 
 * Username: admin
 * PW: adminpassword
 */
public final class App {
    
    public static void main(String[] args) {
        ConnectionHandler.establishConnection();
        RunnerGUI.executeGUI(new Point(0,0));
    }
}
