package me.nik.anticheatbase.commands.subcommands;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.commands.SubCommand;
import me.nik.anticheatbase.enums.MsgType;
import me.nik.anticheatbase.enums.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AlertsCommand extends SubCommand {

    private final Anticheat plugin;

    public AlertsCommand(Anticheat plugin) {
        this.plugin = plugin;
    }

    @Override
    protected String getName() {
        return "alerts";
    }

    @Override
    protected String getDescription() {
        return "Toggle the alerts";
    }

    @Override
    protected String getSyntax() {
        return "alerts";
    }

    @Override
    protected String getPermission() {
        return Permissions.COMMAND_ALERTS.getPermission();
    }

    @Override
    protected int maxArguments() {
        return 1;
    }

    @Override
    protected boolean canConsoleExecute() {
        return false;
    }

    @Override
    protected void perform(CommandSender sender, String[] args) {

        final UUID uuid = ((Player) sender).getUniqueId();

        if (this.plugin.getAlertManager().hasAlerts(uuid)) {

            this.plugin.getAlertManager().removePlayerFromAlerts(uuid);

            sender.sendMessage(MsgType.PREFIX.getMessage() + "You have disabled the Alerts");

        } else {

            this.plugin.getAlertManager().addPlayerToAlerts(uuid);

            sender.sendMessage(MsgType.PREFIX.getMessage() + "You have enabled the Alerts");
        }
    }
}