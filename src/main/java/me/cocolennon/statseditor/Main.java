package me.cocolennon.statseditor;

import me.cocolennon.statseditor.commands.EditMobStatCommand;
import me.cocolennon.statseditor.commands.EditItemStatCommand;
import me.cocolennon.statseditor.commands.EditStatCommand;
import me.cocolennon.statseditor.commands.StatsEditorCommand;
import me.cocolennon.statseditor.listeners.JoinListener;
import me.cocolennon.statseditor.utils.Logger;
import me.cocolennon.statseditor.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    public static String version;
    public static String latestVersion;
    public static boolean usingOldVersion = false;

    @Override
    public void onEnable() {
        instance = this;
        //checkVersion();
        registerCommands();
        Logger.log("&aPlugin enabled!");
    }

    public void onDisable() {
        Logger.log("&cPlugin disabled!");
    }

    /*public void checkVersion() {
        new UpdateChecker(this, this, 111167).getVersion(cVersion -> {
            version = this.getDescription().getVersion();
            latestVersion = cVersion;
            if (!getVersion().equals(cVersion)) {
                getLogger().info("You are using an older version of Stats Editor, please update to version " + cVersion);
                usingOldVersion = true;
            }
        });
    }*/

    private void registerCommands() {
        getCommand("statseditor").setExecutor(new StatsEditorCommand());
        getCommand("edit-stat").setExecutor(new EditStatCommand());
        getCommand("edit-item-stat").setExecutor(new EditItemStatCommand());
        getCommand("edit-mob-stat").setExecutor(new EditMobStatCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public String getVersion() { return version; }

    public String getLatestVersion(){ return latestVersion; }

    public boolean getUsingOldVersion() { return usingOldVersion; }

    public static Main getInstance() {
        return instance;
    }
}
