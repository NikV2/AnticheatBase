package me.nik.anticheatbase.managers.themes.impl;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.managers.themes.BaseTheme;

import java.util.Arrays;

public class DefaultTheme extends BaseTheme {
    public DefaultTheme(Anticheat plugin, String themeName) {
        super(plugin, themeName);
    }

    @Override
    public void create() {
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