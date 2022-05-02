package me.nik.anticheatbase.commands;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.commands.subcommands.AlertsCommand;
import me.nik.anticheatbase.enums.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A command manager class that we'll be using in order to make creating and using commands
 * Much easier and simple.
 */
public class CommandManager implements TabExecutor {

    private final List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(Anticheat plugin) {
        this.subCommands.add(new AlertsCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {

            for (SubCommand subCommand : this.subCommands) {

                if (args[0].equalsIgnoreCase(subCommand.getName())) {

                    if (!subCommand.canConsoleExecute() && sender instanceof ConsoleCommandSender) {
                        sender.sendMessage(MsgType.CONSOLE_COMMANDS.getMessage());
                        return true;
                    }

                    if (!sender.hasPermission(subCommand.getPermission())) {
                        sender.sendMessage(MsgType.NO_PERMISSION.getMessage());
                        return true;
                    }

                    subCommand.perform(sender, args);
                    return true;
                }

                if (args[0].equalsIgnoreCase("help")) {
                    helpMessage(sender);
                    return true;
                }
            }
        }

        helpMessage(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return this.subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());
        }

        return null;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(MsgType.PREFIX.getMessage() + ChatColor.WHITE + "Available Commands");
        sender.sendMessage("");

        this.subCommands.stream()
                .filter(subCommand -> sender.hasPermission(subCommand.getPermission()))
                .forEach(subCommand ->
                        sender.sendMessage(ChatColor.RED + subCommand.getSyntax() + ChatColor.DARK_GRAY + " - "
                                + ChatColor.GRAY + subCommand.getDescription()));

        sender.sendMessage("");
    }
}