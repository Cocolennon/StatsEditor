package me.cocolennon.statseditor.utils;

import me.cocolennon.statseditor.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    public static void log(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', "&3[&d" + Main.getPlugin(Main.class).getName() + "&3]&r " + msg);
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public static void debug(String msg) {
        log("&7[&eDEBUG&7]&r" + msg);
    }
}
