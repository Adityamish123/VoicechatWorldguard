package me.yleoft.voicechatWorlguard;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class WorldguardVoicechatPlugin implements VoicechatPlugin {

    public static final String id = "voicechat_worldguard";
    public static Permission bypass_permission = new Permission("voicechatworlguard.bypass", PermissionDefault.OP);

    @Override
    public String getPluginId() {
        return id;
    }

    @Override
    public void initialize(VoicechatApi api) {
        VoicechatWorlguard.getInstance().getLogger().info("Worldguard voicechat plugin initialized by voicechat API");
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophone);
    }

    private void onMicrophone(MicrophonePacketEvent event) {
        if(
                event.getSenderConnection() == null ||
                !(event.getSenderConnection().getPlayer().getPlayer() instanceof Player player) ||
                player.hasPermission(bypass_permission)
        ) {
            return;
        }

        try {
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

            boolean canBypass = WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(localPlayer, localPlayer.getWorld());
            if (canBypass) return;

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(localPlayer.getLocation());

            if(!set.testState(localPlayer, VoicechatWorlguard.wgFlag)) {
                event.cancel();
            };
        }catch (Exception e) {
            VoicechatWorlguard.getInstance().getLogger().severe("Error checking WorldGuard regions for voicechat:");
        }
    }

}
