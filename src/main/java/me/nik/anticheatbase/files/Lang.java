package me.nik.anticheatbase.files;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.manager.Initializer;
import me.nik.anticheatbase.utils.MiscUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Lang implements Initializer {

    private final Anticheat plugin;
    private FileConfiguration data;

    public Lang(Anticheat plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration get() {
        return data;
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

    @Override
    public void init() {

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

    @Override
    public void shutdown() {
    }
}