package com.starfish_studios.yaf.fabric.data;

import com.starfish_studios.yaf.registry.YAFDyeColor;
import com.starfish_studios.yaf.registry.YAFItems;
import com.starfish_studios.yaf.registry.YAFTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class YAFRecipeProvider extends FabricRecipeProvider {
    public YAFRecipeProvider(FabricDataOutput output) {
        super(output);
    }
    
    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {

        YAFItems.DRAWERS.forEach((omfWoodType, drawerSupplier) -> {
            createDrawer(consumer, drawerSupplier.get(), omfWoodType.getPlanks().asItem(), omfWoodType.getSlab().asItem());
        });
        YAFItems.CABINET.forEach((omfWoodType, drawerSupplier) -> {
            createCabinet(consumer, drawerSupplier.get(), omfWoodType.getPlanks().asItem(), omfWoodType.getSlab().asItem());
        });
        YAFItems.LAMPS.forEach((omfWoodType, drawerSupplier) -> {
            createLamp(consumer, drawerSupplier.get(), omfWoodType.getPlanks().asItem());
        });
        YAFItems.TALL_STOOLS.forEach((omfWoodType, drawerSupplier) -> {
            createTallStool(consumer, drawerSupplier.get(), omfWoodType.getSlab().asItem());
        });
        YAFItems.CHAIRS.forEach((omfWoodType, drawerSupplier) -> {
            createChair(consumer, drawerSupplier.get(), omfWoodType.getSlab().asItem());
        });
        YAFItems.TABLES.forEach((omfWoodType, drawerSupplier) -> {
            createTable(consumer, drawerSupplier.get(), omfWoodType.getSlab().asItem());
        });
        YAFItems.FANS.forEach((omfWoodType, drawerSupplier) -> {
            createFan(consumer, drawerSupplier.get(), omfWoodType.getPlanks().asItem());
        });
        YAFItems.SHELVES.forEach((omfWoodType, drawerSupplier) -> {
            createShelf(consumer, drawerSupplier.get(), omfWoodType.getSlab().asItem());
        });
        YAFItems.BENCHES.forEach((omfWoodType, drawerSupplier) -> {
            createBench(consumer, drawerSupplier.get(), omfWoodType.getSlab().asItem());
        });
        YAFItems.MAIL_BOXES.forEach((omfWoodType, drawerSupplier) -> {
            createMailbox(consumer, drawerSupplier.get(), omfWoodType.getPlanks().asItem());
        });

        YAFItems.SOFAS.forEach((dyeColor, drawerSupplier) -> {
            createSofa(consumer, drawerSupplier.get(), dyeColor.getWool().asItem());
        });

        YAFItems.CURTAINS.forEach((dyeColor, supplier) -> {
            createCurtain(consumer, supplier.get(), dyeColor);
        });


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, YAFItems.SPIGOT.get())
                .define('N', Items.IRON_NUGGET)
                .define('I', Items.IRON_INGOT)
                .define('B', Items.WATER_BUCKET)
                .pattern(" N ")
                .pattern("BB ")
                .pattern(" I ")
                .unlockedBy("has_bucket", has( Items.WATER_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, YAFItems.COPPER_SAW.get())
                .define('N', Items.COPPER_INGOT)
                .define('I', Items.STICK)
                .pattern("NI")
                .pattern("NI")
                .pattern(" I")
                .unlockedBy("has_copper", has( Items.COPPER_INGOT))
                .save(consumer);


        createWindchime(consumer, YAFItems.AMETHYST_WIND_CHIMES.get(), Items.AMETHYST_SHARD);
        createWindchime(consumer, YAFItems.BAMBOO_WIND_CHIMES.get(), Items.BAMBOO_BLOCK);
        createWindchime(consumer, YAFItems.BAMBOO_STRIPPED_WIND_CHIMES.get(), Items.STRIPPED_BAMBOO_BLOCK);
        createWindchime(consumer, YAFItems.BONE_WIND_CHIMES.get(), Items.BONE);
        createWindchime(consumer, YAFItems.COPPER_WIND_CHIMES.get(), Items.COPPER_INGOT);
        createWindchime(consumer, YAFItems.ECHO_SHARD_WIND_CHIMES.get(), Items.ECHO_SHARD);


    }

    private void createWindchime(Consumer<FinishedRecipe> consumer, Item chime, Item material){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chime)
                .define('W', material)
                .define('P', Items.STRING)
                .pattern("P")
                .pattern("W")
                .unlockedBy("has_string", has(Items.STRING))
                .save(consumer);
    }

    private void createMailbox(Consumer<FinishedRecipe> consumer, Item box, Item planks){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, box)
                .define('W', planks)
                .define('P', Items.PAPER)
                .define('I', Items.IRON_INGOT)
                .pattern("III")
                .pattern("IPI")
                .pattern(" W ")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(consumer);
    }


    private void createBench(Consumer<FinishedRecipe> consumer, Item bench, Item planks){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bench, 2)
                .define('W', planks)
                .define('N', Items.IRON_NUGGET)
                .pattern("NW ")
                .pattern("NWW")
                .unlockedBy("has_planks", has(planks))
                .save(consumer);
    }


    private void createShelf(Consumer<FinishedRecipe> consumer, Item shelf, Item slab){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, shelf, 4)
                .define('W', slab)
                .define('S', Items.STICK)
                .pattern("WW")
                .pattern("S ")
                .unlockedBy("has_slab", has(slab))
                .save(consumer);
    }

    private void createFan(Consumer<FinishedRecipe> consumer, Item fan, Item plank){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fan)
                .define('W', plank)
                .define('I', Items.IRON_INGOT)
                .pattern(" W ")
                .pattern("WIW")
                .pattern(" W ")
                .unlockedBy("has_plank", has(plank))
                .save(consumer);
    }


    private void createTable(Consumer<FinishedRecipe> consumer, Item table, Item slab){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, table)
                .define('W', slab)
                .define('S', Items.STICK)
                .pattern("WW")
                .pattern("SS")
                .unlockedBy("has_slab", has(slab))
                .save(consumer);
    }

    private void createChair(Consumer<FinishedRecipe> consumer, Item chair, Item slab){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chair, 2)
                .define('W', slab)
                .define('S', Items.STICK)
                .pattern("W ")
                .pattern("WW")
                .pattern("SS")
                .unlockedBy("has_slab", has(slab))
                .save(consumer);
    }

    private void createSofa(Consumer<FinishedRecipe> consumer, Item sofa, Item wool){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, sofa, 2)
                .define('W', wool)
                .pattern("W ")
                .pattern("WW")
                .group("yaf:sofas")
                .unlockedBy("has_slab", has(wool))
                .save(consumer);
    }

    private void createCabinet(Consumer<FinishedRecipe> consumer, Item drawer, Item plank, Item slab) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, drawer, 1)
                .define('P', plank)
                .define('S', slab)
                .define('I', Items.IRON_NUGGET)
                .pattern("PPP")
                .pattern("SIS")
                .pattern("PPP")
                .unlockedBy("has_nugget", has(Items.IRON_NUGGET))
                .save(consumer);
    }

    private void createTallStool(Consumer<FinishedRecipe> consumer, Item tallStool, Item slab){
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tallStool, 2)
                .define('P', slab)
                .define('S', Items.STICK)
                .pattern("P")
                .pattern("S")
                .unlockedBy("has_slab", has(slab))
                .save(consumer);
    }

    private void createDrawer(Consumer<FinishedRecipe> consumer, Item drawer, Item plank, Item slab) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, drawer, 1)
                .define('P', plank)
                .define('S', slab)
                .define('I', Items.IRON_NUGGET)
                .pattern("PSP")
                .pattern("PIP")
                .pattern("PSP")
                .unlockedBy("has_nugget", has(Items.IRON_NUGGET))
                .save(consumer);
    }

    private void createCurtain(Consumer<FinishedRecipe> consumer, Item curtain, YAFDyeColor color) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, curtain, 8)
                .define('S', Items.STICK)
                .define('W', color.getWool().asItem())
                .pattern("SS")
                .pattern("WW")
                .pattern("WW")
                .group("yaf:curtains")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .unlockedBy("has_stick", has(Items.STICK))
                .save(consumer);

        ItemLike dyeItem = DyeItem.byColor(color.getDyeColor());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, curtain)
                .requires(YAFTags.ItemTags.CURTAINS)
                .requires(dyeItem)
                .group("yaf:curtains")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .unlockedBy("has_stick", has(Items.STICK))
                .save(consumer, getDyeRecipeId(color.getName()));
    }

    private static String getDyeRecipeId(String to) {
        return "yaf:dye_curtain_to_" + to;
    }

    private void createLamp(Consumer<FinishedRecipe> consumer, Item lamp, Item wood) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, lamp, 2)
                .define('W', ItemTags.WOOL)
                .define('T', Items.TORCH)
                .define('S', wood)
                .pattern("W")
                .pattern("T")
                .pattern("S")
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(consumer);
    }
}
