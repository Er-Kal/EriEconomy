package dev.hytalemodding;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class EconomyData implements Component<EntityStore>{
    private double balance;
    public static final BuilderCodec<EconomyData> CODEC =
            BuilderCodec.builder(EconomyData.class, EconomyData::new)
                    .append(new KeyedCodec<>("Balance", Codec.DOUBLE),
                            (data, value) -> data.balance = value,
                            data -> data.balance
                            )
                    .add()
                    .build();

    public EconomyData() {
        this.balance = 0;
    }

    public EconomyData(EconomyData clone){
        this.balance = clone.balance;
    }

    public double getBalance() {return balance;}
    public void addBalance(double balance) { this.balance+=balance;}
    public void setBalance(double balance) {this.balance=balance;}

    @Nonnull
    @Override
    public Component<EntityStore> clone() {
        return new EconomyData(this);
    }
}
