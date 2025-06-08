package com.starfish_studios.yaf.block;

import com.starfish_studios.yaf.block.entity.DrawerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DrawerBlock extends AbstractDrawerBlock {

    public static final BooleanProperty TOP_OPEN = BooleanProperty.create("top_open");
    public static final BooleanProperty BOTTOM_OPEN = BooleanProperty.create("bottom_open");

    public DrawerBlock(Item planks, Properties properties) {
        super(planks, properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TOP_OPEN, false)
                .setValue(BOTTOM_OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(FACING, TOP_OPEN, BOTTOM_OPEN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateDefinition().any()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(TOP_OPEN, false)
                .setValue(BOTTOM_OPEN, false);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new DrawerBlockEntity(blockPos, blockState);
    }
}
