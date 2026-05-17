package dev.hytalemodding.ui;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

public class BalanceHUD extends CustomUIHud {
    public BalanceHUD(@Nonnull PlayerRef playerRef){
        super(playerRef);
    }

    @Override
    protected void build(@Nonnull UICommandBuilder uiCommandBuilder){
        uiCommandBuilder.append("BalanceHUD.ui");
    }

    public void setBalance(double balance){
        UICommandBuilder builder = new UICommandBuilder();
        builder.set("#BalanceLabel.TextSpans", Message.raw("$ "+balance));
        update(false,builder);
    }
}
