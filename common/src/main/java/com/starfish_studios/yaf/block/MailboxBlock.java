package com.starfish_studios.yaf.block;

import com.starfish_studios.yaf.block.entity.MailboxBlockEntity;
import com.starfish_studios.yaf.block.properties.FlagStatus;
import com.starfish_studios.yaf.inventory.MailboxMenu;
import com.starfish_studios.yaf.registry.YAFBlockEntities;
import com.starfish_studios.yaf.world.YAFSavedData;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MailboxBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<FlagStatus> FLAG_STATUS = EnumProperty.create("flag", FlagStatus.class);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty FENCE_BELOW = BooleanProperty.create("fence_below");
//    public static final EnumProperty<ColorList> COLOR = NCBlockProperties.COLOR;

    public static final VoxelShape NORTH_AABB = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 12.0D, 16.0D);
    public static final VoxelShape NORTH_FENCE_AABB = Block.box(4.0D, 0.0D, -7.0D, 12.0D, 12.0D, 10.0D);
    public static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    public static final VoxelShape EAST_FENCE_AABB = Block.box(6.0D, 0.0D, 4.0D, 23.0D, 12.0D, 12.0D);
    public static final VoxelShape SOUTH_AABB = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 12.0D, 16.0D);
    public static final VoxelShape SOUTH_FENCE_AABB = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 12.0D, 23.0D);
    public static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    public static final VoxelShape WEST_FENCE_AABB = Block.box(-7.0D, 0.0D, 4.0D, 10.0D, 12.0D, 12.0D);

    public MailboxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(FLAG_STATUS, FlagStatus.DOWN)
                .setValue(OPEN, false)
                .setValue(FENCE_BELOW, false));
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            default -> blockState.getValue(FENCE_BELOW) ? NORTH_FENCE_AABB : NORTH_AABB;
            case EAST -> blockState.getValue(FENCE_BELOW) ? EAST_FENCE_AABB : EAST_AABB;
            case SOUTH -> blockState.getValue(FENCE_BELOW) ? SOUTH_FENCE_AABB : SOUTH_AABB;
            case WEST -> blockState.getValue(FENCE_BELOW) ? WEST_FENCE_AABB : WEST_AABB;
        };
    }

    public BlockState open(BlockState blockState, Level level, BlockPos blockPos) {
        blockState = blockState.cycle(OPEN);
        level.setBlock(blockPos, blockState, 3);
        return blockState;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            MailboxBlockEntity mailboxBlockEntity = (MailboxBlockEntity) blockEntity;
            if(player.isShiftKeyDown()) {

                if (state.getValue(FLAG_STATUS) == FlagStatus.DOWN) {
                    assert blockEntity != null;
                    player.displayClientMessage(
                            Component.translatable("yaf.mailbox.sending",
                                            mailboxBlockEntity.getMailboxName(),
                                            "XYZ: " + pos.getX() + " / " + pos.getY() + " / "  + pos.getZ()
                            ).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC), true
                    );


                } else if (state.getValue(FLAG_STATUS) == FlagStatus.UP) {
                    assert blockEntity != null;
                    player.displayClientMessage(
                            Component.translatable("yaf.mailbox.receiving",
                                    mailboxBlockEntity.getMailboxName(),
                                    "XYZ: " + pos.getX() + " / " + pos.getY() + " / "  + pos.getZ()
                            ).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC), true
                    );
                }


                BlockState blockState2;
                blockState2 = this.open(state, level, pos);
                float f = blockState2.getValue(FLAG_STATUS) == FlagStatus.UP ? 0.5F : 0.6F;
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
                level.setBlock(pos, state.cycle(FLAG_STATUS), 3);
            } else if (blockEntity instanceof MailboxBlockEntity mailboxBlockEntity1) {
                if (player instanceof ServerPlayer serverPlayer) {
                    MenuRegistry.openExtendedMenu(serverPlayer, new ExtendedMenuProvider() {
                        @Override
                        public void saveExtraData(FriendlyByteBuf buf) {
                            buf.writeBlockPos(pos.immutable());
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                            var buf = new FriendlyByteBuf(Unpooled.buffer());
                            saveExtraData(buf);
                            return new MailboxMenu(id, inventory, buf);
                        }

                        @Override
                        public Component getDisplayName() {
                            return mailboxBlockEntity1.getDisplayName();
                        }
                    });
                }

                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, YAFBlockEntities.MAILBOX.get(), MailboxBlockEntity::sendItemsTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(FACING, WATERLOGGED, FLAG_STATUS, OPEN, FENCE_BELOW);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.getStateDefinition().any()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, waterlogged)
                .setValue(FLAG_STATUS, FlagStatus.DOWN)
                .setValue(OPEN, false)
                .setValue(FENCE_BELOW, context.getLevel().getBlockState(context.getClickedPos().below()).is(BlockTags.FENCES));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean isPowered = level.hasNeighborSignal(pos);
            FlagStatus newStatus = isPowered ? FlagStatus.UP : FlagStatus.DOWN;
            if (state.getValue(FLAG_STATUS) != newStatus) {
                level.setBlock(pos, state.setValue(FLAG_STATUS, newStatus), 3);
                float pitch = newStatus == FlagStatus.UP ? 0.6F : 0.5F;
                level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, pitch);
            }
        }
        super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
    }


    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof Container) {
                Containers.dropContents(level, blockPos, (Container)blockEntity);
                level.updateNeighbourForOutputSignal(blockPos, this);
            }

            if (level instanceof ServerLevel serverLevel) {
                var mailboxes = YAFSavedData.getMailboxes(serverLevel);
                var globalPos = GlobalPos.of(level.dimension(), blockPos);

                mailboxes.mailboxes.removeIf(entry -> entry.globalPos().equals(globalPos));
                mailboxes.setDirty();
            }

            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if (blockEntity instanceof MailboxBlockEntity) {
            if (player != null && !itemStack.hasCustomHoverName()) {
                ((MailboxBlockEntity)blockEntity).setCustomName(Component.translatable("container.mailbox.player_name", player.getDisplayName()));
            } else if (itemStack.hasCustomHoverName()) {
                ((MailboxBlockEntity)blockEntity).setCustomName(itemStack.getHoverName());
            }

            if (level instanceof ServerLevel serverLevel) {
                var mailboxes = YAFSavedData.getMailboxes(serverLevel);
                var globalPos = GlobalPos.of(level.dimension(), blockPos);

                mailboxes.mailboxes.add(new YAFSavedData.MailboxData(
                        player != null ? player.getDisplayName().getString() : "PlayerName",
                        itemStack.hasCustomHoverName() ? itemStack.getHoverName().getString() : "PlayerMailbox",
                        globalPos
                ));
                mailboxes.setDirty();
            }
        }
    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof MailboxBlockEntity mailboxBlockEntity) {
            mailboxBlockEntity.recheckOpen();
        }
    }


    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MailboxBlockEntity(blockPos, blockState);
    }
}
