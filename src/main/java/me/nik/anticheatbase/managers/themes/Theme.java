package me.nik.anticheatbase.managers.themes;

import me.nik.anticheatbase.utils.MiscUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class Theme {

    private final File file;
    private final String name;
    private FileConfiguration config;

    public Theme(File file) {
        this.file = file;
        this.name = file.getName().replace(".yml", "");
        reload();
    }

    public void reload() {
        try {
            this.config = MiscUtils.loadConfigurationUTF_8(this.file);
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getAuthor() {
        return this.config.getString("theme_author");
    }

    public String getPrefix() {
        return this.config.getString("prefix");
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public String getString(String path) {
        return this.config.getString(path, "null");
    }

    public String getThemeName() {
        return this.name;
    }
}