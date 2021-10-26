package me.nik.anticheatbase.manager.impl;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.utils.ChatUtils;

import java.util.List;

/**
 * A message type enumerations class in order to cache our messages from our lang.yml and easily grab them
 */
public enum MsgType {
    PREFIX(ChatUtils.format(Anticheat.getInstance().getLang().getString("prefix"))),
    NO_PERMISSION(PREFIX.getMessage() + ChatUtils.format(Anticheat.getInstance().getLang().getString("no_perm"))),
    CONSOLE_COMMANDS(PREFIX.getMessage() + ChatUtils.format(Anticheat.getInstance().getLang().getString("console_commands"))),
    ALERT_MESSAGE(PREFIX.getMessage() + ChatUtils.format(Anticheat.getInstance().getLang().getString("alert_message"))),
    ALERT_HOVER(stringFromList(Anticheat.getInstance().getLang().getStringList("alert_hover")));

    private final String message;

    MsgType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private static String stringFromList(List<String> list) {

        StringBuilder sb = new StringBuilder();

        int size = list.size();

        for (int i = 0; i < size; i++) {

            sb.append(list.get(i));

            if (size - 1 != i) sb.append("\n");
        }

        return ChatUtils.format(sb.toString());
    }
}