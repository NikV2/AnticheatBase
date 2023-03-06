package me.nik.anticheatbase.utils;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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

    public static final ItemStack EMPTY_ITEM = new ItemStack(Material.AIR);

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

    /**
     * Initialize static fields inside the specified classes.
     *
     * @param paths The class path
     * @throws ClassNotFoundException if one of the classes is not present
     */
    public static void initializeClasses(final String... paths) throws ClassNotFoundException {
        for (final String path : paths) Class.forName(path);
    }

    public static String wrapString(final String str, int wrapLength) {
        if (str == null) {

            return null;

        } else {

            if (wrapLength < 1) wrapLength = 1;

            int inputLineLength = str.length();

            int offset = 0;

            StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);

            while (inputLineLength - offset > wrapLength) {

                if (str.charAt(offset) == ' ') {

                    offset++;

                } else {

                    int spaceToWrapAt = str.lastIndexOf(32, wrapLength + offset);

                    if (spaceToWrapAt >= offset) {

                        wrappedLine.append(str, offset, spaceToWrapAt);

                        wrappedLine.append(SystemUtils.LINE_SEPARATOR);

                        offset = spaceToWrapAt + 1;

                    } else {

                        spaceToWrapAt = str.indexOf(32, wrapLength + offset);

                        if (spaceToWrapAt >= 0) {

                            wrappedLine.append(str, offset, spaceToWrapAt);

                            wrappedLine.append(SystemUtils.LINE_SEPARATOR);

                            offset = spaceToWrapAt + 1;

                        } else {

                            wrappedLine.append(str.substring(offset));

                            offset = inputLineLength;
                        }
                    }
                }
            }

            wrappedLine.append(str.substring(offset));

            return wrappedLine.toString();
        }
    }

    public static String capitalizeFirstLetter(final String data) {

        final char firstLetter = Character.toTitleCase(data.substring(0, 1).charAt(0));

        final String restLetters = data.substring(1).toLowerCase();

        return firstLetter + restLetters;
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

    public static void consoleCommand(final Collection<String> commands) {
        if (commands == null || commands.size() == 0) return;

        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if (Bukkit.isPrimaryThread()) {

            commands.forEach(command -> Bukkit.dispatchCommand(console, command));

        } else {

            TaskUtils.task(() -> commands.forEach(command -> Bukkit.dispatchCommand(console, command)));
        }
    }
}