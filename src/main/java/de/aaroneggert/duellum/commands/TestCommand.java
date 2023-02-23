package de.aaroneggert.duellum.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.*;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("test")) {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin sollte funktionieren");
        }

        return true;
    }
}
