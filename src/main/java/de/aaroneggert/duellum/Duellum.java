package de.aaroneggert.duellum;

import com.google.common.util.concurrent.Service;
import de.aaroneggert.duellum.commands.TeamCommand;
import de.aaroneggert.duellum.commands.TestCommand;
import de.aaroneggert.duellum.database.Database;
import de.aaroneggert.duellum.events.JoinEvent;
import org.bukkit.Warning;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Objects;

public final class Duellum extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //getCommand("test").setExecutor(new TestCommand());
        Objects.requireNonNull(getCommand("team")).setExecutor(new TeamCommand());
        //getCommand("team").setTabCompleter(new TeamCommand());

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);

        try {
            Database.connect();
            getLogger().info("Successfully connected to Database");
        } catch (SQLException e) {
            getLogger().warning("Connection to Database failed: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            Database.closeConnection();
            getLogger().info("Closed Database");
        } catch (SQLException e) {
            getLogger().warning("Error closing Database: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
