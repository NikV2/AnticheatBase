package me.nik.anticheatbase.files;

import me.nik.anticheatbase.utils.MiscUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Lang {

    private final JavaPlugin plugin;
    private FileConfiguration data;

    public Lang(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration get() {
        return data;
    }

    public void setup() {

        File file = new File(plugin.getDataFolder(), "lang.yml");

        try {

            file.createNewFile();

        } catch (IOException ignored) {
        }

        this.data = MiscUtils.loadConfigurationUTF_8(file);

        write();

        this.data.options().copyDefaults(true);

        try {

            this.data.save(file);

        } catch (IOException ignored) {
        }
    }

    public void write() {
        get().addDefault("prefix", "&8「&cAnticheat&8」&7»&r ");
        get().addDefault("no_perm", "&cYou do not have permission to do that!");
        get().addDefault("console_commands", "&c&lYou cannot run this command through the console :(");
        get().addDefault("alert_message", "&7%player% &ffailed &c%check% &fx%vl%");
        get().addDefault("alert_hover",
                Arrays.asList(
                        "&7Description:&r",
                        "%description%",
                        "",
                        "&7Information:&r",
                        "%information%",
                        "",
                        "&7TPS: &r%tps%",
                        "",
                        "&fClick to teleport"
                ));
    }
}