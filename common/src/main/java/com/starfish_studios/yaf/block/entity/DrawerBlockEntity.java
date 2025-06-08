package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.block.CabinetBlock;
import com.starfish_studios.yaf.block.DrawerBlock;
import com.starfish_studios.yaf.registry.YAFBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DrawerBlockEntity extends AbstractDrawerBlockEntity {

    public DrawerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(YAFBlockEntities.DRAWER.get(), blockPos, blockState);
    }

    void updateTopState(BlockState blockState, boolean bl) {
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), blockState.setValue(DrawerBlock.TOP_OPEN, bl), 3);
    }

    void updateBottomState(BlockState blockState, boolean bl) {
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), blockState.setValue(DrawerBlock.BOTTOM_OPEN, bl), 3);
    }

    @Override
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
        Pair<Integer, Direction> raycastResult;

        HitResult hitResult = player.pick(10, 1, false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            Vec3 localHitPos = blockHitResult.getLocation().subtract(Vec3.atCenterOf(blockHitResult.getBlockPos()));

            var bl = this.getBlockState().getBlock() instanceof CabinetBlock;
            int relativePos = getRelativeDrawerPos(blockHitResult.getDirection(), localHitPos, bl);

            raycastResult = new Pair<>(relativePos, blockHitResult.getDirection());
            if (raycastResult.getFirst() < 7) {
                this.updateBottomState(this.getBlockState(), true);
            } else {
                this.updateTopState(this.getBlockState(), true);
            }
        }
    }

    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }

        Pair<Integer, Direction> raycastResult;

        HitResult hitResult = player.pick(10, 1, false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            Vec3 localHitPos = blockHitResult.getLocation().subtract(Vec3.atCenterOf(blockHitResult.getBlockPos()));

            var bl = this.getBlockState().getBlock() instanceof CabinetBlock;
            int relativePos = getRelativeDrawerPos(blockHitResult.getDirection(), localHitPos, bl);

            raycastResult = new Pair<>(relativePos, blockHitResult.getDirection());
            if (raycastResult.getFirst() < 7) {
                this.updateBottomState(this.getBlockState(), false);
            } else {
                this.updateTopState(this.getBlockState(), false);
            }
        }
    }
}
