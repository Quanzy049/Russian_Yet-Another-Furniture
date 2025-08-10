package com.starfish_studios.yaf.client.renderer.blockentity;

import com.starfish_studios.yaf.YetAnotherFurniture;
import com.starfish_studios.yaf.block.WindChimeBlock;
import com.starfish_studios.yaf.block.entity.WindChimeBlockEntity;
import com.starfish_studios.yaf.client.model.ChimeBlockEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChimeBlockEntityRenderer implements BlockEntityRenderer<WindChimeBlockEntity> {

    ChimeBlockEntityModel model;

    public ChimeBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        model = new ChimeBlockEntityModel(context.bakeLayer(ChimeBlockEntityModel.LAYER_LOCATION));
    }

    public ResourceLocation getTextureLocation(WindChimeBlockEntity entity) {
        var material = ((WindChimeBlock) entity.getBlockState().getBlock()).material;
        return new ResourceLocation(YetAnotherFurniture.MOD_ID, "textures/block/wind_chime/" + material + "_wind_chimes_world.png");
    }

    @Override
    public void render(WindChimeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        float animFactor = blockEntity.getAnimationFactor();

        float baseSwingAngleX = blockEntity.getBaseSwingAngleX(partialTick) * animFactor
                + blockEntity.getIdleBaseSwingAngleX(partialTick) * (1.0f - animFactor);
        float baseSwingAngleZ = blockEntity.getBaseSwingAngleZ(partialTick) * animFactor
                + blockEntity.getIdleBaseSwingAngleZ(partialTick) * (1.0f - animFactor);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(blockEntity)));

        model.base.xRot = (float) Math.toRadians(baseSwingAngleX * 15.0f * animFactor);
        model.base.zRot = (float) Math.toRadians(baseSwingAngleZ * 15.0f * animFactor);

        for (int i = 0; i < 4; i++) {
            float chimeAngle = blockEntity.getChimeSwingAngleX(i, partialTick) * animFactor
                    + blockEntity.getIdleChimeSwingAngleX(i, partialTick) * (1.0f - animFactor);
            model.getChimeBound(i + 1).xRot = (float) Math.toRadians(chimeAngle);
            model.getChime(i + 1).xRot = (float) Math.toRadians(chimeAngle);

            float chimeAngleZ = blockEntity.getChimeSwingAngleZ(i, partialTick) * animFactor
                    + blockEntity.getIdleChimeSwingAngleZ(i, partialTick) * (1.0f - animFactor);
            model.getChimeBound(i + 1).zRot = (float) Math.toRadians(chimeAngleZ);
            model.getChime(i + 1).zRot = (float) Math.toRadians(chimeAngleZ);
        }

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1f, 1f, 1f, 1f);
        poseStack.popPose();
    }

}
