package dev.hytalemodding;

import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import dev.hytalemodding.commands.SetMoneyCommand;
import dev.hytalemodding.events.PlayerJoinListener;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hytalemodding.events.PlayerLeaveListener;

import javax.annotation.Nonnull;

public class EriEconomy extends JavaPlugin {
    private ComponentType<EntityStore, EconomyData> economyDataComponent;
    public static EriEconomy instance;
    private EconomySystem economySystem;

    public EriEconomy(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        // Register Economy Data Component
        this.economyDataComponent = this.getEntityStoreRegistry().registerComponent(
                EconomyData.class,
                "erieconomy:economy",
                EconomyData.CODEC
        );

        this.economySystem = new EconomySystem(this);

        // Register On Ready Event
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, new PlayerJoinListener(this)::onPlayerReady);
        this.getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, new PlayerLeaveListener(this)::onPlayerLeave);
        this.getCommandRegistry().registerCommand(new SetMoneyCommand("setmoney", "Example command",this));
    }

    public EconomySystem getEconomySystem() {return economySystem;}

    public ComponentType<EntityStore, EconomyData> getEconomyDataComponent(){
        return this.economyDataComponent;
    }
}