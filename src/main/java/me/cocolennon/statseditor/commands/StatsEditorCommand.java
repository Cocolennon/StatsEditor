package me.cocolennon.statseditor.commands;

import me.cocolennon.statseditor.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class StatsEditorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if (!sender.hasPermission("statseditor.info")) {
            sender.sendMessage("§3[§dStatsEditor§3]§f: §cYou can't do that!");
            return false;
        }

        sendInfo(sender);
        return true;
    }

    private void sendInfo(CommandSender sender){
        List<String> info = new LinkedList<>();
        info.add("§3§l=========================");
        info.add("§d§lStats Editor " + getPlugin(Main.class).getDescription().getVersion());
        if(Main.getUsingOldVersion()){
            info.add("§dAn update is available!");
        }else{
            info.add("§dYou're using the latest version");
        }
        info.add("§3§l=========================");

        info.forEach(sender::sendMessage);
    }
}
