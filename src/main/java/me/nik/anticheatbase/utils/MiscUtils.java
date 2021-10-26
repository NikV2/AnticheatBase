package me.nik.anticheatbase.utils;

import com.google.common.base.Charsets;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class MiscUtils {

    private MiscUtils() {
    }

    public static YamlConfiguration loadConfigurationUTF_8(final File file) {

        final YamlConfiguration config = new YamlConfiguration();

        try {

            final FileInputStream stream = new FileInputStream(file);

            config.load(new InputStreamReader(stream, Charsets.UTF_8));

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }

    @SuppressWarnings("unchecked")
    public static <E> E randomElement(final Collection<? extends E> collection) {
        if (collection.size() == 0) return null;

        int index = new Random().nextInt(collection.size());

        if (collection instanceof List) {

            return ((List<? extends E>) collection).get(index);

        } else {

            Iterator<? extends E> iter = collection.iterator();

            for (int i = 0; i < index; i++) iter.next();

            return iter.next();
        }
    }

    public static void consoleCommand(final String command) {
        if (command.isEmpty()) return;

        if (Bukkit.isPrimaryThread()) {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

        } else {

            TaskUtils.task(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
        }
    }
}