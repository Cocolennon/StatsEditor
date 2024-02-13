package me.cocolennon.statseditor.commands;

import me.cocolennon.statseditor.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EditStatCommand implements TabExecutor {
    List<Statistic> itemStatistics = Arrays.asList(
            Statistic.MINE_BLOCK,
            Statistic.BREAK_ITEM,
            Statistic.CRAFT_ITEM,
            Statistic.USE_ITEM,
            Statistic.PICKUP,
            Statistic.DROP
    );

    List<Statistic> mobStatistics = Arrays.asList(
            Statistic.KILL_ENTITY,
            Statistic.ENTITY_KILLED_BY
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("§3[§dStatsEditor§3] §cUsage: /"+label+" <player> <stat name> <value>");
            return false;
        }

        Player player = Bukkit.getServer().getPlayer(args[0]);
        if(player == null) {
            sender.sendMessage("§3[§dStatsEditor§3] §cPlayer is either not online or does not exist.");
            return false;
        }

        if(args.length < 2) {
            sender.sendMessage("§3[§dStatsEditor§3] §cYou must input a statistic to edit.");
            return false;
        }

        if(args.length < 3) {
            sender.sendMessage("§3[§dStatsEditor§3] §cYou must input a value to set the statistic at.");
            return false;
        }

        String statisticName = args[1].substring(10).toUpperCase();
        Statistic statistic = Statistic.valueOf(statisticName);
        if(itemStatistics.contains(statistic)) {
            player.sendMessage("§3[§dStatsEditor§3] §cThe statistic you put in needs to be used with /edit-item-stat.");
            return false;
        }

        if(mobStatistics.contains(statistic)) {
            player.sendMessage("§3[§dStatsEditor§3] §cThe statistic you put in needs to be used with /edit-mob-stat.");
            return false;
        }

        try {
            player.setStatistic(statistic, Integer.parseInt(args[2]));
        }catch(IllegalArgumentException error){
            player.sendMessage("§3[§dStatsEditor§3] §cThe statistic you put in does not exist.");
            return false;
        }

        sender.sendMessage("§3[§dStatsEditor§3] §aStatistic has successfully been edited.");
        Logger.log("§a"+sender.getName()+" has set "+player.getName()+"'s "+statisticName+" stat to "+args[2]);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return null;
        if(args.length == 2) {
            Statistic[] allStatistics = Statistic.values();
            List<String> statistics = new LinkedList<>();
            final List<String> completions = new ArrayList<>();
            for (Statistic current : allStatistics) {
                statistics.add("minecraft:" + current.name().toLowerCase());
            }
            StringUtil.copyPartialMatches(args[1], statistics, completions);
            return completions;
        }
        return null;
    }
}
