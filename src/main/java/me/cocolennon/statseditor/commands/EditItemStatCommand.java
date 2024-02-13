package me.cocolennon.statseditor.commands;

import me.cocolennon.statseditor.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

public class EditItemStatCommand implements TabExecutor {
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

    List<String> math = Arrays.asList("add", "subtract", "multiply", "divide");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("§3[§dStatsEditor§3] §cUsage: /"+label+" <player> <stat name> <mob name> <value>");
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
            sender.sendMessage("§3[§dStatsEditor§3] §cYou must input an item to set the statistic of.");
            return false;
        }

        if(args.length < 4) {
            sender.sendMessage("§3[§dStatsEditor§3] §cYou must input a value to set the statistic at.");
            return false;
        }

        String statisticName = args[1].substring(10).toUpperCase();
        Statistic statistic = Statistic.valueOf(statisticName);
        if(mobStatistics.contains(statistic)) {
            player.sendMessage("§3[§dStatsEditor§3] §cThe statistic you put in need to be used with /edit-mob-stat.");
            return false;
        }

        String materialName = args[2].substring(10).toUpperCase();
        Material material = Material.valueOf(materialName);
        if(!itemStatistics.contains(statistic)) {
            player.sendMessage("§3[§dStatsEditor§3] §cThe statistic you put in need to be used with /edit-mob-stat.");
            return false;
        }

        int newValue = Integer.parseInt(args[3]);
        int oldValue = player.getStatistic(statistic, material);
        if(args.length == 5) {
            switch(args[4]) {
                case "add" -> newValue += oldValue;
                case "subtract" -> newValue = oldValue - newValue;
                case "multiply" -> newValue *= oldValue;
                case "divide" -> newValue = oldValue / newValue;
                default -> {
                    player.sendMessage("§3[§dStatsEditor§3] §c" + args[4] + " isn't an operation!");
                    return false;
                }
            }
        }

        try {
            player.setStatistic(statistic, material, newValue);
        }catch(IllegalArgumentException error){
            player.sendMessage("§3[§dStatsEditor§3] §cEither statistic or the item you put in does not exist.");
            return false;
        }

        sender.sendMessage("§3[§dStatsEditor§3] §aStatistic §d" + statistic.name().replaceAll("_", " ").toLowerCase() + "§a for §d" + material.name().replaceAll("_", " ").toLowerCase() + " §ahas successfully been edited from §d" + oldValue + " §ato §d" + newValue);
        Logger.log("§a"+sender.getName()+" has set "+player.getName()+"'s "+statisticName+" stat for "+materialName+" to "+args[3]);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return null;
        if(args.length == 2) {
            List<Statistic> allStatistics = itemStatistics;
            List<String> statistics = new LinkedList<>();
            final List<String> completions = new ArrayList<>();
            for(Statistic current : allStatistics) {
                statistics.add("minecraft:" + current.name().toLowerCase());
            }
            StringUtil.copyPartialMatches(args[1], statistics, completions);
            return completions;
        }else if(args.length == 3) {
            Material[] allMaterials = Material.values();
            List<String> materials = new LinkedList<>();
            final List<String> completions = new ArrayList<>();
            for(Material current : allMaterials) {
                materials.add("minecraft:" + current.name().toLowerCase());
            }
            StringUtil.copyPartialMatches(args[2], materials, completions);
            return completions;
        }else if(args.length == 4) {
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[3], math, completions);
            return completions;
        }
        return null;
    }
}
