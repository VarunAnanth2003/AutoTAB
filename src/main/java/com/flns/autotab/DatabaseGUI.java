package com.flns.autotab;
import java.awt.*;

import javax.swing.*;

public class DatabaseGUI {
    public static void displayList(Point oldPos) {
        String[][] dbData = ConnectionHandler.pullData();
        final JFrame mainFrame = new JFrame("MIDItoTAB");
        mainFrame.setSize(600,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocation(oldPos);

        JButton ex = new JButton("Exit");
        ex.addActionListener(e -> {
            RunnerGUI.executeGUI(mainFrame.getLocation());
            mainFrame.dispose();
        });
        ex.setMaximumSize(new Dimension(100, 30)); 
        ex.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jlb = new JLabel("All TABs:");
        jlb.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(50, 50, 500, 300);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(ex);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(jlb);
        mainPanel.add(Box.createVerticalStrut(10));
        for (int i = 0; i < dbData.length; i++) {
            final JButton b = new JButton(dbData[i][1]);
            b.putClientProperty("ID", dbData[i][0]);
            b.putClientProperty("name", dbData[i][1]);
            b.putClientProperty("tabString", dbData[i][2]);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            Dimension buttonDim = new Dimension(400, 30);
            b.setMaximumSize(buttonDim);
            b.addActionListener(e -> {
                TABDisplayGUIDB.displayTab(mainFrame.getLocation(), (String) b.getClientProperty("ID"), (String) b.getClientProperty("name"), (String) b.getClientProperty("tabString"));
                mainFrame.dispose();
            });
            mainPanel.add(b);
            mainPanel.add(Box.createVerticalStrut(10));
        }
        JScrollPane jsp = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainFrame.add(jsp);
        mainFrame.setVisible(true);
    }
}
