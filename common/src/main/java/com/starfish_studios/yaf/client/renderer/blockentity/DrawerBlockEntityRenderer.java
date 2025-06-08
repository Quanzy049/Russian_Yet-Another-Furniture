package com.starfish_studios.yaf.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.starfish_studios.yaf.YetAnotherFurniture;
import com.starfish_studios.yaf.block.CabinetBlock;
import com.starfish_studios.yaf.block.entity.AbstractDrawerBlockEntity;
import com.starfish_studios.yaf.block.entity.CabinetBlockEntity;
import com.starfish_studios.yaf.client.model.DrawerCountertopModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class DrawerBlockEntityRenderer<T extends AbstractDrawerBlockEntity> implements BlockEntityRenderer<T> {

    private static Model model;

    public DrawerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        model = new DrawerCountertopModel(context.bakeLayer(DrawerCountertopModel.LAYER_LOCATION));
    }

    public ResourceLocation getTextureLocation(AbstractDrawerBlockEntity entity) {
        var material = entity.countertopType.toString().toLowerCase(Locale.ROOT);
        return YetAnotherFurniture.id("textures/entity/drawer_countertop/" + material + ".png");
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        if (blockEntity instanceof CabinetBlockEntity cabinetBlockEntity) {
            var blockState = cabinetBlockEntity.getBlockState();
            if (blockState.hasProperty(CabinetBlock.BOTTOM) && blockState.getValue(CabinetBlock.BOTTOM)) {
                poseStack.translate(0.5D, 1.5D, 0.5D);
            } else {
                poseStack.translate(0.5D, 0.75D, 0.5D);
            }
        } else {
            poseStack.translate(0.5D, 1.5D, 0.5D);
        }
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(blockEntity)));

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, -1);
        poseStack.popPose();
    }
}
