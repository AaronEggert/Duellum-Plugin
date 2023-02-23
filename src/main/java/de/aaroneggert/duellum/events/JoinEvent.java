package de.aaroneggert.duellum.events;

import jdk.javadoc.internal.tool.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class JoinEvent implements Listener {
    private Main main;

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean hasPlayed = player.hasPlayedBefore();


    }

}
