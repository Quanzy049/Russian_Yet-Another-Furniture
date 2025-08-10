package com.starfish_studios.yaf.fabric.data;

import com.starfish_studios.yaf.registry.YAFBlocks;
import com.starfish_studios.yaf.registry.YAFItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class YAFLangProvider extends FabricLanguageProvider {
    public YAFLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {

        YAFItems.ITEMS.forEach(supplier -> {
            String name = supplier.getId().getPath();
            String prepend = "item.yaf." + (name);
            builder.add(prepend, formatString(name));
        });

        YAFBlocks.BLOCKS.forEach(supplier -> {
            String name = supplier.getId().getPath();
            String prepend = "block.yaf." + (name);
            builder.add(prepend, formatString(name));
        });

        builder.add("itemGroup.yaf.tab", "Yet Another Furniture");
        builder.add( "block_type.yaf.table", "%s Table");
        builder.add( "block_type.yaf.chair", "%s Chair");
        builder.add( "block_type.yaf.nightstand", "%s Nightstand");
        builder.add( "block_type.yaf.mailbox", "%s Mailbox");
        builder.add( "block_type.yaf.lamp", "%s Lamp");
        builder.add( "block_type.yaf.shelf", "%s Shelf");


        builder.add( "container.drawer", "Drawer");
        builder.add( "container.drawer_top", "Top Drawer");
        builder.add( "container.drawer_bottom", "Bottom Drawer");
        builder.add( "container.mailbox", "Mailbox");
        builder.add( "container.mailbox.player_name", "%s's Mailbox");
        builder.add( "container.mailbox.send", "Send to Mailbox:");
        builder.add( "container.mailbox.error", "Cannot send!");

        builder.add("container.cabinet_left", "Left Cabinet");
        builder.add("container.cabinet_right", "Right Cabinet");

        builder.add( "yaf.mailbox.sending", "Sending item(s) from %s at %s");
        builder.add( "yaf.mailbox.receiving", "Receiving item(s) from %s at %s");
        builder.add( "yaf.mailbox.at", "at");

        builder.add("subtitles.yaf.block.windchimes.amethyst", "Amethyst Wind Chimes twinkling");
        builder.add("subtitles.yaf.block.windchimes.bamboo", "Bamboo Wind Chimes clattering");
        builder.add("subtitles.yaf.block.windchimes.bone", "Bone Wind Chimes rattling");
        builder.add("subtitles.yaf.block.windchimes.copper", "Copper Wind Chimes tinkling");
        builder.add("subtitles.yaf.block.windchimes.echo_shard", "Echo Shard Wind Chimes resonating");
        builder.add("subtitles.yaf.block.spigot.on", "Spigot turns on");
        builder.add("subtitles.yaf.block.spigot.off", "Spigot turns off");
        builder.add("subtitles.yaf.block.cabinet.open", "Cabinet opens");
        builder.add("subtitles.yaf.block.cabinet.close", "Cabinet closes");

        builder.add("subtitles.yaf.block.drawer.open", "Drawer opens");
        builder.add("subtitles.yaf.block.drawer.close", "Drawer closes");

        builder.add("subtitles.yaf.block.lamp.on", "Light turns on");
        builder.add("subtitles.yaf.block.lamp.off", "Light turns off");
        builder.add("subtitles.yaf.block.fan.ambience", "Fan whirring");

        builder.add("subtitles.yaf.block.mailbox.open", "Mailbox opens");
        builder.add("subtitles.yaf.block.mailbox.close", "Mailbox closes");

        builder.add("subtitles.yaf.block.fan.on", "Fan turns on");
        builder.add("subtitles.yaf.block.fan.off", "Fan turns off");
    }

    private static final Set<String> LOWERCASE_WORDS = new HashSet<>(Arrays.asList("and", "or"));

    public static String formatString(String input) {
        String[] words = input.split("_");

        return Arrays.stream(words).map(YAFLangProvider::capitalizeWord).collect(Collectors.joining(" "));
    }

    private static String capitalizeWord(String word) {
        String lowerWord = word.toLowerCase();
        if (LOWERCASE_WORDS.contains(lowerWord)) {
            return lowerWord;
        }
        return Character.toUpperCase(lowerWord.charAt(0)) + lowerWord.substring(1);
    }
}