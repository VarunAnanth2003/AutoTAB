package com.flns.autotab;
public class Note {
    private String[] notesSharp = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private String[] notesFlat = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
    private int noteVal;
    private int octave;
    private int velocity;
    private long startTime;
    public Note() {}
    public Note(int noteVal, int octave, int velocity, long startTime) {
        this.noteVal = noteVal; this.octave = octave; this.velocity = velocity; this.startTime = startTime;
    }
    public String getNoteAsString(boolean flatten) {
        return flatten?notesFlat[noteVal]:notesSharp[noteVal];
    }
    public String getNoteAsString() {
        return notesSharp[noteVal]+octave;
    }
    public int getNoteVal() {
        return noteVal;
    }
    public int getOctave() {
        return octave;
    }
    public int getVelocity() {
        return velocity;
    }
    public long getStartTime() {
        return startTime;
    }

    public void reduceOctave() {
        octave--;
    }

    public void increaseOctave() {
        octave++;
    }

    @Override
    public String toString() {
        return getNoteAsString(false) + octave + " @" + startTime;
    }
}
