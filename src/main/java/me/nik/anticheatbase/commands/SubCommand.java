package me.nik.anticheatbase.commands;

import org.bukkit.command.CommandSender;

/**
 * A subcommand class that we'll be using to extend on our commands
 */
public abstract class SubCommand {

    /**
     * @return The command's name
     */
    protected abstract String getName();

    /**
     * @return The command's description
     */
    protected abstract String getDescription();

    /**
     * @return The command's syntax
     */
    protected abstract String getSyntax();

    /**
     * @return The permission required in order to run this command
     */
    protected abstract String getPermission();

    /**
     * @return The maximum arguments for this command
     */
    protected abstract int maxArguments();

    /**
     * @return Whether this command can be executed through the console
     */
    protected abstract boolean canConsoleExecute();

    /**
     * The method that will be run once the command is executed
     *
     * @param sender Sender
     * @param args   Args
     */
    protected abstract void perform(CommandSender sender, String[] args);
}