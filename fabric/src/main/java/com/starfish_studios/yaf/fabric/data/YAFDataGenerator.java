package com.starfish_studios.yaf.fabric.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class YAFDataGenerator implements DataGeneratorEntrypoint  {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        var pack = fabricDataGenerator.createPack();

        pack.addProvider(YAFModelProvider::new);
        pack.addProvider(YAFLangProvider::new);
        pack.addProvider(YAFBlockLootTableProvider::new);
        pack.addProvider(YAFBlockTagProvider::new);
        pack.addProvider(YAFBlockTagProvider.ItemTagsProvider::new);
        pack.addProvider(YAFRecipeProvider::new);
    }
}