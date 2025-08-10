package com.starfish_studios.yaf.registry;

import com.starfish_studios.yaf.YetAnotherFurniture;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class YAFSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(YetAnotherFurniture.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> WINDCHIME_AMETHYST = register("block.windchimes.amethyst");
    public static final RegistrySupplier<SoundEvent> WINDCHIME_BAMBOO = register("block.windchimes.bamboo");
    public static final RegistrySupplier<SoundEvent> WINDCHIME_BONE = register("block.windchimes.bone");
    public static final RegistrySupplier<SoundEvent> WINDCHIME_COPPER = register("block.windchimes.copper");
    public static final RegistrySupplier<SoundEvent> WINDCHIME_ECHO_SHARD = register("block.windchimes.echo_shard");
    public static final RegistrySupplier<SoundEvent> SPIGOT_ON = register("block.spigot.on");
    public static final RegistrySupplier<SoundEvent> SPIGOT_OFF = register("block.spigot.off");
    public static final RegistrySupplier<SoundEvent> CABINET_OPEN = register("block.cabinet.open");
    public static final RegistrySupplier<SoundEvent> CABINET_CLOSE = register("block.cabinet.close");
    public static final RegistrySupplier<SoundEvent> DRAWER_OPEN = register("block.drawer.open");
    public static final RegistrySupplier<SoundEvent> DRAWER_CLOSE = register("block.drawer.close");
    public static final RegistrySupplier<SoundEvent> FAN_ON = register("block.fan.on");
    public static final RegistrySupplier<SoundEvent> FAN_OFF = register("block.fan.off");

    public static final RegistrySupplier<SoundEvent> MAILBOX_OPEN = register("block.mailbox.open");
    public static final RegistrySupplier<SoundEvent> MAILBOX_CLOSE = register("block.mailbox.close");


    public static final RegistrySupplier<SoundEvent> LAMP_ON = register("block.lamp.on");
    public static final RegistrySupplier<SoundEvent> LAMP_OFF = register("block.lamp.off");
    public static final RegistrySupplier<SoundEvent> FAN_AMBIENCE = register("block.fan.ambience");

    public static RegistrySupplier<SoundEvent> register(String name) {
        ResourceLocation id = new ResourceLocation(YetAnotherFurniture.MOD_ID, name);
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
