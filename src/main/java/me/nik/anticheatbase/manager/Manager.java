package me.nik.anticheatbase.manager;

import lombok.Getter;
import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.utils.Initializer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public abstract class Manager<T> implements Initializer {
    private final List<T> list = new CopyOnWriteArrayList<>();

    public Manager() {
        // Check if this class implements Listener
        if (this instanceof Listener) {
            // Register the listener in that case.
            Bukkit.getServer().getPluginManager().registerEvents((Listener) this, Anticheat.getInstance());
        }
    }

    public boolean add(T t) {
        return list.add(t);
    }

    public void remove(T t) {
        list.remove(t);
    }

    public T remove(int index) {
        return list.remove(index);
    }

    public boolean contains(T t) {
        return list.contains(t);
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }
}
