package com.starfish_studios.yaf.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starfish_studios.yaf.block.LampBlock;
import com.starfish_studios.yaf.block.entity.DrawerBlockEntity;
import com.starfish_studios.yaf.block.entity.LampBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class LampBlockEntityRenderer implements BlockEntityRenderer<LampBlockEntity> {

    public LampBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(LampBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

    }
}
