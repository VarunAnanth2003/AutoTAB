package com.flns.autotab;
import java.awt.*;
import java.awt.Component;

import javax.swing.*;

public class TABFieldGUI {
    public static void makeTab(NoteLinkedList nll, Point oldPos) {
        final JFrame mainFrame = new JFrame("MIDItoTAB");
        mainFrame.setSize(600,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocation(oldPos);

        JButton ex = new JButton("Exit");
        ex.addActionListener(e -> {
            NewTABGUI.createNewTab(mainFrame.getLocation());
            mainFrame.dispose();
        });
        ex.setAlignmentX(Component.LEFT_ALIGNMENT);
        ex.setBounds(10, 10, 100, 30); 

        JLabel jlA = new JLabel("Name:");
        jlA.setBounds(200, 40, 200, 20);
        final JTextArea jta_NAME = new JTextArea(1,5);
        final JScrollPane jsp_NAME = new JScrollPane(jta_NAME, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp_NAME.setBounds(200, 60, 200, 20);

        JLabel jlB = new JLabel("BPM:");
        jlB.setBounds(200, 100, 200, 20);
        final JTextArea jta_BPM = new JTextArea(1,5);
        final JScrollPane jsp_BPM = new JScrollPane(jta_BPM, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp_BPM.setBounds(200, 120, 200, 20);

        JLabel jlC = new JLabel("Key:");
        jlC.setBounds(200, 160, 200, 20);
        final JComboBox<String> jcb_KEY = new JComboBox<>(new String[] {"[Select Key]", "A", "Ab", "A#", "B", "Bb", "C", "C#", "D", "Db", "D#", "E", "Eb", "F", "F#", "G", "Gb", "G#",      "Amin", "Abmin", "A#min", "Bmin", "Bbmin", "Cmin", "C#min", "Dmin", "Dbmin", "D#min", "Emin", "Ebmin", "Fmin", "F#min", "Gmin", "Gbmin", "G#min"});
        jcb_KEY.setBounds(200, 180, 200, 20);

        JLabel jlD = new JLabel("Center (String and Fret):");
        jlD.setBounds(200, 220, 200, 20);
        final JComboBox<String> jcb_STRING = new JComboBox<>(new String[] {"[Select String]", "E", "A", "D", "G", "B", "e"});
        jcb_STRING.setBounds(200, 240, 90, 20);
        final JTextArea jta_FRET = new JTextArea(1,5);
        final JScrollPane jsp_FRET = new JScrollPane(jta_FRET, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp_FRET.setBounds(300, 240, 90, 20);

        JLabel jlE = new JLabel("Octave Shift:");
        jlE.setBounds(450, 125, 200, 20);
        final JTextArea jta_OCT = new JTextArea(1,5);
        final JScrollPane jsp_OCT = new JScrollPane(jta_OCT, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp_OCT.setBounds(450, 145, 100, 20);
        jta_OCT.setText("0");

        JLabel jlF = new JLabel("Tab Section Length:");
        jlF.setBounds(450, 180, 200, 20);
        final JTextArea jta_BUCKET = new JTextArea(1,5);
        final JScrollPane jsp_BUCKET = new JScrollPane(jta_BUCKET, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp_BUCKET.setBounds(450, 200, 100, 20);
        jta_BUCKET.setText("50");

        final JCheckBox jchb_LINK = new JCheckBox("Link Notes?");
        jchb_LINK.setBounds(250, 280, 200, 20);

        JButton go = new JButton("Create TAB!");
        go.addActionListener(e -> {
            String bucket = jta_BUCKET.getText();
            String octave = jta_OCT.getText();
            String name = jta_NAME.getText();
            String BPM = jta_BPM.getText();
            Object key = jcb_KEY.getSelectedItem(); 
            Object string = jcb_STRING.getSelectedItem();
            String fret = jta_FRET.getText();
            boolean link = jchb_LINK.isSelected();
            boolean allGood = true;
            int bpm = 0;
            int octShift = 0;
            int bucketNum = 50;
            try {
                bucketNum = Integer.valueOf(bucket);
            } catch (NumberFormatException nfe) {
                jta_BUCKET.setText("INVALID BUCKET");
                bucketNum = 0;
                allGood = false;
            }
            try {
                octShift = Integer.valueOf(octave);
                nll.changeAllOctaves(octShift);
            } catch (NumberFormatException nfe) {
                jta_OCT.setText("INVALID OCTAVE");
                octShift = 0;
                allGood = false;
            }
            try {
                bpm = Integer.valueOf(BPM);
            } catch (NumberFormatException nfe) {
                jta_BPM.setText("INVALID BPM");
                allGood = false;
            }
            String midiKey = (String) key;
            if(midiKey.equals("[Select String]")) {
                allGood = false;
            }
            int[] center = new int[2];
            String centerString = (String) string;
                switch(centerString) {
                    case "E":
                        center[0] = 0;
                        break;
                    case "A":
                        center[0] = 1;
                        break;
                    case "D":
                        center[0] = 2;
                        break;
                    case "G":
                        center[0] = 3;
                        break;
                    case "B":
                        center[0] = 4;
                        break;
                    case "e":
                        center[0] = 5;
                        break;
                    default:
                        center[0] = -1;
                        allGood = false;
                }
                try{
                    center[1] = Integer.valueOf(fret);
                    if(center[1] < 0 || center[1] > 24) {
                        jta_FRET.setText("INVALID FRET");
                        allGood = false;
                    }
                } catch (NumberFormatException nfe) {
                    jta_FRET.setText("INVALID FRET");
                    allGood = false;
                }
                if(allGood) {
                    TAB t = new TAB(name, bpm, midiKey, nll, center, link, bucketNum);
                    TABDisplayGUI.displayTab(mainFrame.getLocation(), t);
                    mainFrame.dispose();
                }
        });
        go.setAlignmentX(Component.LEFT_ALIGNMENT);
        go.setBounds(225, 320, 150, 30); 

        mainFrame.add(jsp_NAME);
        mainFrame.add(jsp_BPM);
        mainFrame.add(jcb_KEY);
        mainFrame.add(jcb_STRING);
        mainFrame.add(jsp_FRET);
        mainFrame.add(jchb_LINK);
        mainFrame.add(jsp_OCT);
        mainFrame.add(jsp_BUCKET);
        mainFrame.add(ex);
        mainFrame.add(jlA);
        mainFrame.add(jlB);
        mainFrame.add(jlC);
        mainFrame.add(jlD);
        mainFrame.add(jlE);
        mainFrame.add(jlF);
        mainFrame.add(go);
        JPanel mainPanel = new JPanel();
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
}