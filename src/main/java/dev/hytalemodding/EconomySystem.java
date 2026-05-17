package dev.hytalemodding;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hytalemodding.ui.BalanceHUD;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class EconomySystem {
    private final EriEconomy plugin;
    private final Map<PlayerRef, BalanceHUD> hudMap = new HashMap<>();
    public EconomySystem(EriEconomy plugin){
        this.plugin = plugin;
    }
    // Add money to the user
    public void addMoney(@Nonnull Ref<EntityStore> ref, double amount){
        var playerStore = ref.getStore();
        var data = playerStore.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        data.addBalance(amount);
        updateHud(ref);
    }
    // Set the user's money
    public void setMoney(@Nonnull Ref<EntityStore> ref, double amount){
        var playerStore = ref.getStore();
        var data = playerStore.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        data.setBalance(amount);
        updateHud(ref);
    }
    // Process transactions (purchase math logic)
    public boolean processTransaction(@Nonnull Ref<EntityStore> ref, double amount){
        var playerStore = ref.getStore();
        var data = playerStore.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        if (data.getBalance()>=amount){
            data.addBalance(-amount);
            updateHud(ref);
            return true;
        }
        else{
            return false;
        }
    }
    // Return the user's balance
    public double getMoney(@Nonnull Ref<EntityStore> ref){
        var playerStore = ref.getStore();
        var data = playerStore.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        return data.getBalance();
    }

    private void updateHud(Ref<EntityStore> ref){
        PlayerRef player = ref.getStore().getComponent(ref,PlayerRef.getComponentType());
        BalanceHUD hud = hudMap.get(player);
        if (hud!= null){
            hud.setBalance(getMoney(ref));
        }
    }

    // Entity Event Systems (Command Buffer based)

    public void addMoney(@Nonnull CommandBuffer<EntityStore> cb, @Nonnull Ref<EntityStore> ref, double amount){
        var data = cb.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        data.addBalance(amount);
        updateHud(cb, ref);
    }

    public void setMoney(@Nonnull CommandBuffer<EntityStore> cb, @Nonnull Ref<EntityStore> ref, double amount){
        var data = cb.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        data.setBalance(amount);
        updateHud(cb, ref);
    }

    public boolean processTransaction(@Nonnull CommandBuffer<EntityStore> cb, @Nonnull Ref<EntityStore> ref, double amount){
        var data = cb.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        if (data.getBalance()>=amount){
            data.addBalance(-amount);
            updateHud(cb, ref);
            return true;
        }
        else{
            return false;
        }
    }

    public double getMoney(@Nonnull CommandBuffer<EntityStore> cb, @Nonnull Ref<EntityStore> ref){
        var data = cb.ensureAndGetComponent(ref, plugin.getEconomyDataComponent());
        return data.getBalance();
    }

    private void updateHud(@Nonnull CommandBuffer<EntityStore> cb, @Nonnull Ref<EntityStore> ref){
        PlayerRef player = cb.getComponent(ref,PlayerRef.getComponentType());
        BalanceHUD hud = hudMap.get(player);
        if (hud!= null){
            hud.setBalance(getMoney(cb, ref));
        }
    }

    public void removeHUD(PlayerRef playerRef){
        hudMap.remove(playerRef);
    }

    public void registerHUD(PlayerRef playerRef, BalanceHUD hud){
        if (hudMap.get(playerRef) == null){
            hudMap.put(playerRef,hud);
            updateHud(playerRef.getReference());
        }
    }

    public BalanceHUD getPlayerHud(PlayerRef playerRef){
        return hudMap.get(playerRef);
    }
}
