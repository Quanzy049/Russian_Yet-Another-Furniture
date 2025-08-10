package com.starfish_studios.yaf.fabric.data;

import com.starfish_studios.yaf.registry.YAFBlocks;
import com.starfish_studios.yaf.registry.YAFItems;
import com.starfish_studios.yaf.registry.YAFTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class YAFBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public YAFBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        YAFBlocks.CHAIRS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.BENCHES.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.SOFAS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.SHELVES.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.DRAWERS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.CABINET.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.FANS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.MAIL_BOXES.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.TALL_STOOLS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.TABLES.forEach((omfWoodType, supplier) ->  {
            getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get());
            getOrCreateTagBuilder(YAFTags.BlockTags.TABLES_CONNECTABLE).add(supplier.get());
            getOrCreateTagBuilder(YAFTags.BlockTags.TABLES).add(supplier.get());
        });
        YAFBlocks.FLOWER_BASKETS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));
        YAFBlocks.LAMPS.forEach((omfWoodType, supplier) ->  getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(supplier.get()));

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(YAFBlocks.SPIGOT.get());
        getOrCreateTagBuilder(YAFTags.BlockTags.TABLES_CONNECTABLE).add(Blocks.SCAFFOLDING);
    }

    public static class ItemTagsProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            var curtains = getOrCreateTagBuilder(YAFTags.ItemTags.CURTAINS);
            YAFItems.CURTAINS.forEach((color, item) -> curtains.add(item.get()));
        }
    }
}
