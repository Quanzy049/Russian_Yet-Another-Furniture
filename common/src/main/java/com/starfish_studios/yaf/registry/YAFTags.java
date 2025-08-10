package com.starfish_studios.yaf.registry;

import com.starfish_studios.yaf.YetAnotherFurniture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface YAFTags {

    class BlockTags {
        public static final TagKey<Block> ABOVE_BYPASSES_SEAT_CHECK = tag("above_bypasses_seat_check");
        public static final TagKey<Block> TABLES = tag("tables_connectable");
        public static final TagKey<Block> TABLES_CONNECTABLE = tag("tables_connectable");
        public static final TagKey<Block> CHAIRS_TUCKABLE_UNDER = tag("chairs_tuckable");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation(YetAnotherFurniture.MOD_ID, name));
        }
    }

    class EntityTags {

        public static final TagKey<EntityType<?>> CAN_SIT_IN_SEATS = tag("can_sit_in_seats");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), new ResourceLocation(YetAnotherFurniture.MOD_ID, name));
        }
    }

    class ItemTags {
        public static final TagKey<Item> CURTAINS = tag("curtains");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation(YetAnotherFurniture.MOD_ID, name));
        }
    }

}
