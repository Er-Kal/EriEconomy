package dev.hytalemodding.events;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import dev.hytalemodding.EriEconomy;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import dev.hytalemodding.ui.BalanceHUD;

public class PlayerJoinListener {
    private final EriEconomy plugin;

    public PlayerJoinListener(EriEconomy plugin){
        this.plugin = plugin;
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(Message.raw("Welcome " + player.getDisplayName()));

        var ref = player.getReference();
        var playerStore = ref.getStore();

        PlayerRef playerRef = playerStore.getComponent(ref, PlayerRef.getComponentType());


        playerStore.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());

        var hud = new BalanceHUD(playerRef);
        player.getHudManager().setCustomHud(playerRef,hud);
        plugin.getEconomySystem().registerHUD(playerRef, hud);
    }
}