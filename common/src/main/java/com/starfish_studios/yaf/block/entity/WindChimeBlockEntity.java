package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.registry.YAFBlockEntities;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
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

    private final SwingData baseSwingX = new SwingData();
    private final SwingData baseSwingZ = new SwingData();
    private final SwingData[] chimeSwingsX = new SwingData[4];
    private final SwingData[] chimeSwingsZ = new SwingData[4];

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
    }

    public void commonTick(Level level, BlockState state) {
        if (level.isClientSide) {
            if (level instanceof ClientLevel clientLevel) {
                clientTick(clientLevel);
            }
        }
    }

    public void clientTick(ClientLevel clientLevel) {
        tickCounter++;

        baseSwingX.update(tickCounter, 0.01f, clientLevel.random);
        baseSwingZ.update(tickCounter, 0.01f, clientLevel.random);

        for (int i = 0; i < 4; i++) {
            float boundFactor = 0.7f + (i * 0.1f);
            chimeSwingsX[i].update(tickCounter, boundFactor, clientLevel.random);
            chimeSwingsZ[i].update(tickCounter, boundFactor, clientLevel.random);
        }
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

    private static class SwingData {
        private static final float BASE_SPEED = 0.065f;
        private static final int RANDOM_TICK_INTERVAL = 200;
        private static final float DEGREE_MOD = 3.0f;

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
            swingSpeed = base.swingSpeed + (random.nextFloat() * 0.0075f);
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
}