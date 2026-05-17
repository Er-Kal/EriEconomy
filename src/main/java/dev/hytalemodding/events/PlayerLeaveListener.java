package dev.hytalemodding.events;

import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import dev.hytalemodding.EriEconomy;

public class PlayerLeaveListener {
    private final EriEconomy plugin;

    public PlayerLeaveListener(EriEconomy plugin){
        this.plugin = plugin;
    }

    public void onPlayerLeave(PlayerDisconnectEvent event) {
        PlayerRef player = event.getPlayerRef();

        plugin.getEconomySystem().removeHUD(player);
    }
}