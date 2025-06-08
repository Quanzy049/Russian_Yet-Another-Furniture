package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.registry.YAFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LampBlockEntity extends BlockEntity {
    public LampBlockEntity(BlockPos pos, BlockState blockState) {
        super(YAFBlockEntities.LAMP.get(), pos, blockState);
    }
}
