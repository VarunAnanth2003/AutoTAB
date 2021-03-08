package com.flns.autotab;
import java.awt.*;
import java.awt.Component;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.*;

public class TABDisplayGUIDB {
    public static void displayTab(Point oldPos, String ID, String name, String tabString) {
        final JFrame mainFrame = new JFrame("MIDItoTAB");
        mainFrame.setSize(600,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocation(oldPos);

        JButton ex = new JButton("Exit");
        ex.addActionListener(e -> {
            DatabaseGUI.displayList(mainFrame.getLocation());
            mainFrame.dispose();
        });
        ex.setAlignmentX(Component.LEFT_ALIGNMENT);
        ex.setBounds(10, 10, 100, 30); 

        final JLabel jlb = new JLabel("Click \"Save\" to upload TAB to database");
        jlb.setBounds(150, 10, 400, 30);

        final JTextArea jta = new JTextArea(1,5);
        final JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setBounds(50, 50, 500, 250);
        jta.setFont(new Font("monospaced", Font.PLAIN, 12));

        JButton copy = new JButton("Copy");
        copy.addActionListener(e -> {
            StringSelection ss = new StringSelection(jta.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(ss, null);
        });
        copy.setAlignmentX(Component.LEFT_ALIGNMENT);
        copy.setBounds(400, 320, 100, 30); 

        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            ConnectionHandler.removeData(ID);
            DatabaseGUI.displayList(mainFrame.getLocation());
            mainFrame.dispose();
        });
        delete.setAlignmentX(Component.LEFT_ALIGNMENT);
        delete.setBounds(250, 320, 100, 30); 

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            if(ConnectionHandler.sendData(ID, name, jta.getText())) {
                DatabaseGUI.displayList(mainFrame.getLocation());
                mainFrame.dispose();
            } else {
                jlb.setText("TAB either already exists, or had an error with uploading");
            }
        });
        save.setAlignmentX(Component.LEFT_ALIGNMENT);
        save.setBounds(100, 320, 100, 30); 

        jta.setText(tabString);
        
        mainFrame.add(ex);
        mainFrame.add(jsp);
        mainFrame.add(copy);
        mainFrame.add(save);
        mainFrame.add(delete);
        mainFrame.add(jlb);
        JPanel mainPanel = new JPanel();
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
}
