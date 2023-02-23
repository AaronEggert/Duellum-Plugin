package de.aaroneggert.duellum.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.aaroneggert.duellum.database.Database;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.*;

public class TeamCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("team")) return true;

        if (args.length == 0) {
            sender.sendMessage("Befahl kann nicht leer sein!");
            return true;
        }

        switch (args[0]) {
            case "create":
                sender.sendMessage("Create");

                String teamName = args[1];
                String teamTag = args[2];

                if (teamName == null) {
                    sender.sendMessage("Du musst einen Teamnamen angeben");
                    return true;
                }
                if (teamTag == null) {
                    sender.sendMessage("Du musst einen Teamtag angeben");
                    return true;
                }
                if (4 > teamName.length() || 12 < teamName.length()) {
                    sender.sendMessage("Teamname muss zwischen 4 und 12 Zeichen lang sein");
                    return true;
                }
                if (teamTag.length() < 3 || teamTag.length() > 4) {
                    sender.sendMessage("Teamtag muss zwischen 3 und 4 zeichen lang sein");
                    return true;
                }

                Connection dbCon = Database.getConnection();

                getLogger().info(teamName);
                getLogger().info(teamTag);


                try {
                    String sql = "SELECT * FROM teams where name = ?;";
                    PreparedStatement statement = dbCon.prepareStatement(sql);
                    statement.setString(1, teamName);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        sender.sendMessage(ChatColor.RED + "Teamname '" + teamName + "' ist leider schon vergeben");
                        return true;
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    String sql = "SELECT * FROM teams where tag = ?;";
                    PreparedStatement statement = dbCon.prepareStatement(sql);
                    statement.setString(1, teamTag);
                    ResultSet result = statement.executeQuery();
                    if (result.next()) {
                        sender.sendMessage(ChatColor.RED + "Teamtag '" + teamTag + "' ist leider schon vergeben");
                        return true;
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                try {
                    String sql = "INSERT INTO teams (name, tag) VALUES (?, ?);";
                    PreparedStatement statement = dbCon.prepareStatement(sql);
                    statement.setString(1, teamName);
                    statement.setString(2, teamTag);

                    statement.execute();

                    sender.sendMessage(ChatColor.GREEN + "Team erfolgreich erstellt");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return true;

            case "list":
                dbCon = Database.getConnection();
                try {
                    PreparedStatement statement = dbCon.prepareStatement("SELECT * FROM teams;");
                    ResultSet result = statement.executeQuery();

                    ArrayList<JsonObject> res = new ArrayList<>();

                    while (result.next()) {
                        JsonObject item = new JsonObject();
                        item.addProperty("name", result.getString("name"));
                        item.addProperty("tag", result.getString("tag"));
                        res.add(item);
                    }

                    sender.sendMessage(ChatColor.GOLD + String.valueOf(res.size()) + ChatColor.WHITE + " Teams gefunden:");

                    for (JsonObject team : res) {
                        sender.sendMessage("[" + team.get("tag").getAsString() + "] " + team.get("name").getAsString());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                return true;

            case "add":
                String playerName = args[1];
                teamName = args[2];

                dbCon = Database.getConnection();

                Player player =  getPlayer(playerName);
                assert player != null;
                PreparedStatement statement = dbCon.prepareStatement("SELECT * FROM ")


                return true;

            default:
                sender.sendMessage(args[0] + " ist kein Valider Befehl");
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin sollte funktionieren");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> completed = new ArrayList<>();

        if (args.length == 0) return null;

        if (args.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            list.add("create");
            list.add("list");
            list.add("add");
            return list;
        }

        getLogger().info(String.valueOf(args.length));

        if (args[1] != null && args[0].equals("add")) {
            if (args.length == 2) {
                ArrayList<String> players = new ArrayList<>();

                for (Player p : getOnlinePlayers()) {
                    players.add(p.getName());
                }

                return players;
            }

        }
        return null;
    }
}
