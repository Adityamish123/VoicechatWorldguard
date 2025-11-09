package me.yleoft.voicechatWorlguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public final class VoicechatWorlguard extends JavaPlugin {

    public static StateFlag wgFlag;

    public static VoicechatWorlguard instance;
    public static VoicechatWorlguard getInstance() {
        return instance;
    }

    @Nullable
    private WorldguardVoicechatPlugin vcPlugin;

    @Override
    public void onLoad() {
        try {
            if (getServer().getPluginManager().getPlugin("WorldGuard") instanceof WorldGuardPlugin wg) {
                try {
                    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
                    try {
                        StateFlag flag = new StateFlag("allow-voicechat", true);
                        registry.register(flag);
                        wgFlag = flag;
                    } catch (FlagConflictException | IllegalStateException e) {
                        Flag<?> existingFlag = registry.get("allow-voicechat");
                        if (existingFlag instanceof StateFlag) {
                            wgFlag = (StateFlag) existingFlag;
                        }
                    }
                } catch (Exception e) {
                    getLogger().severe("Failed to hook into WorldGuard properly.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Error hooking into WorldGuard");
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        BukkitVoicechatService vcservice = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (vcservice != null) {
            vcPlugin = new WorldguardVoicechatPlugin();
            vcservice.registerPlugin(vcPlugin);
            getLogger().info("Successfully registered voicechat worlguard extension");
        } else {
            getLogger().info("Failed to register voicechat worlguard extension: voicechat service not found");
        }
        new Metrics(this, 27929);
    }

    @Override
    public void onDisable() {
        if (vcPlugin != null) {
            getServer().getServicesManager().unregister(vcPlugin);
            getLogger().info("Successfully unregistered voicechat worlguard extension");
        }
    }
}
