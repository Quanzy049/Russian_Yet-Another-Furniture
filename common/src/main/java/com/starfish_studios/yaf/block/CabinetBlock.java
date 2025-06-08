package com.starfish_studios.yaf.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.starfish_studios.yaf.block.entity.CabinetBlockEntity;
import com.starfish_studios.yaf.block.properties.CountertopType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class CabinetBlock extends AbstractDrawerBlock {

    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public static final BooleanProperty LEFT_OPEN = BooleanProperty.create("left_open");
    public static final BooleanProperty RIGHT_OPEN = BooleanProperty.create("right_open");

    private final Item plank;


    public CabinetBlock(Item plank, Properties properties) {
        super(plank, properties);
        this.plank = plank;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BOTTOM, true)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CabinetBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BOTTOM);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        BlockState blockState2 = this.defaultBlockState()
                .setValue(BOTTOM, true)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());

        Direction upOrDown = context.getNearestLookingVerticalDirection();
        if (upOrDown == Direction.UP) {
            blockState2 = blockState2.setValue(BOTTOM, false);
        }
        return blockState2;
    }
}