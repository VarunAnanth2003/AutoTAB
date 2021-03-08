package com.flns.autotab;
public class TABPrinter {
    public static String printTAB(TAB t) {
        String s = "";
        String[][] tab = t.getTab();
        String[] notesStrings = {"e", "B", "G", "D", "A", "E"};
        s+="TAB for " + t.getName() + "\n";
        s+="Each dash represents 1/" + t.getMinStepDenom()*2 + " of a measure at " + t.getBPM() + " BPM" +"\n";
        s+="This TAB is in the key of " + t.getKey() +"\n";
        int maxLength = tab[0].length;
        for (int i = 0; i < tab.length; i++) {
            if(tab[i].length > maxLength) {
                maxLength = tab[i].length;
            }
        }
        int buckets = t.getBuckets();
        int reps = (int)Math.ceil(((double)maxLength)/buckets);
        for (int i = 0; i < reps; i++) {
            for (int j = 0; j < tab.length; j++) {
                s+=notesStrings[j] + "|";
                for (int k = i*buckets; k < (i*buckets)+buckets; k++) {
                    try{
                        s+=tab[5-j][k];
                    } catch (IndexOutOfBoundsException ioobe) {
                        s+="-";
                    }
                }
                s+="\n";
            }
            s+="\n";
        }
        return s;
    }
}
