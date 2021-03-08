package com.flns.autotab;
import java.awt.*;
import java.awt.Component;

import javax.swing.*;

public class RunnerGUI {
    public static void executeGUI(Point oldPos) {
        final JFrame mainFrame = new JFrame("MIDItoTAB");
        JPanel mainPanel = new JPanel();
        mainFrame.setSize(600,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.add(mainPanel);
        mainFrame.setLocation(oldPos);

        JButton nt = new JButton("New TAB");
        nt.addActionListener(e -> {
            NewTABGUI.createNewTab(mainFrame.getLocation());
            mainFrame.dispose();
        });
        nt.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton td = new JButton("TAB Database");
        td.addActionListener(e -> {
            DatabaseGUI.displayList(mainFrame.getLocation());
            mainFrame.dispose();
        });
        td.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton ex = new JButton("Exit");
        ex.addActionListener(e ->{
            System.exit(0);
        });
        ex.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jl = new JLabel("Auto TABBER");
        jl.setAlignmentX(Component.CENTER_ALIGNMENT);
        jl.setFont(new Font(jl.getFont().getName(), Font.PLAIN, 50));
        Dimension buttonDim = new Dimension(200, 100);
        nt.setMaximumSize(buttonDim); td.setMaximumSize(buttonDim);  ex.setMaximumSize(buttonDim); 
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(25)); mainPanel.add(jl); mainPanel.add(Box.createVerticalStrut(50)); mainPanel.add(nt); mainPanel.add(Box.createVerticalStrut(20)); mainPanel.add(td); mainPanel.add(Box.createVerticalStrut(20)); mainPanel.add(ex);
        
        mainFrame.setVisible(true);
    }
}
