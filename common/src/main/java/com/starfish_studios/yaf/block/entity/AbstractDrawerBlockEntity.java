//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.block.CabinetBlock;
import com.starfish_studios.yaf.block.DrawerBlock;
import com.starfish_studios.yaf.block.properties.CountertopType;
import com.starfish_studios.yaf.inventory.DrawerMenu;
import com.mojang.datafixers.util.Pair;
import com.starfish_studios.yaf.mixin.CustomNameAccessor;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractDrawerBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider, Nameable {
    private static final SoundEvent SOUND_OPEN = SoundEvents.BARREL_OPEN;
    private static final SoundEvent SOUND_CLOSE = SoundEvents.BARREL_CLOSE;

    public CountertopType countertopType;
    private NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    public ContainerOpenersCounter openersCounter;

    public AbstractDrawerBlockEntity(BlockEntityType<?> be, BlockPos blockPos, BlockState blockState) {
        super(be, blockPos, blockState);
        countertopType = CountertopType.getFromState(blockState);

        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
                AbstractDrawerBlockEntity.this.playSound(blockState, SOUND_OPEN);
            }

            protected void onClose(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
                AbstractDrawerBlockEntity.this.playSound(blockState, SOUND_CLOSE);
            }

            protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, int i, int j) {
            }

            protected boolean isOwnContainer(@NotNull Player player) {
                if (player.containerMenu instanceof DrawerMenu) {
                    Container container = ((DrawerMenu) player.containerMenu).getContainer();
                    return container == AbstractDrawerBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, HolderLookup.Provider registries) {
        super.saveAdditional(compoundTag, registries);
        ContainerHelper.saveAllItems(compoundTag, this.items, registries);
        if (countertopType != null) {
            compoundTag.putString("CountertopType", countertopType.getSerializedName());
        }
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag compoundTag, HolderLookup.Provider registries) {
        super.loadAdditional(compoundTag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items, registries);
        if (compoundTag.contains("CountertopType")) {
            String countertopName = compoundTag.getString("CountertopType");
            this.countertopType = CountertopType.valueOf(countertopName.toUpperCase());
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {

        CompoundTag compoundtag = new CompoundTag();
        if (countertopType != null) {
            compoundtag.putString("CountertopType", countertopType.getSerializedName());
        }

        return compoundtag;
    }

    public int getContainerSize() {
        return 10;
    }

    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(@NotNull NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.drawer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, Player player) {
        Pair<Integer, Direction> raycastResult = new Pair<>(0, Direction.UP);

        assert level != null;
        BlockPos blockPos = this.getBlockPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        HitResult hitResult = player.pick(10, 1, false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            Vec3 localHitPos = blockHitResult.getLocation().subtract(Vec3.atCenterOf(blockHitResult.getBlockPos()));

            boolean isCabinet = this.getBlockState().getBlock() instanceof CabinetBlock;
            int relativePos = getRelativeDrawerPos(blockHitResult.getDirection(), localHitPos, isCabinet);
            raycastResult = new Pair<>(relativePos, blockHitResult.getDirection());
        }

        if (raycastResult.getSecond() == this.getBlockState().getValue(DrawerBlock.FACING)) {
            if (blockEntity instanceof AbstractDrawerBlockEntity abstractDrawerBlockEntity) {
                if (abstractDrawerBlockEntity instanceof CabinetBlockEntity) {
                    // Swap left/right naming: positive dot now means right side.
                    if (raycastResult.getFirst() > 0) {
                        ((CustomNameAccessor)abstractDrawerBlockEntity).setName(Component.translatable("container.cabinet_right"));
                    } else {
                        ((CustomNameAccessor)abstractDrawerBlockEntity).setName(Component.translatable("container.cabinet_left"));
                    }
                } else {
                    if (raycastResult.getFirst() > 7) {
                        ((CustomNameAccessor)abstractDrawerBlockEntity).setName(Component.translatable("container.drawer_top"));
                    } else {
                        ((CustomNameAccessor)abstractDrawerBlockEntity).setName(Component.translatable("container.drawer_bottom"));
                    }
                }

                if (player instanceof ServerPlayer serverPlayer) {
                    openScreen(abstractDrawerBlockEntity, serverPlayer, raycastResult);
                }
            }
        }
        return null;
    }

    private void openScreen(AbstractDrawerBlockEntity blockEntity, ServerPlayer player, Pair<Integer, Direction> raycastResult) {
        MenuRegistry.openExtendedMenu(player, new ExtendedMenuProvider() {
            @Override
            public void saveExtraData(FriendlyByteBuf buf) {

            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                int drawerIndex = (blockEntity instanceof CabinetBlockEntity)
                        ? (raycastResult.getFirst() > 0 ? 5 : 0)  // Right side gets index 5, left side gets 0.
                        : (raycastResult.getFirst() < 7 ? 0 : 5);
                return new DrawerMenu(id, inventory, blockEntity, drawerIndex);
            }

            @Override
            public Component getDisplayName() {
                return blockEntity.getDisplayName();
            }
        });
    }

    // UNUSED, SHOULDN'T BE CALLED (?)
    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory) {
        return new DrawerMenu(i, inventory, this, 0);
    }

    public int getRelativeDrawerPos(Direction direction, Vec3 localHitPos, boolean isCabinet) {
        if (isCabinet) {
            Direction facing = this.getBlockState().getValue(DrawerBlock.FACING);
            double dot = getDot(localHitPos, facing);
            return (int) Math.floor(dot * 16);
        } else {
            return -switch (direction) {
                case NORTH, SOUTH, EAST, WEST -> (int) Math.floor(1.0 - (localHitPos.y() + 0.5) * 16);
                case UP -> (int) Math.floor((localHitPos.z() + 0.5) * 16);
                case DOWN -> (int) Math.floor(1.0 - (localHitPos.z() + 0.5) * 16);
            };
        }
    }

    private static double getDot(Vec3 localHitPos, Direction facing) {
        int leftX = 0, leftZ = 0;
        switch (facing) {
            case NORTH -> leftX = -1;
            case SOUTH -> leftX = 1;
            case EAST  -> leftZ = -1;
            case WEST  -> leftZ = 1;
            default -> { }
        }
        return localHitPos.x() * leftX + localHitPos.z() * leftZ;
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }

    void playSound(BlockState blockState, SoundEvent soundEvent) {
        // For cabinets, play sound from the center of the block.
        if (blockState.getBlock() instanceof CabinetBlock) {
            double d = this.worldPosition.getX() + 0.5;
            double e = this.worldPosition.getY() + 0.5;
            double f = this.worldPosition.getZ() + 0.5;
            assert this.level != null;
            this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
        } else {
            Vec3i vec3i = blockState.getValue(DrawerBlock.FACING).getNormal();
            double d = this.worldPosition.getX() + 0.5 + (double) vec3i.getX() / 2.0;
            double e = this.worldPosition.getY() + 0.5 + (double) vec3i.getY() / 2.0;
            double f = this.worldPosition.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
            assert this.level != null;
            this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
