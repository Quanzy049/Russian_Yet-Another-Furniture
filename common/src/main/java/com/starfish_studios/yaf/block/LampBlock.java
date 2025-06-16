package com.starfish_studios.yaf.block;

import com.starfish_studios.yaf.block.properties.ColorList;
import com.starfish_studios.yaf.registry.YAFSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LampBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<LampType> LAMP_TYPE = EnumProperty.create("lamp_type", LampType.class);

    public static final EnumProperty<ColorList> LAMPSHADE = EnumProperty.create("lampshade", ColorList.class);

    protected static final VoxelShape SINGLE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D);
    protected static final VoxelShape TOP = Block.box(3.0D, 7.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    protected static final VoxelShape MIDDLE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    protected static final VoxelShape BOTTOM = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 2.0D, 12.0D);


    public LampBlock(Properties properties) {
        super(properties);
        this.defaultBlockState()
                .setValue(WATERLOGGED, false)
                .setValue(LIT, false)
                .setValue(LAMP_TYPE, LampType.SINGLE)
                .setValue(LAMPSHADE, ColorList.WHITE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(LAMP_TYPE)){
            case SINGLE -> Shapes.or(SINGLE, MIDDLE, TOP);
            case BOTTOM -> Shapes.or(BOTTOM, MIDDLE);
            case MIDDLE -> MIDDLE;
            case TOP ->  Shapes.or(TOP, MIDDLE);
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof LampBlock) {
                BlockPos currentPos = pos.above();

                while (level.getBlockState(currentPos).getBlock() instanceof LampBlock) {
                    currentPos = currentPos.above();
                }

                if (level.getBlockState(currentPos).canBeReplaced() && currentPos.getY() <= level.getMaxBuildHeight()) {
                    BlockPos topLampPos = currentPos.below();
                    BlockState topLampState = level.getBlockState(topLampPos);

                    if (topLampState.getValue(LAMP_TYPE) == LampType.SINGLE) {
                        level.setBlock(topLampPos, topLampState.setValue(LAMP_TYPE, LampType.BOTTOM), 3);
                    } else {
                        level.setBlock(topLampPos, topLampState.setValue(LAMP_TYPE, LampType.MIDDLE), 3);
                    }

                    level.setBlock(currentPos, state.setValue(LAMP_TYPE, LampType.TOP), 3);
                }
            } else {
                BlockPos topLampPos = pos;
                while (level.getBlockState(topLampPos.above()).getBlock() instanceof LampBlock) {
                    topLampPos = topLampPos.above();
                }

                float f = level.getBlockState(topLampPos).getValue(LIT) ? 0.8F : 0.7F;
                if (state.getValue(LIT)) {
                    level.playSound(null, pos, YAFSoundEvents.LAMP_OFF.get(), SoundSource.BLOCKS, f, 1.0F);
                } else {
                    level.playSound(null, pos, YAFSoundEvents.LAMP_ON.get(), SoundSource.BLOCKS, f, 1.0F);
                }

                level.setBlock(topLampPos, level.getBlockState(topLampPos).cycle(LIT), 3);

            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        boolean waterlogged = level.getFluidState(pos).getType().isSame(Fluids.WATER);

        return this.defaultBlockState()
                .setValue(WATERLOGGED, waterlogged)
                .setValue(LIT, false)
                .setValue(LAMPSHADE, ColorList.WHITE)
                .setValue(LAMP_TYPE, LampType.SINGLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, LIT, LAMP_TYPE, LAMPSHADE);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }

        var thisType = state.getValue(LAMP_TYPE);
        var aboveState = level.getBlockState(pos.above());

        if (!(aboveState.getBlock() instanceof LampBlock)) {
            if (thisType == LampType.MIDDLE) {
                level.setBlock(pos, state.setValue(LAMP_TYPE, LampType.TOP), 3);
            }

            if (thisType == LampType.BOTTOM) {
                level.setBlock(pos, state.setValue(LAMP_TYPE, LampType.SINGLE), 3);
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        LampType type = state.getValue(LAMP_TYPE);

        boolean belowIsLamp = level.getBlockState(pos.below()).getBlock() instanceof LampBlock;
        boolean aboveIsLamp = level.getBlockState(pos.above()).getBlock() instanceof LampBlock;

        return switch (type) {
            case SINGLE -> super.canSurvive(state, level, pos);
            case MIDDLE, TOP -> belowIsLamp;
            case BOTTOM -> aboveIsLamp;
        };
    }

    public enum LampType implements StringRepresentable {
        SINGLE("single"),
        BOTTOM("bottom"),
        MIDDLE("middle"),
        TOP("top");

        private final String name;

        LampType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
