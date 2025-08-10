package com.starfish_studios.yaf.block;

import com.starfish_studios.yaf.block.entity.FanBlockEntity;
import com.starfish_studios.yaf.registry.YAFSoundEvents;
import com.starfish_studios.yaf.util.YAFSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FanBlock extends BaseEntityBlock {

    public String wood;

    private static final VoxelShape SHAPE_NORTH = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 16.0D);
    private static final VoxelShape SHAPE_SOUTH = Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 12.0D);
    private static final VoxelShape SHAPE_WEST = Block.box(4.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    private static final VoxelShape SHAPE_EAST = Block.box(0.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    private static final VoxelShape SHAPE_UP = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    private static final VoxelShape SHAPE_DOWN = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public FanBlock(String wood, Properties properties) {
        super(properties
                .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 12 : 0)
                .emissiveRendering((blockState, blockGetter, blockPos) -> blockState.getValue(BlockStateProperties.POWERED)));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.FACING, Direction.NORTH)
                .setValue(BlockStateProperties.POWERED, false)
        );
        this.wood = wood;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BlockStateProperties.FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (level1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof FanBlockEntity fanBlockEntity) {
                fanBlockEntity.commonTick(level, state);
            }
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isShiftKeyDown()) {
            state = state.cycle(BlockStateProperties.POWERED);
            level.setBlock(pos, state, 2);

            playSound(level, state, pos);
        } else {
            if (level.getBlockEntity(pos) instanceof FanBlockEntity fanBlockEntity) {
                fanBlockEntity.fanOn = !fanBlockEntity.fanOn;
                playFanToggleSound(level, pos, fanBlockEntity.fanOn);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void playSound(Level level, BlockState state, BlockPos pos) {
        var powered = state.getValue(BlockStateProperties.POWERED);
        if (powered) {
            level.playSound(null, pos, YAFSoundEvents.LAMP_ON.get(), SoundSource.BLOCKS);
        } else {
            level.playSound(null, pos, YAFSoundEvents.LAMP_OFF.get(), SoundSource.BLOCKS);
        }
        if (level.isClientSide && level.getBlockEntity(pos) instanceof FanBlockEntity fanBlockEntity) {
            if (fanBlockEntity.fanOn) {
                YAFSoundInstance.tryPlay(fanBlockEntity);
            } else {
                YAFSoundInstance.stop(fanBlockEntity);
            }
        }
    }

    private void playFanToggleSound(Level level, BlockPos pos, boolean fanOn) {
        if (fanOn) {
            level.playSound(null, pos, YAFSoundEvents.FAN_OFF.get(), SoundSource.BLOCKS);
        } else {
            level.playSound(null, pos, YAFSoundEvents.FAN_ON.get(), SoundSource.BLOCKS);
        }
        if (level.isClientSide && level.getBlockEntity(pos) instanceof FanBlockEntity fanBlockEntity) {
            if (fanOn) {
                YAFSoundInstance.tryPlay(fanBlockEntity);
            } else {
                YAFSoundInstance.stop(fanBlockEntity);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        boolean bl = level.hasNeighborSignal(pos);
        boolean isPowered = state.getValue(BlockStateProperties.POWERED);
        if (bl != isPowered) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, bl), 2);
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FanBlockEntity(pos, state);
    }
}
