package me.nik.anticheatbase.manager;

// NOTE: I don't know of a much better name to call this.
// Serves to purpose to store these two methods for multiple classes
// to extend - Vaziak
public interface Initializer {
    void init();
    void shutdown();
}