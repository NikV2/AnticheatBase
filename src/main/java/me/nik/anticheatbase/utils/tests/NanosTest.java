package me.nik.anticheatbase.utils.tests;

public class NanosTest {

    private final long start;

    public NanosTest() {
        this.start = System.nanoTime();
    }

    public long getNanos() {
        return System.nanoTime() - this.start;
    }
}