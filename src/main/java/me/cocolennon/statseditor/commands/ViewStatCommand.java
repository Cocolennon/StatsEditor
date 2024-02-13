package me.cocolennon.statseditor.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewStatCommand implements TabExecutor {
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
        if(!(sender instanceof Player player)) return false;
        if (!sender.hasPermission("statseditor.view-stat")) {
            sender.sendMessage("§3[§dStatsEditor§3] §cYou can't do that!");
            return false;
        }
        if(args.length == 0) {
            sender.sendMessage("§3[§dStatsEditor§3] §cPlease provide a player and a statistic to view!");
            return false;
        }else if(args.length == 1){
            sender.sendMessage("§3[§dStatsEditor§3] §cPlease provide a statistic to view!");
            return false;
        }
        Statistic stat;
        try {
            stat = Statistic.valueOf(args[1]);
        }catch(IllegalArgumentException exception) {
            sender.sendMessage("§3[§dStatsEditor§3] §cPlease provide a valid statistic!");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage("§3[§dStatsEditor§3] §cPlease provide a valid player!");
            return false;
        }
        int statValue;
        if(itemStatistics.contains(stat)) {
            if(args.length == 2) {
                sender.sendMessage("§3[§dStatsEditor§3] §cPlease provide an item for this statistic!");
                return false;
            }
            Material item = Material.valueOf(args[2].substring(10).toUpperCase());
            statValue = target.getStatistic(stat, item);
            player.sendMessage("§3[§dStatsEditor§3] §aStatistic §d" + stat.name().replaceAll("_", " ").toLowerCase() + " §afor §d" + item.name().replaceAll("_", " ").toLowerCase() + " of §d" + target.getName() + " §ais at §d" + statValue);
            return true;
        }else if(mobStatistics.contains(stat)) {
            if(args.length == 2) {
                sender.sendMessage("§3[§dStatsEditor§3] §cPlease provide a mob for this statistic!");
                return false;
            }
            EntityType mob = EntityType.valueOf(args[2].substring(10).toUpperCase());
            statValue = target.getStatistic(stat, mob);
            player.sendMessage("§3[§dStatsEditor§3] §aStatistic §d" + stat.name().replaceAll("_", " ").toLowerCase() + " §afor §d" + mob.name().replaceAll("_", " ").toLowerCase() + " of §d" + target.getName() + " §ais at §d" + statValue);
            return true;
        }else statValue = target.getStatistic(stat);

        player.sendMessage("§3[§dStatsEditor§3] §aStatistic §d" + stat.name().replaceAll("_", " ").toLowerCase() + " §a of §d" + target.getName() + " §ais at §d" + statValue);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return null;
        if(args.length == 2) {
            List<String> statistics = new ArrayList<>();
            for(Statistic statistic : Statistic.values()) statistics.add(statistic.name());
            List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(args[1], statistics, completions);
            return completions;
        }else if(args.length == 3) {
            Statistic stat;
            try {
                stat = Statistic.valueOf(args[1]);
            }catch(IllegalArgumentException ignored) { return null; }
            if(itemStatistics.contains(stat)) {
                List<String> items = new ArrayList<>();
                for(Material material : Material.values()) items.add(material.name());
                List<String> completions = new ArrayList<>();
                StringUtil.copyPartialMatches(args[2], items, completions);
                return completions;
            }else if(mobStatistics.contains(stat)){
                List<String> mobs = new ArrayList<>();
                for(EntityType material : EntityType.values()) mobs.add(material.name());
                List<String> completions = new ArrayList<>();
                StringUtil.copyPartialMatches(args[2], mobs, completions);
                return completions;
            }else return null;
        }
        return null;
    }
}
