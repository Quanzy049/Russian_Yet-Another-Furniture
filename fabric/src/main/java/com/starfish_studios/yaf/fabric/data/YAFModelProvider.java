package com.starfish_studios.yaf.fabric.data;

import com.starfish_studios.yaf.YetAnotherFurniture;
import com.starfish_studios.yaf.block.*;
import com.starfish_studios.yaf.block.properties.CountertopType;
import com.starfish_studios.yaf.registry.YAFBlocks;
import com.starfish_studios.yaf.registry.YAFItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class YAFModelProvider extends FabricModelProvider {

    public static final TextureSlot SIDES = TextureSlot.create("sides");
    public static final TextureSlot COUNTERTOP_SIDES = TextureSlot.create("countertop");
    public static final ModelTemplate DRAWER_CUBE_ORIENTABLE = createTemplate("drawer", TextureSlot.TOP, TextureSlot.FRONT, SIDES, TextureSlot.PARTICLE);
    public static final ModelTemplate DRAWER_CUBE_INVENTORY = createTemplate("drawer_inventory", TextureSlot.TOP, TextureSlot.FRONT, SIDES, COUNTERTOP_SIDES);
    public static final ModelTemplate COUNTERTOP = createTemplate("countertop", TextureSlot.TOP, SIDES);
    public static final ModelTemplate COUNTERTOP_BOTTOM = createTemplate("countertop_bottom", TextureSlot.TOP, SIDES);

    public static final ModelTemplate CABINET = createTemplate("cabinet", TextureSlot.TOP, TextureSlot.FRONT, SIDES, TextureSlot.PARTICLE);
    public static final ModelTemplate CABINET_TOP = createTemplate("cabinet_top", TextureSlot.TOP, TextureSlot.FRONT, SIDES, TextureSlot.PARTICLE);
    public static final ModelTemplate CABINET_INVENTORY = createTemplate("cabinet_inventory", TextureSlot.TOP, TextureSlot.FRONT, SIDES, COUNTERTOP_SIDES);


    public static final TextureSlot CORE = TextureSlot.create("core");
    public static final TextureSlot BITS = TextureSlot.create("bits");

    public static final ModelTemplate NIGHTSTAND = createTemplate("nightstand", CORE, BITS, TextureSlot.PARTICLE);
    public static final ModelTemplate NIGHTSTAND_OPEN = createTemplate("nightstand_open", CORE, BITS, TextureSlot.PARTICLE);

    public static final ModelTemplate LAMP = createTemplate("lamp", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate LAMP_BOTTOM = createTemplate("lamp_bottom", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate LAMP_MIDDLE = createTemplate("lamp_middle", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate LAMP_TOP = createTemplate("lamp_top", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate LAMPSHADE = createTemplate("lampshade", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate SOFA_SINGLE = createTemplate("sofa_single", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SOFA_LEFT = createTemplate("sofa_left", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SOFA_RIGHT = createTemplate("sofa_right", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SOFA_MIDDLE = createTemplate("sofa_middle", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SOFA_INNER = createTemplate("sofa_corner_inner", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SOFA_OUTER = createTemplate("sofa_corner_outer", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate SHELF_BOTTOM_CEILING = createTemplate("shelf/shelf_ceiling_bottom", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SHELF_DOUBLE_CEILING = createTemplate("shelf/shelf_ceiling_double", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SHELF_TOP_CEILING = createTemplate("shelf/shelf_ceiling_top", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate SHELF_DOUBLE_FLOOR = createTemplate("shelf/shelf_floor_double", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SHELF_BOTTOM_FLOOR = createTemplate("shelf/shelf_floor_bottom", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SHELF_TOP_FLOOR = createTemplate("shelf/shelf_floor_top", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate SHELF_BOTTOM_SINGLE = createTemplate("shelf/shelf_wall_bottom", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SHELF_DOUBLE_SINGLE = createTemplate("shelf/shelf_wall_double", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate SHELF_TOP_SINGLE = createTemplate("shelf/shelf_wall_top", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate CURTAIN_SINGLE_CLOSED = createTemplate("curtain_single_closed", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_BOTTOM_MIDDLE_CLOSED = createTemplate("curtain_bottom_middle_closed", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate CURTAIN_SINGLE_OPEN = createTemplate("curtain_single_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_LEFT_OPEN = createTemplate("curtain_left_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_RIGHT_OPEN = createTemplate("curtain_right_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_BOTTOM_LEFT_OPEN = createTemplate("curtain_bottom_left_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_BOTTOM_RIGHT_OPEN = createTemplate("curtain_bottom_right_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_BOTTOM_MIDDLE_OPEN = createTemplate("curtain_bottom_middle_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_LEFT_CORNER_OPEN = createTemplate("curtain_left_corner_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_RIGHT_CORNER_OPEN = createTemplate("curtain_right_corner_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_TOP_OPEN = createTemplate("curtain_top_open", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate CURTAIN_TOP_SINGLE_OPEN = createTemplate("curtain_top_single_open", TextureSlot.ALL, TextureSlot.PARTICLE);

    public static final ModelTemplate WIND_CHIME = createTemplate("chime", TextureSlot.ALL);
    public static final ModelTemplate FLOWER_BASKET = createTemplate("flower_basket", TextureSlot.ALL, TextureSlot.DIRT, TextureSlot.PARTICLE);
    public static final ModelTemplate FAN = createTemplate("ceiling_fan", TextureSlot.ALL);


    public YAFModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        for (CountertopType type : CountertopType.values()) {
            createCountertopType(generators, type);
        }

        YAFBlocks.FLOWER_BASKETS.forEach((omfWoodType, supplier) -> createFlowerBasket(generators, supplier.get()));
        YAFBlocks.CURTAINS.forEach((omfWoodType, supplier) -> createCurtainBlock(generators, supplier.get()));
        YAFBlocks.SOFAS.forEach((omfWoodType, supplier) -> createSofaBlock(generators, supplier.get()));
        YAFBlocks.DRAWERS.forEach((omfWoodType, supplier) -> createDrawerBlock(generators, supplier.get()));
        YAFBlocks.CABINET.forEach((omfWoodType, supplier) -> createCabinetBlock(generators, supplier.get()));
        YAFBlocks.SHELVES.forEach((omfWoodType, supplier) -> createShelfBlock(generators, supplier.get()));

        createWindChimeBlock(generators, YAFBlocks.AMETHYST_WIND_CHIMES.get());
        createWindChimeBlock(generators, YAFBlocks.BAMBOO_WIND_CHIMES.get());
        createWindChimeBlock(generators, YAFBlocks.BAMBOO_STRIPPED_WIND_CHIMES.get());
        createWindChimeBlock(generators, YAFBlocks.BONE_WIND_CHIMES.get());
        createWindChimeBlock(generators, YAFBlocks.COPPER_WIND_CHIMES.get());
        createWindChimeBlock(generators, YAFBlocks.ECHO_SHARD_WIND_CHIMES.get());

    }

    private void createWindChimeBlock(BlockModelGenerators generators, Block block){
        TextureMapping one = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "wind_chime", ""));

        var res = WIND_CHIME.create(block, one, generators.modelOutput);
        MultiVariantGenerator multiVariant = MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, res));

        multiVariant.with(BlockModelGenerators.createHorizontalFacingDispatch());
        generators.blockStateOutput.accept(multiVariant);
    }

    private void createFlowerBasket(BlockModelGenerators generators, Block block) {
        String blockPath = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String wood = blockPath.replace("_flower_basket", "");

        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.ALL, YetAnotherFurniture.id("block/flower_basket/" + wood + "_flower_basket"))
                .put(TextureSlot.PARTICLE, ResourceLocation.withDefaultNamespace("block/" + wood + "_planks"))
                .put(TextureSlot.DIRT, ResourceLocation.withDefaultNamespace("block/dirt"));

        ModelTemplate ceilingTemplate = createTemplate("flower_basket_ceiling", TextureSlot.ALL, TextureSlot.PARTICLE);
        ModelTemplate floorTemplate   = createTemplate("flower_basket_floor", TextureSlot.ALL, TextureSlot.PARTICLE);
        ModelTemplate wallTemplate    = createTemplate("flower_basket_wall", TextureSlot.ALL, TextureSlot.PARTICLE);

        ResourceLocation ceilingModel = ceilingTemplate.createWithSuffix(block, "_ceiling", mapping, generators.modelOutput);
        ResourceLocation floorModel   = floorTemplate.createWithSuffix(block, "_floor", mapping, generators.modelOutput);
        ResourceLocation wallModel    = wallTemplate.createWithSuffix(block, "_wall", mapping, generators.modelOutput);

        PropertyDispatch.C2<AttachFace, Direction> dispatch =
                PropertyDispatch.properties(FlowerBasketBlock.FACE, FlowerBasketBlock.FACING);
        for (AttachFace face : new AttachFace[]{AttachFace.CEILING, AttachFace.FLOOR, AttachFace.WALL}) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Variant variant = Variant.variant();
                if (face == AttachFace.CEILING) {
                    variant = variant.with(VariantProperties.MODEL, ceilingModel);
                } else if (face == AttachFace.FLOOR) {
                    variant = variant.with(VariantProperties.MODEL, floorModel);
                } else {
                    variant = variant.with(VariantProperties.MODEL, wallModel);
                }
                VariantProperties.Rotation rotation = switch (direction) {
                    case EAST  -> VariantProperties.Rotation.R90;
                    case SOUTH -> VariantProperties.Rotation.R180;
                    case WEST  -> VariantProperties.Rotation.R270;
                    default    -> VariantProperties.Rotation.R0;
                };
                if (rotation != VariantProperties.Rotation.R0) {
                    variant = variant.with(VariantProperties.Y_ROT, rotation);
                }
                dispatch = dispatch.select(face, direction, variant);
            }
        }
        MultiVariantGenerator multiVariant = MultiVariantGenerator.multiVariant(block).with(dispatch);
        generators.blockStateOutput.accept(multiVariant);
    }



    public record OpenClosed(ResourceLocation open, ResourceLocation closed){}


    private PropertyDispatch.C2<Boolean, CurtainBlock.CurtainShape> createCurtainPropertyDispatch(Map<CurtainBlock.CurtainShape, OpenClosed> curtainMappings) {
        PropertyDispatch.C2<Boolean, CurtainBlock.CurtainShape> dispatch = PropertyDispatch.properties(CurtainBlock.OPEN, CurtainBlock.SHAPE);

        for (Map.Entry<CurtainBlock.CurtainShape, OpenClosed> entry : curtainMappings.entrySet()) {
            CurtainBlock.CurtainShape shape = entry.getKey();
            OpenClosed models = entry.getValue();
            dispatch = dispatch.select(true, shape, Variant.variant().with(VariantProperties.MODEL, models.open))
                    .select(false, shape, Variant.variant().with(VariantProperties.MODEL, models.closed));
        }

        return dispatch;
    }

    private void createCurtainBlock(BlockModelGenerators generators, Block block) {
        Map<CurtainBlock.CurtainShape, OpenClosed> curtainMappings = new HashMap<>();

        var bottomMiddleAndMiddleOpen = new TextureMapping().put(TextureSlot.ALL, YetAnotherFurniture.id("block/curtain/empty"));
        var bottomRightAndBottomLeftOpen = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_bottom_open"));
        var cornerRightAndCornerLeftOpen = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_corner_open"));
        var rightAndLeftOpen = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_open"));
        var topAndBottomSingleOpen = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_top_single_open"));

        var singleClosed = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_single_closed"));
        var singleOpen = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_single_open"));
        var topOpenMapping = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_top_open"));
        var closedMapping = new TextureMapping().put(TextureSlot.ALL, getTexture(block, "curtain", "_bottom"));


        ResourceLocation bottomMiddleOpenResource = CURTAIN_BOTTOM_MIDDLE_OPEN.createWithSuffix(block, "_middle_open", bottomMiddleAndMiddleOpen, generators.modelOutput);
        ResourceLocation bottomMiddleClosedResource = CURTAIN_BOTTOM_MIDDLE_CLOSED.createWithSuffix(block, "_closed", closedMapping, generators.modelOutput);

        ResourceLocation bottomLeftOpenResource = CURTAIN_BOTTOM_LEFT_OPEN.createWithSuffix(block, "_bottom_left_open", bottomRightAndBottomLeftOpen, generators.modelOutput);
        ResourceLocation bottomRightOpenResource = CURTAIN_BOTTOM_RIGHT_OPEN.createWithSuffix(block, "_bottom_right_open", bottomRightAndBottomLeftOpen, generators.modelOutput);

        ResourceLocation cornerLeftOpenResource = CURTAIN_LEFT_CORNER_OPEN.createWithSuffix(block, "_left_corner_open", cornerRightAndCornerLeftOpen, generators.modelOutput);
        ResourceLocation cornerRightOpenResource = CURTAIN_RIGHT_CORNER_OPEN.createWithSuffix(block, "_right_corner_open", cornerRightAndCornerLeftOpen, generators.modelOutput);
        ResourceLocation defaultClosed = CURTAIN_SINGLE_CLOSED.createWithSuffix(block, "_single_closed", singleClosed, generators.modelOutput);
        ResourceLocation singleOpenResource = CURTAIN_SINGLE_OPEN.createWithSuffix(block, "_single_open", singleOpen, generators.modelOutput);

        ResourceLocation topOpenResource = CURTAIN_TOP_OPEN.createWithSuffix(block, "_top_open", topOpenMapping, generators.modelOutput);
        ResourceLocation topSingleOpenResource = CURTAIN_TOP_SINGLE_OPEN.createWithSuffix(block, "_top_single_open", topAndBottomSingleOpen, generators.modelOutput);

        ResourceLocation leftOpenResource = CURTAIN_LEFT_OPEN.createWithSuffix(block, "_left_open", rightAndLeftOpen, generators.modelOutput);
        ResourceLocation rightOpenResource = CURTAIN_RIGHT_OPEN.createWithSuffix(block, "_right_open", rightAndLeftOpen, generators.modelOutput);


        curtainMappings.put(CurtainBlock.CurtainShape.BOTTOM_MIDDLE, new OpenClosed(bottomMiddleOpenResource, bottomMiddleClosedResource));
        curtainMappings.put(CurtainBlock.CurtainShape.BOTTOM_LEFT, new OpenClosed(bottomLeftOpenResource, bottomMiddleClosedResource));
        curtainMappings.put(CurtainBlock.CurtainShape.BOTTOM_RIGHT, new OpenClosed(bottomRightOpenResource, bottomMiddleClosedResource));
        curtainMappings.put(CurtainBlock.CurtainShape.CORNER_RIGHT, new OpenClosed(cornerRightOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.CORNER_LEFT, new OpenClosed(cornerLeftOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.TOP, new OpenClosed(topOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.TOP_SINGLE, new OpenClosed(topSingleOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.LEFT, new OpenClosed(leftOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.RIGHT, new OpenClosed(rightOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.MIDDLE, new OpenClosed(bottomMiddleOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.SINGLE, new OpenClosed(topSingleOpenResource, defaultClosed));
        curtainMappings.put(CurtainBlock.CurtainShape.BOTTOM_SINGLE, new OpenClosed(singleOpenResource, bottomMiddleClosedResource));
        curtainMappings.put(CurtainBlock.CurtainShape.VERTICAL_MIDDLE, new OpenClosed(singleOpenResource, defaultClosed));

        MultiVariantGenerator multiVariant = MultiVariantGenerator.multiVariant(block);
        multiVariant.with(BlockModelGenerators.createHorizontalFacingDispatch());
        multiVariant.with(createCurtainPropertyDispatch(curtainMappings));

        generators.skipAutoItemBlock(block);
        generators.blockStateOutput.accept(multiVariant);
    }


    private void createShelfBlock(BlockModelGenerators generators, Block block) {
        MultiVariantGenerator multiVariant = MultiVariantGenerator.multiVariant(block);
        var textMap = new TextureMapping()
                .put(TextureSlot.ALL, getTexture(block, "shelf", ""))
                .put(TextureSlot.PARTICLE, getTexture(block, "shelf", ""));
        ResourceLocation ceilingBottom = SHELF_BOTTOM_CEILING.createWithSuffix(block, "_ceiling_bottom", textMap, generators.modelOutput);
        ResourceLocation ceilingDouble = SHELF_DOUBLE_CEILING.createWithSuffix(block, "_ceiling_double", textMap, generators.modelOutput);
        ResourceLocation ceilingTop = SHELF_TOP_CEILING.createWithSuffix(block, "_ceiling_top", textMap, generators.modelOutput);
        ResourceLocation floorDouble = SHELF_DOUBLE_FLOOR.createWithSuffix(block, "_floor_double", textMap, generators.modelOutput);
        ResourceLocation floorBottom = SHELF_BOTTOM_FLOOR.createWithSuffix(block, "_floor_bottom", textMap, generators.modelOutput);
        ResourceLocation floorTop = SHELF_TOP_FLOOR.createWithSuffix(block, "_floor_top", textMap, generators.modelOutput);
        ResourceLocation shelfBottom = SHELF_BOTTOM_SINGLE.createWithSuffix(block, "_wall_bottom", textMap, generators.modelOutput);
        ResourceLocation shelfDouble = SHELF_DOUBLE_SINGLE.createWithSuffix(block, "_wall_double", textMap, generators.modelOutput);
        ResourceLocation shelfTop = SHELF_TOP_SINGLE.createWithSuffix(block, "_wall_top", textMap, generators.modelOutput);
        multiVariant.with(BlockModelGenerators.createHorizontalFacingDispatch());
        multiVariant.with(PropertyDispatch.properties(ShelfBlock.FACE, ShelfBlock.HALF)
                .select(AttachFace.CEILING, SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, ceilingTop))
                .select(AttachFace.CEILING, SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, ceilingBottom))
                .select(AttachFace.CEILING, SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, ceilingDouble))
                .select(AttachFace.FLOOR, SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, floorTop))
                .select(AttachFace.FLOOR, SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, floorBottom))
                .select(AttachFace.FLOOR, SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, floorDouble))
                .select(AttachFace.WALL, SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, shelfTop))
                .select(AttachFace.WALL, SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, shelfBottom))
                .select(AttachFace.WALL, SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, shelfDouble))
        );
        generators.skipAutoItemBlock(block);
        generators.blockStateOutput.accept(multiVariant);
    }

    private void createCountertopType(BlockModelGenerators generators, CountertopType type) {
        TextureMapping baseMapping = new TextureMapping()
                .put(TextureSlot.TOP, YetAnotherFurniture.id("block/drawers/" + type.getSerializedName() + "_drawer_top"))
                .put(SIDES, YetAnotherFurniture.id("block/drawers/countertop/" + type.getSerializedName() + "_drawer_countertop_sides"));
        COUNTERTOP.create(YetAnotherFurniture.id("block/drawers/countertop/countertop_" + type.getSerializedName()), baseMapping, generators.modelOutput);

        COUNTERTOP_BOTTOM.create(YetAnotherFurniture.id("block/drawers/countertop/countertop_" + type.getSerializedName() + "_bottom"), baseMapping, generators.modelOutput);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {

        generators.generateFlatItem(YAFItems.COPPER_SAW.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        YAFBlocks.FLOWER_BASKETS.forEach((omfWoodType, supplier) -> generators.generateFlatItem(supplier.get().asItem(), ModelTemplates.FLAT_ITEM));
        YAFBlocks.CURTAINS.forEach((omfWoodType, supplier) -> generators.generateFlatItem(supplier.get().asItem(), ModelTemplates.FLAT_ITEM));
        YAFBlocks.DRAWERS.forEach(((omfWoodType, supplier) -> createDrawerItem(generators, supplier.get().asItem(), supplier.get())));
        YAFBlocks.CABINET.forEach(((omfWoodType, supplier) -> createCabinetItem(generators, supplier.get().asItem(), supplier.get())));
        YAFItems.FANS.forEach(((omfWoodType, supplier) ->  generators.generateFlatItem(supplier.get(), ModelTemplates.FLAT_ITEM)));

    }

    /**
     * Creates a reference to our own model templates
     */
    public static ModelTemplate createTemplate(String blockModelLocation, TextureSlot... requiredSlots) {
        return new ModelTemplate(Optional.of(
                YetAnotherFurniture.id(
                        "block/template/" + blockModelLocation
                )
        ),
                Optional.empty(),
                requiredSlots
        );
    }

    public final void createDrawerItem(ItemModelGenerators generators, Item item, Block block) {
        TextureMapping baseMapping = new TextureMapping()
                .put(TextureSlot.TOP, getTexture(block, "drawers", "_top"))
                .put(SIDES, getTexture(block, "drawers", "_side"))
                .put(TextureSlot.FRONT, getTexture(block, "drawers", "_front"))
                .put(COUNTERTOP_SIDES, getTexture(block, "drawers/countertop", "_countertop_sides"));
        DRAWER_CUBE_INVENTORY.create(ModelLocationUtils.getModelLocation(item), baseMapping, generators.output);
    }

    public final void createDrawerBlock(BlockModelGenerators generators, Block block) {
        TextureMapping baseMapping = new TextureMapping()
                .put(TextureSlot.TOP, getTexture(block, "drawers", "_top"))
                .put(SIDES, getTexture(block, "drawers", "_side"))
                .put(TextureSlot.FRONT, getTexture(block, "drawers", "_front"))
                .put(TextureSlot.PARTICLE, getTexture(block, "drawers", "_front"));
        ResourceLocation drawerId = DRAWER_CUBE_ORIENTABLE.create(block, baseMapping, generators.modelOutput);
        generators.skipAutoItemBlock(block);
        generators.blockStateOutput.accept(createDrawerMultipart(
                block,
                drawerId
        ));
    }

    public final void createCabinetItem(ItemModelGenerators generators, Item item, Block block) {
        String wood = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String firstName = wood.split("_cabinet")[0];
        ResourceLocation loc = YetAnotherFurniture.id("block/drawers/" + firstName + "_drawer_top");
        ResourceLocation countertopLoc = YetAnotherFurniture.id("block/drawers/countertop/" + firstName + "_drawer_countertop_sides");

        TextureMapping baseMapping = new TextureMapping()
                .put(TextureSlot.TOP, loc)
                .put(SIDES, getTexture(block, "cabinet", "_sides"))
                .put(TextureSlot.FRONT, getTexture(block, "cabinet", "_front"))
                .put(COUNTERTOP_SIDES, countertopLoc);
        CABINET_INVENTORY.create(ModelLocationUtils.getModelLocation(item), baseMapping, generators.output);
    }

    public final void createCabinetBlock(BlockModelGenerators generators, Block block) {
        String wood = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String firstName = wood.split("_cabinet")[0];
        ResourceLocation loc = YetAnotherFurniture.id( "block/drawers/" + firstName + "_drawer_top");

        TextureMapping baseMapping = new TextureMapping()
                .put(TextureSlot.TOP, loc)
                .put(SIDES, getTexture(block, "cabinet", "_sides"))
                .put(TextureSlot.FRONT, getTexture(block, "cabinet", "_front"))
                .put(TextureSlot.PARTICLE, getTexture(block, "cabinet", "_front"));

        TextureMapping upperMapping = new TextureMapping()
                .put(TextureSlot.TOP, loc)
                .put(SIDES, getTexture(block, "cabinet", "_sides_upper"))
                .put(TextureSlot.FRONT, getTexture(block, "cabinet", "_front_upper"))
                .put(TextureSlot.PARTICLE, getTexture(block, "cabinet", "_front_upper"));
        ResourceLocation cabinetId = CABINET.create(block, baseMapping, generators.modelOutput);
        ResourceLocation cabinetTopId = CABINET_TOP.createWithSuffix(block, "_bottom", upperMapping, generators.modelOutput);

        generators.skipAutoItemBlock(block);
        generators.blockStateOutput.accept(createCabinetMultipart(
                block,
                cabinetId,
                cabinetTopId
        ));
    }

    public static BlockStateGenerator createCabinetMultipart(
            Block drawerBlock,
            ResourceLocation cabinetId,
            ResourceLocation cabinetTopId
    ) {
        MultiPartGenerator multiPart = MultiPartGenerator.multiPart(drawerBlock);

        for (boolean bl : new boolean[]{true, false}) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                multiPart.with(
                        Condition.condition()
                                .term(BlockStateProperties.BOTTOM, bl)
                                .term(BlockStateProperties.HORIZONTAL_FACING, direction),
                        Variant.variant()
                                .with(VariantProperties.MODEL, bl ? cabinetId : cabinetTopId)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[((direction.get2DDataValue() * 90 + 180) / 90) % 4])
                );
            }
        }

        return multiPart;
    }

    public static BlockStateGenerator createDrawerMultipart(
            Block drawerBlock,
            ResourceLocation drawerId
    ) {
        MultiPartGenerator multiPart = MultiPartGenerator.multiPart(drawerBlock);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            multiPart.with(
                    Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction),
                    Variant.variant()
                            .with(VariantProperties.MODEL, drawerId)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[((direction.get2DDataValue() * 90 + 180) / 90) % 4])
            );
        }
        return multiPart;
    }

    private void createSofaBlock(BlockModelGenerators generators, Block sofa) {
        MultiVariantGenerator multiVariant = MultiVariantGenerator.multiVariant(sofa);
        var textMap = new TextureMapping()
                .put(TextureSlot.ALL, getTexture(sofa, "sofa", ""))
                .put(TextureSlot.PARTICLE, getTexture(sofa, "sofa", ""));
        ResourceLocation single = SOFA_SINGLE.create(sofa, textMap, generators.modelOutput);
        ResourceLocation left = SOFA_LEFT.createWithSuffix(sofa, "_left", textMap, generators.modelOutput);
        ResourceLocation right = SOFA_RIGHT.createWithSuffix(sofa, "_right", textMap, generators.modelOutput);
        ResourceLocation middle = SOFA_MIDDLE.createWithSuffix(sofa, "_middle", textMap, generators.modelOutput);
        ResourceLocation inner = SOFA_INNER.createWithSuffix(sofa, "_inner", textMap, generators.modelOutput);
        ResourceLocation outer = SOFA_OUTER.createWithSuffix(sofa, "_outer", textMap, generators.modelOutput);
        Map<SofaBlock.SofaShape, ResourceLocation> shapeModels = Map.of(
                SofaBlock.SofaShape.SINGLE, single,
                SofaBlock.SofaShape.LEFT, left,
                SofaBlock.SofaShape.RIGHT, right,
                SofaBlock.SofaShape.MIDDLE, middle,
                SofaBlock.SofaShape.INNER_LEFT, inner,
                SofaBlock.SofaShape.INNER_RIGHT, inner,
                SofaBlock.SofaShape.OUTER_LEFT, outer,
                SofaBlock.SofaShape.OUTER_RIGHT, outer
        );
        PropertyDispatch.C2<SofaBlock.SofaShape, Direction> dispatch = PropertyDispatch.properties(SofaBlock.SHAPE, BlockStateProperties.HORIZONTAL_FACING);
        for (var entry : shapeModels.entrySet()) {
            SofaBlock.SofaShape shape = entry.getKey();
            ResourceLocation model = entry.getValue();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                VariantProperties.Rotation rotation = getRotation(direction, shape);
                dispatch.select(shape, direction, Variant.variant()
                        .with(VariantProperties.Y_ROT, rotation)
                        .with(VariantProperties.MODEL, model));
            }
        }
        multiVariant.with(dispatch);
        generators.blockStateOutput.accept(multiVariant);
    }

    private static VariantProperties.@NotNull Rotation getRotation(Direction direction, SofaBlock.SofaShape shape) {
        VariantProperties.Rotation rotation = switch (direction) {
            case EAST -> VariantProperties.Rotation.R90;
            case SOUTH -> VariantProperties.Rotation.R180;
            case WEST -> VariantProperties.Rotation.R270;
            default -> VariantProperties.Rotation.R0;
        };
        if (shape == SofaBlock.SofaShape.INNER_RIGHT) {
            rotation = switch (direction) {
                case NORTH -> VariantProperties.Rotation.R90;
                case EAST -> VariantProperties.Rotation.R180;
                case SOUTH -> VariantProperties.Rotation.R270;
                default -> VariantProperties.Rotation.R0;
            };
        }
        if (shape == SofaBlock.SofaShape.OUTER_LEFT) {
            rotation = switch (direction) {
                case NORTH -> VariantProperties.Rotation.R270;
                case SOUTH -> VariantProperties.Rotation.R90;
                case WEST -> VariantProperties.Rotation.R180;
                default -> VariantProperties.Rotation.R0;
            };
        }
        return rotation;
    }

    public static ResourceLocation getTexture(Block block, String folder, String textureSuffix) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPath((string2 -> "block/"+ folder + "/" + string2 + textureSuffix));
    }
}
