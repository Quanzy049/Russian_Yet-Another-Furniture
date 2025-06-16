package com.starfish_studios.yaf.registry;

import com.starfish_studios.yaf.YetAnotherFurniture;
import com.starfish_studios.yaf.block.entity.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class YAFBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(YetAnotherFurniture.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<MailboxBlockEntity>> MAILBOX = BLOCK_ENTITY_TYPES.register("mailbox",
            () -> BlockEntityType.Builder.of(MailboxBlockEntity::new,
                    YAFBlocks.MAIL_BOXES.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<DrawerBlockEntity>> DRAWER = BLOCK_ENTITY_TYPES.register("drawer",
            () -> BlockEntityType.Builder.of(DrawerBlockEntity::new,
                    YAFBlocks.DRAWERS.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET = BLOCK_ENTITY_TYPES.register("cabinet",
            () -> BlockEntityType.Builder.of(CabinetBlockEntity::new,
                    YAFBlocks.CABINET.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<SpigotBlockEntity>> SPIGOT = BLOCK_ENTITY_TYPES.register("spigot",
            () -> BlockEntityType.Builder.of(SpigotBlockEntity::new, YAFBlocks.SPIGOT.get()).build(null));


    public static final RegistrySupplier<BlockEntityType<FlowerBasketBlockEntity>> FLOWER_BASKET = BLOCK_ENTITY_TYPES.register("flower_basket",
            () -> BlockEntityType.Builder.of(FlowerBasketBlockEntity::new,
                    YAFBlocks.FLOWER_BASKETS.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<ShelfBlockEntity>> SHELF = BLOCK_ENTITY_TYPES.register("shelf",
            () -> BlockEntityType.Builder.of(ShelfBlockEntity::new,
                    YAFBlocks.SHELVES.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<FanBlockEntity>> FAN = BLOCK_ENTITY_TYPES.register("fan",
            () -> BlockEntityType.Builder.of(FanBlockEntity::new,
                    YAFBlocks.FANS.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<TableBlockEntity>> TABLE = BLOCK_ENTITY_TYPES.register("table",
            () -> BlockEntityType.Builder.of(TableBlockEntity::new,
                    YAFBlocks.TABLES.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<ChairBlockEntity>> CHAIR = BLOCK_ENTITY_TYPES.register("chair",
            () -> BlockEntityType.Builder.of(ChairBlockEntity::new,
                    YAFBlocks.CHAIRS.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );

    public static final RegistrySupplier<BlockEntityType<TallStoolBlockEntity>> TALL_STOOL = BLOCK_ENTITY_TYPES.register("tall_stool",
            () -> BlockEntityType.Builder.of(TallStoolBlockEntity::new,
                    YAFBlocks.TALL_STOOLS.values().stream()
                            .map(RegistrySupplier::get)
                            .toArray(Block[]::new)
            ).build(null)
    );


    public static final RegistrySupplier<BlockEntityType<WindChimeBlockEntity>> CHIME = BLOCK_ENTITY_TYPES.register("chime",  () -> BlockEntityType.Builder.of(WindChimeBlockEntity::new,
            YAFBlocks.AMETHYST_WIND_CHIMES.get(),
            YAFBlocks.BAMBOO_WIND_CHIMES.get(),
            YAFBlocks.BAMBOO_STRIPPED_WIND_CHIMES.get(),
            YAFBlocks.BONE_WIND_CHIMES.get(),
            YAFBlocks.COPPER_WIND_CHIMES.get(),
            YAFBlocks.ECHO_SHARD_WIND_CHIMES.get()

    ).build(null));
}
