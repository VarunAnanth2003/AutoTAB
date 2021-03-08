package com.flns.autotab;
import java.awt.*;
import java.awt.Component;
import java.io.File;
import java.io.IOException;


import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

public class NewTABGUI {
    final static JTextArea jta = new JTextArea(1,5);
    public static void createNewTab(Point oldPos) {
        final PaintableFrame mainFrame = new PaintableFrame("MIDItoTAB");
        mainFrame.setSize(600,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocation(oldPos);
        mainFrame.setTransferHandler(new FileDropHandler());

        JButton ex = new JButton("Exit");
        ex.addActionListener(e -> {
            RunnerGUI.executeGUI(mainFrame.getLocation());
            mainFrame.dispose();
        });
        ex.setAlignmentX(Component.LEFT_ALIGNMENT);
        ex.setBounds(10, 10, 100, 30); 

        final JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton go = new JButton("Go");
        go.addActionListener(e -> {
            String link = jta.getText();
            link = link.trim();
            link = link.replaceAll("\\\\", "\\\\");
            File mf = new File(link);
            if(mf.exists()) {
                try {
                    NoteLinkedList nll = MidiReader.pullDataFromMidi(mf); 
                    TABFieldGUI.makeTab(nll, mainFrame.getLocation());
                    mainFrame.dispose();
                } catch (InvalidMidiDataException | IOException error) {
                    jta.setText("MIDI is corrupted");
                }    
            } else {
                jta.setText("Not a valid filepath");
            }
        });
        go.setAlignmentX(Component.LEFT_ALIGNMENT);
        go.setBounds(475, 325, 50, 20); 

        jsp.setBounds(55, 325, 400, 20);
        mainFrame.add(ex);
        mainFrame.add(go);
        mainFrame.add(jsp);
        JPanel mainPanel = new JPanel();
        mainFrame.add(mainPanel);

        mainFrame.setVisible(true);
    }
}
