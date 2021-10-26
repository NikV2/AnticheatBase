package me.nik.anticheatbase.utils.custom;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A custom concurrent sample list that we'll be using on our checks.
 */
public final class ConcurrentSampleList<T> extends ConcurrentLinkedDeque<T> {

    private final int sampleSize;
    private final boolean update;

    public ConcurrentSampleList(int sampleSize) {
        this.sampleSize = sampleSize;
        this.update = false;
    }

    public ConcurrentSampleList(int sampleSize, boolean update) {
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

    public boolean isCollected() {
        return super.size() >= this.sampleSize;
    }
}