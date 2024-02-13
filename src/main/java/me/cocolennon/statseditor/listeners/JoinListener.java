package me.cocolennon.statseditor.listeners;

import me.cocolennon.statseditor.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Main.getInstance().checkVersion();
        Player player = event.getPlayer();
        if(!player.isOp()) return;
        if(Main.getInstance().getUsingOldVersion()) {
            player.sendMessage("§3[§dStatsEditor§3] §aYou are using an older version of Stats Editor, please update to version " + Main.getInstance().getLatestVersion());
        }
    }
}
