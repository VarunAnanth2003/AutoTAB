package com.flns.autotab;
public class NoteLinkedList {
    public NoteNode header;
    public int size;

    public NoteLinkedList() {
        header = new NoteNode();
    }

    public NoteLinkedList(NoteNode header) {
        this.header = header;
        size = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    public NoteNode getFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("List is empty, no first element");
        } else {
            return header;
        }
    }

    public NoteNode getLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("List is empty, no last element");
        } else if (size == 1) {
            return header;
        } else {
            NoteNode copy = header;
            while (copy.next != null) {
                copy = copy.next;
            }
            return copy;
        }
    }

    public NoteNode getIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds for size " + size);
        }
        if (index == 0) {
            return header;
        } else {
            NoteNode copy = header;
            for (int i = 0; i < index - 1; i++) {
                copy = copy.next;
            }
            return copy.next;
        }
    }

    public NoteNode removeIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds for size " + size);
        }
        if (index == 0) {
            NoteNode n = header;
            getIndex(size - 1).next = header.next;
            header = header.next;
            size--;
            return n;
        } else {
            NoteNode copy = header;
            for (int i = 0; i < index - 1; i++) {
                copy = copy.next;
            }
            NoteNode n = copy.next;
            copy.next = n.next;
            size--;
            return n;
        }
    }

    public void addAtIndex(NoteNode n, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds for size " + size);
        }
        if (index == 0) {
            n.next = header;
        } else {
            NoteNode copy = header;
            for (int i = 0; i < index - 1; i++) {
                copy = copy.next;
            }
            NoteNode temp = copy.next;
            copy.next = n;
            while (copy.next != null) {
                copy = copy.next;
            }
            copy.next = temp;
        }
        size++;
    }

    public void addAtEnd(NoteNode n) {
        if (size == 0) {
            header = n;
        } else {
            NoteNode copy = header;
            while (copy.next != null) {
                copy = copy.next;
            }
            copy.next = n;
        }
        size++;
    }

    public void addAtHead(NoteNode n) {
        if (size == 0) {
            header = n;
        } else {
            n.next = header;
            header = n;
        }
        size++;
    }

    public void changeAllOctaves(int amount) {
        NoteNode copy = header;
        if (amount < 0) {
            while (copy != null) {
                for (int i = 0; i < -amount; i++) {
                    copy.data.reduceOctave();
                }
                copy = copy.next;
            }
        } else {
            while (copy != null) {
                for (int i = 0; i < amount; i++) {
                    copy.data.reduceOctave();
                }
                copy = copy.next;
            }
        }
    }

    public String toString() {
        NoteNode copy = header;
        String ret_val = "[";
        while (copy != null) {
            ret_val += copy.data.toString() + ", ";
            copy = copy.next;
        }
        return ret_val.substring(0, ret_val.length() - 2) + "]";
    }

    static class NoteNode {
        public Note data;
        public NoteNode next;

        public NoteNode() {
            this.data = null;
            this.next = null;
        }

        public NoteNode(Note data) {
            this.data = data;
            this.next = null;
        }

        public NoteNode(Note data, NoteNode next) {
            this.data = data;
            this.next = next;
        }
    }
}
