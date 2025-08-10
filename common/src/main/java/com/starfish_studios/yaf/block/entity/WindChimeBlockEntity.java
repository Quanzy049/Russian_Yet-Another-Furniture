package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.registry.YAFBlockEntities;
import net.minecraft.util.Mth;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;

import java.util.Random;
public class WindChimeBlockEntity extends BlockEntity {
    private final Random random = new Random();
    private int tickCounter = 0;
    private int animateTicksRemaining = 0;
    private float animationProgress = 0.0f;
    private static final int FADE_IN_TICKS = 15;
    private static final int FADE_OUT_TICKS = 40;

    private final SwingData baseSwingX = new SwingData();
    private final SwingData baseSwingZ = new SwingData();
    private final SwingData[] chimeSwingsX = new SwingData[4];
    private final SwingData[] chimeSwingsZ = new SwingData[4];

    private final SwingData idleBaseSwingX = new SwingData();
    private final SwingData idleBaseSwingZ = new SwingData();
    private final SwingData[] idleChimeSwingsX = new SwingData[4];
    private final SwingData[] idleChimeSwingsZ = new SwingData[4];
    private int idleTickCounter = 0;
    private boolean idleTickFlip = false;

    public WindChimeBlockEntity(BlockPos pos, BlockState state) {
        super(YAFBlockEntities.CHIME.get(), pos, state);
        randomize();
    }

    private void randomize() {
        baseSwingX.randomizeBase(random);
        baseSwingZ.randomizeBase(random);

        for (int i = 0; i < 4; i++) {
            chimeSwingsX[i] = new SwingData();
            chimeSwingsZ[i] = new SwingData();
            chimeSwingsX[i].randomizeChime(random, baseSwingX);
            chimeSwingsZ[i].randomizeChime(random, baseSwingZ);
        }

        idleBaseSwingX.randomizeBase(random);
        idleBaseSwingZ.randomizeBase(random);
        for (int i = 0; i < 4; i++) {
            idleChimeSwingsX[i] = new SwingData();
            idleChimeSwingsZ[i] = new SwingData();
            idleChimeSwingsX[i].randomizeChime(random, idleBaseSwingX);
            idleChimeSwingsZ[i].randomizeChime(random, idleBaseSwingZ);
        }
    }

    public void triggerAnimate(int ticks) {
        this.animateTicksRemaining = Math.max(this.animateTicksRemaining, Math.max(0, ticks));
    }

    public void commonTick(Level level, BlockState state) {
        if (level.isClientSide) {
            if (level instanceof ClientLevel clientLevel) {
                clientTick(clientLevel);
            }
        }
    }

    public void clientTick(ClientLevel clientLevel) {
        boolean active = animateTicksRemaining > 0;

        if (active) {
            animationProgress = Math.min(1.0f, animationProgress + (1.0f / FADE_IN_TICKS));
        } else {
            animationProgress = Math.max(0.0f, animationProgress - (1.0f / FADE_OUT_TICKS));
        }

        idleTickFlip = !idleTickFlip;
        if (idleTickFlip) {
            idleTickCounter++;
        }
        idleBaseSwingX.update(idleTickCounter, 0.006f, clientLevel.random);
        idleBaseSwingZ.update(idleTickCounter, 0.006f, clientLevel.random);
        for (int i = 0; i < 4; i++) {
            float idleFactor = 0.25f + (i * 0.03f);
            idleChimeSwingsX[i].update(idleTickCounter, idleFactor, clientLevel.random);
            idleChimeSwingsZ[i].update(idleTickCounter, idleFactor, clientLevel.random);
        }

        if (animationProgress > 0.0f || active) {
            tickCounter++;

            baseSwingX.update(tickCounter, 0.01f, clientLevel.random);
            baseSwingZ.update(tickCounter, 0.01f, clientLevel.random);

            for (int i = 0; i < 4; i++) {
                float boundFactor = 0.7f + (i * 0.1f);
                chimeSwingsX[i].update(tickCounter, boundFactor, clientLevel.random);
                chimeSwingsZ[i].update(tickCounter, boundFactor, clientLevel.random);
            }
        }

        if (animateTicksRemaining > 0) animateTicksRemaining--;
    }

    public float getBaseSwingAngleX(float partialTick) {
        return baseSwingX.getInterpolatedAngle(partialTick);
    }

    public float getBaseSwingAngleZ(float partialTick) {
        return baseSwingZ.getInterpolatedAngle(partialTick);
    }

    public float getChimeSwingAngleX(int index, float partialTick) {
        return chimeSwingsX[index].getInterpolatedAngle(partialTick);
    }

    public float getChimeSwingAngleZ(int index, float partialTick) {
        return chimeSwingsZ[index].getInterpolatedAngle(partialTick);
    }

    public float getIdleBaseSwingAngleX(float partialTick) {
        return idleBaseSwingX.getInterpolatedAngle(partialTick);
    }

    public float getIdleBaseSwingAngleZ(float partialTick) {
        return idleBaseSwingZ.getInterpolatedAngle(partialTick);
    }

    public float getIdleChimeSwingAngleX(int index, float partialTick) {
        return idleChimeSwingsX[index].getInterpolatedAngle(partialTick);
    }

    public float getIdleChimeSwingAngleZ(int index, float partialTick) {
        return idleChimeSwingsZ[index].getInterpolatedAngle(partialTick);
    }

    private static class SwingData {
        private static final float BASE_SPEED = 0.095f;
        private static final int RANDOM_TICK_INTERVAL = 200;
        private static final float DEGREE_MOD = 4.2f;

        private float currentAngle = 0;
        private float previousAngle = 0;
        private float swingSpeed;
        private float swingOffset;
        private float targetSpeed;
        private float targetOffset;

        void randomizeBase(Random random) {
            swingSpeed = BASE_SPEED + random.nextFloat() * BASE_SPEED;
            swingOffset = random.nextFloat() * Mth.TWO_PI;
            targetSpeed = swingSpeed;
            targetOffset = swingOffset;
        }

        void randomizeChime(Random random, SwingData base) {
            swingSpeed = base.swingSpeed + (random.nextFloat() * 0.0125f);
            swingOffset = random.nextFloat() * Mth.TWO_PI;
            targetSpeed = swingSpeed;
            targetOffset = swingOffset;
        }

        void update(int tickCounter, float boundFactor, RandomSource random) {
            if (tickCounter % RANDOM_TICK_INTERVAL == 0) {
                targetSpeed = BASE_SPEED + random.nextFloat() * BASE_SPEED;
                targetOffset = random.nextFloat() * Mth.TWO_PI;
            }

            swingSpeed = Mth.lerp(0.005f, swingSpeed, targetSpeed);
            swingOffset = Mth.lerp(0.005f, swingOffset, targetOffset);

            float targetAngle = Mth.sin(tickCounter * swingSpeed + swingOffset) * (DEGREE_MOD * boundFactor);
            previousAngle = currentAngle;
            currentAngle = targetAngle;
        }

        float getInterpolatedAngle(float partialTick) {
            return Mth.lerp(partialTick, previousAngle, currentAngle);
        }
    }

    public float getAnimationFactor() {
        float t = Mth.clamp(animationProgress, 0.0f, 1.0f);
        return t * t * (3.0f - 2.0f * t);
    }
}