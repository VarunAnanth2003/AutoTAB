package com.flns.autotab;
import java.io.*;

import javax.sound.midi.*;

public class MidiReader {
    public static NoteLinkedList pullDataFromMidi(File mf) throws InvalidMidiDataException, IOException {
        try {
            Sequence seq = MidiSystem.getSequence(mf);
            NoteLinkedList nll = new NoteLinkedList();
            for(Track t : seq.getTracks()) {
                for (int i = 0; i < t.size(); i++) {
                    MidiEvent currEvent = t.get(i);
                    if(currEvent.getMessage() instanceof ShortMessage) {
                        ShortMessage noteData = (ShortMessage) currEvent.getMessage();
                        if(noteData.getCommand() == 0x90) {
                            int noteRaw = noteData.getData1();

                            int noteVal = noteRaw%12;
                            int octave = noteRaw/12;
                            int velocity = noteData.getData2();
                            long startTime = currEvent.getTick();
                            Note n = new Note(noteVal, octave, velocity, startTime);
                            nll.addAtEnd(new NoteLinkedList.NoteNode(n));
                        }
                    } 
                }
            }
            return nll;
        } catch (InvalidMidiDataException imde) {
            System.out.println("Midi is corrupted");
            throw new InvalidMidiDataException();
        } catch (IOException ioe) {
            System.out.println("Input/Output Exception");
            throw new IOException();
        }
    }
}