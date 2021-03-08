package com.flns.autotab;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class TAB {
    private int BPM;
    private String key;
    private NoteLinkedList notes;
    private int[] center = {0,0};
    private boolean linkedNotes;
    private String name;
    private int buckets;

    private String ID;

    private int minStepDenom;
    private int lowestO;
    private int highestO;
    private String[][] tab = new String[6][];

    private String[] E = { "E2", "F2", "F#2", "G2", "G#2", "A2", "A#2", "B2", "C3", "C#3", "D3", "D#3", "E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3", "C4", "C#4", "D4", "D#4", "E4" };
    private String[] A = { "A2", "A#2", "B2", "C3", "C#3", "D3", "D#3", "E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3", "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4" };
    private String[] D = { "D3", "D#3", "E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3", "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5" };
    private String[] G = { "G3", "G#3", "A3", "A#3", "B3", "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5" };
    private String[] B = { "B3", "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5" };
    private String[] e = { "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4", "C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5", "C6", "C#6", "D6", "D#6", "E6" };
    String[][] noteMap = { E, A, D, G, B, e };

    public TAB() {
    }

    public TAB(NoteLinkedList notes) {
        this.notes = notes;
        this.ID = name + ":" + new Random().nextInt(Integer.MAX_VALUE) + ":" + ((char)(new Random().nextInt(25) + 65));
        extractInfo();
    }

    public TAB(String name, int BPM, String key, NoteLinkedList notes, int[] center, boolean linkedNotes, int buckets) {
        this.name = name;
        this.BPM = BPM;
        this.key = key;
        this.notes = notes;
        this.center = center;
        this.linkedNotes = linkedNotes;
        this.buckets = buckets;
        this.ID = name + ":" + new Random().nextInt(Integer.MAX_VALUE) + ":" + ((char)(new Random().nextInt(25) + 65));
        verifyInfo(key, center);
        extractInfo();
    }

    private void verifyInfo(String key, int[] center) {
        String[] possibleKeys = {"A", "Ab", "A#", "B", "Bb", "C", "C#", "D", "Db", "D#", "E", "Eb", "F", "F#", "G", "Gb", "G#",      "Amin", "Abmin", "A#min", "Bmin", "Bbmin", "Cmin", "C#min", "Dmin", "Dbmin", "D#min", "Emin", "Ebmin", "Fmin", "F#min", "Gmin", "Gbmin", "G#min"};
        boolean validKey = false;
        for (int i = 0; i < possibleKeys.length; i++) {
            if(key.equals(possibleKeys[i])) validKey = true;
        }
        if(!validKey) {
            this.key = "[Insert Proper Key]";
        }
        if(center[0] > 5 || center[0] < 0) {
            center[0] = 0;
        }
        if(center[1] > 24 || center[1] < 0) {
            center[1] = 0;
        }
    }

    public int getBPM() {
        return BPM;
    }

    public String getKey() {
        return key;
    }

    public NoteLinkedList getNotes() {
        return notes;
    }

    public int[] getCenter() {
        return center;
    }

    public int getMinStepDenom() {
        return minStepDenom;
    }

    public String[][] getTabString() {
        return tab;
    }

    public int getLowestOctave() {
        return lowestO;
    }

    public int getHighestOctave() {
        return highestO;
    }

    public String[][] getTab() {
        return tab;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public int getBuckets() {
        return buckets;
    }

    private void extractInfo() {
        calculateMSD();
        calculateOctaveRange();
    }

    private void calculateMSD() {
        NoteLinkedList.NoteNode cn = notes.header;
        long minDiff = Long.MAX_VALUE;
        while (cn.next != null) {
            long diff = cn.next.data.getStartTime() - cn.data.getStartTime();
            if (diff != 0 && diff < minDiff) {
                minDiff = diff;
            }
            cn = cn.next;
        }
        minStepDenom = 384 / (int) minDiff;
        if(minStepDenom == 0) {
            minStepDenom = 1;
        }
    }

    private void calculateOctaveRange() {
        NoteLinkedList.NoteNode cn = notes.header;
        lowestO = cn.data.getOctave();
        highestO = cn.data.getOctave();
        while (cn.next != null) {
            cn = cn.next;
            if (cn.data.getOctave() < lowestO) {
                lowestO = cn.data.getOctave();
            }
            if (cn.data.getOctave() > highestO) {
                highestO = cn.data.getOctave();
            }
        }

    }

    public void tabMidi() {
        TreeMap<Long, NoteLinkedList> tm = new TreeMap<>();
        NoteLinkedList.NoteNode cn = notes.header;
        while (cn != null) {
            if (!tm.containsKey(cn.data.getStartTime())) {
                tm.put(cn.data.getStartTime(), new NoteLinkedList());
            }
            tm.get(cn.data.getStartTime()).addAtEnd(new NoteLinkedList.NoteNode(cn.data));
            cn = cn.next;
        }
        int tabLength = ((int) (notes.getLast().data.getStartTime() / (384 / minStepDenom)))+1;
        for (int i = 0; i < tab.length; i++) {
            tab[i] = new String[tabLength*2];
        }
        for (Map.Entry<Long, NoteLinkedList> entry : tm.entrySet()) {
            int column = (int) (entry.getKey() / (384 / minStepDenom))*2;
            NoteLinkedList.NoteNode copy = entry.getValue().header;
            boolean [] visited = new boolean[6];
            int count = 0;
            while (copy != null) {
                int[] temp = searchForNote(copy.data, visited, count);
                if(temp[0] == -1) {
                    tab[5][column] = "H";
                } else if (temp[0] == -2) {
                    tab[0][column] = "L";
                } else if (temp[0] == -3) {
                    tab[0][column] = "?";
                } else {
                    tab[temp[0]][column] = String.valueOf(temp[1]);
                }
                copy = copy.next;
                count++;
            }
        }
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if(tab[i][j] == null) {
                    try{
                        if((Integer.valueOf(tab[i][j-1])) < 10) {
                            tab[i][j] = "-";
                        } else {
                            tab[i][j] = "";
                        }
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        tab[i][j] = "-";
                    }  
                }
            }
        }
    }
    private int[] searchForNote(Note n, boolean[] visited, int count) {
        ArrayList<Integer[]> possibleNotes = new ArrayList<>();
        for (int i = noteMap.length-1; i > -1 ; i--) {
            for (int j = noteMap[i].length-1; j > -1; j--) {
                if (noteMap[i][j].equals(n.getNoteAsString()) && !visited(visited, i)) {
                    possibleNotes.add(new Integer[] { i, j });
                }
            }
        }
        Integer[] bestPos = new Integer[2];
        System.out.println(n.getNoteAsString());
        for(Integer[] arr : possibleNotes) {
            System.out.println(Arrays.toString(arr));
        }
        System.out.println("---");
        double minDist = Double.MAX_VALUE;
        for(Integer[] i : possibleNotes) {
            double curDist = pythagDist(center, i);
            if(curDist < minDist) {
                minDist = curDist;
                bestPos = i;
            }
        }
        if(bestPos[0] == null) {
            if(n.getOctave() <= 2) { 
                bestPos[0] = -2; 
            }
            else if(n.getOctave() >= 6) { 
                bestPos[0] = -1; 
            } else {
                bestPos[0] = -3;
            }
            bestPos[1] = -1;
        } else {
            visited[bestPos[0]] = true;
            if(linkedNotes) { center[0] = bestPos[0]; center[1] = bestPos[1]; }
        }
        return new int[] {bestPos[0], bestPos[1]};
    }

    private boolean visited(boolean[] visited, int guitarString) {
        return visited[guitarString];
    }

    private double pythagDist(int[] center, Integer[] pos) {
        return Math.sqrt(((pos[0]-center[0])*(pos[0]-center[0]))+((pos[1]-center[1])*(pos[1]-center[1])));
    }
}
