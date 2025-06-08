package com.starfish_studios.yaf.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starfish_studios.yaf.block.entity.DrawerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DrawerBlockEntityRenderer implements BlockEntityRenderer<DrawerBlockEntity> {

    public DrawerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(DrawerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

    }
}
