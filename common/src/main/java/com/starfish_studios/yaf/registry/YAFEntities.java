package com.starfish_studios.yaf.registry;

import com.starfish_studios.yaf.YetAnotherFurniture;
import com.starfish_studios.yaf.entities.SeatEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;

public class YAFEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(YetAnotherFurniture.MOD_ID, Registries.ENTITY_TYPE);


    public static final RegistrySupplier<EntityType<SeatEntity>> SEAT = ENTITY_TYPES.register("seat", () ->
            EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .sized(0f, 0f)
                    .build(YetAnotherFurniture.MOD_ID + ":seat"));

}
