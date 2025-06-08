package com.starfish_studios.yaf.block;


import com.starfish_studios.yaf.block.entity.AbstractDrawerBlockEntity;
import com.starfish_studios.yaf.block.properties.CountertopType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class AbstractDrawerBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public final Item plankBlock;

    public AbstractDrawerBlock(Item plank, Properties properties) {
        super(properties);
        this.plankBlock = plank;
    }

    public Item getPlanks(){
        return plankBlock;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return Shapes.block();
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            var handItem = player.getMainHandItem().getItem();
            var counterTops = Arrays.stream(CountertopType.values());
            var filtered = counterTops.filter(type -> type.getItem() == handItem).toList();

            if (level.getBlockEntity(pos) instanceof AbstractDrawerBlockEntity blockEntity) {
                var currentCounterTop = blockEntity.countertopType;

                if (!filtered.isEmpty()) {
                    var newCounterTop = filtered.get(0);
                    var defaultCounterTop = CountertopType.getFromBlock(((AbstractDrawerBlock)state.getBlock()).getPlanks());

                    if (newCounterTop != currentCounterTop) {
                        if (currentCounterTop != defaultCounterTop) {
                            Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, new ItemStack(currentCounterTop.getItem()));
                        }

                        if (newCounterTop != defaultCounterTop && !player.isCreative()) {
                            player.getMainHandItem().shrink(1);
                        }

                        blockEntity.countertopType = newCounterTop;
                        blockEntity.setChanged();

                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        Direction facing = state.getValue(FACING);
        Direction hitLoc = hit.getDirection();

        if (blockEntity instanceof AbstractDrawerBlockEntity drawerBlockEntity && facing == hitLoc) {
            player.openMenu(drawerBlockEntity);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof Container) {
                Containers.dropContents(level, blockPos, (Container) blockEntity);
                level.updateNeighbourForOutputSignal(blockPos, this);
            }

            if (level.getBlockEntity(blockPos) instanceof AbstractDrawerBlockEntity drawerBlockEntity) {
                var currentCounterTop = drawerBlockEntity.countertopType;

                if (currentCounterTop.getItem() != plankBlock) {
                    Containers.dropItemStack(level, blockPos.getX() + 0.5, blockPos.getY() + 0.85, blockPos.getZ() + 0.5, new ItemStack(currentCounterTop.getItem()));
                }
            }

            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }

    @Override
    public void tick(@NotNull BlockState blockState, ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof AbstractDrawerBlockEntity) {
            ((AbstractDrawerBlockEntity) blockEntity).recheckOpen();
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }
}