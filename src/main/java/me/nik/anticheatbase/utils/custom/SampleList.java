package me.nik.anticheatbase.utils.custom;

import java.util.LinkedList;

/**
 * A custom sample list that we'll be using on our checks.
 */
public final class SampleList<T> extends LinkedList<T> {

    private final int sampleSize;
    private final boolean update;

    public SampleList(int sampleSize) {
        this.sampleSize = sampleSize;
        this.update = false;
    }

    public SampleList(int sampleSize, boolean update) {
        this.sampleSize = sampleSize;
        this.update = update;
    }

    @Override
    public boolean add(T t) {
        if (isCollected()) {
            if (this.update) {
                super.removeFirst();
            } else super.clear();
        }

        return super.add(t);
    }

    public int getMaxSize() {
        return sampleSize;
    }

    public boolean isCollected() {
        return super.size() >= this.sampleSize;
    }
}