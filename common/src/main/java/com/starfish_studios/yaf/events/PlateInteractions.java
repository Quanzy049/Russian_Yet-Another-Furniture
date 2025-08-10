package com.starfish_studios.yaf.events;
/*
import com.starfish_studios.yaf.blocks.PlateBlock;
import com.starfish_studios.yaf.blocks.SofaBlock;
import com.starfish_studios.yaf.blocks.properties.FoodList;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

public class PlateInteractions implements UseBlockCallback {
    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (block instanceof PlateBlock) {
            Item foodItem = itemStack.getItem();
            FoodList food = getFoodFor(foodItem);
            if (food != FoodList.EMPTY) {
                level.setBlockAndUpdate(pos, blockState.setValue(PlateBlock.FOOD, getBlockstateForFood(foodItem, blockState).getValue(PlateBlock.FOOD)));
                level.playSound(null, pos, SoundEvents.CHICKEN_EGG, player.getSoundSource(), 1.0F, 1.0F);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static BlockState getBlockstateForFood(Item item, BlockState blockState) {
        return blockState.setValue(PlateBlock.FOOD, getFoodFor(item));
    }

    public static FoodList getFoodFor(Item item) {
        if (item == Items.APPLE) {
            return FoodList.APPLE;
        } else if (item == Items.BAKED_POTATO) {
            return FoodList.BAKED_POTATO;
        } else if (item == Items.BEETROOT) {
            return FoodList.BEETROOT;
        } else if (item == Items.BREAD) {
            return FoodList.BREAD;
        } else if (item == Items.CARROT) {
            return FoodList.CARROT;
        } else if (item == Items.COOKIE) {
            return FoodList.COOKIE;
        } else if (item == Items.POTATO) {
            return FoodList.POTATO;
        } else {
            return FoodList.EMPTY;
        }
    }

}

 */
