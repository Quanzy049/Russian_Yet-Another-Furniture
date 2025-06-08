//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.starfish_studios.yaf.block.properties;

import com.mojang.serialization.Codec;
import com.starfish_studios.yaf.block.AbstractDrawerBlock;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CountertopType implements StringRepresentable {
    OAK("oak", () -> Items.OAK_PLANKS),
    SPRUCE("spruce", () -> Items.SPRUCE_PLANKS),
    BIRCH("birch", () -> Items.BIRCH_PLANKS),
    JUNGLE("jungle", () -> Items.JUNGLE_PLANKS),
    ACACIA("acacia", () -> Items.ACACIA_PLANKS),
    DARK_OAK("dark_oak", () -> Items.DARK_OAK_PLANKS),
    CRIMSON("crimson", () -> Items.CRIMSON_PLANKS),
    WARPED("warped", () -> Items.WARPED_PLANKS),
    MANGROVE("mangrove", () -> Items.MANGROVE_PLANKS),
    BAMBOO("bamboo", () -> Items.BAMBOO_PLANKS),
    CHERRY("cherry", () -> Items.CHERRY_PLANKS),
    QUARTZ("quartz", () -> Items.QUARTZ_BLOCK);

    private final String name;
    private final Supplier<Item> item;

    public static final Codec<CountertopType> CODEC = StringRepresentable.fromEnum(CountertopType::values);

    CountertopType(String name, Supplier<Item> item) {
        this.name = name;
        this.item = item;
    }

    public static CountertopType getFromBlock(Item drawer) {
        return Arrays.stream(CountertopType.values()).filter(plank -> {
            plank.getItem();
            return plank.getItem() == drawer;
        }).findFirst().orElse(null);
    }

    public static CountertopType getFromState(BlockState drawer) {
        var item = ((AbstractDrawerBlock) drawer.getBlock()).plankBlock;
        return CountertopType.getFromBlock(item);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public @NotNull Item getItem() {
        return this.item.get();
    }
}
