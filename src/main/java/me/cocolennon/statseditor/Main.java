package me.cocolennon.statseditor;

import me.cocolennon.statseditor.commands.EditMobStatCommand;
import me.cocolennon.statseditor.commands.EditItemStatCommand;
import me.cocolennon.statseditor.commands.EditStatCommand;
import me.cocolennon.statseditor.commands.StatsEditorCommand;
import me.cocolennon.statseditor.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static String version;
    public static String latestVersion;
    public static boolean usingOldVersion = false;

    @Override
    public void onEnable() {
        //checkVersion();
        registerCommands();
        Logger.log("&aPlugin enabled!");
    }

    public void onDisable() {
        Logger.log("&cPlugin disabled!");
    }

    /*public void checkVersion() {
        new UpdateChecker(this, this, 105225).getVersion(cVersion -> {
            version = this.getDescription().getVersion();
            latestVersion = cVersion;
            if (!getVersion().equals(cVersion)) {
                getLogger().info("You are using an older version of Animated Nicknames, please update to version " + cVersion);
                usingOldVersion = true;
            }
        });
    }*/

    public void registerCommands() {
        getCommand("statseditor").setExecutor(new StatsEditorCommand());
        getCommand("edit-stat").setExecutor(new EditStatCommand());
        getCommand("edit-item-stat").setExecutor(new EditItemStatCommand());
        getCommand("edit-mob-stat").setExecutor(new EditMobStatCommand());
    }

    public static String getVersion() { return version; }

    public static String getLatestVersion(){ return latestVersion; }

    public static boolean getUsingOldVersion() { return usingOldVersion; }
}
