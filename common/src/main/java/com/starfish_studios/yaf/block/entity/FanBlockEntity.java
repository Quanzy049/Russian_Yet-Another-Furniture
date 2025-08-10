package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.registry.YAFBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;

import java.util.List;

public class FanBlockEntity extends BlockEntity {

    private static final int MAX_DISTANCE = 8;
    private static final double MAX_FORCE = 0.25;
    private static final double MIN_FORCE = 0.05;

    private static final float MAX_SPEED = 40.0f;
    private static final float ACCELERATION = 0.5f;
    private static final float DECELERATION = 0.3f;

    private float rotationSpeed = 0.0f;
    private float currentRotation = 0.0f;

    public boolean fanOn = false;

    public FanBlockEntity(BlockPos pos, BlockState blockState) {
        super(YAFBlockEntities.FAN.get(), pos, blockState);
    }

    public void commonTick(Level level, BlockState state) {
        if (level.isClientSide()) {
            if (fanOn) {
                rotationSpeed = Math.min(rotationSpeed + ACCELERATION, MAX_SPEED);
            } else {
                rotationSpeed = Math.max(rotationSpeed - DECELERATION, 0);
            }
            currentRotation = (currentRotation + rotationSpeed) % 360;
        }

        if (!fanOn) {
            return;
        }

        BlockPos pos = getBlockPos();
        Direction direction = state.getValue(BlockStateProperties.FACING);
        if (direction == Direction.DOWN) {
            return;
        }

        Vec3 fanPos = Vec3.atCenterOf(pos);
        Vec3i pushDirection = direction.getNormal();

        if (level.isClientSide()) {
            if (level.getGameTime() % 10 == 0) {
                Vec3 directionVec = new Vec3(pushDirection.getX(), pushDirection.getY(), pushDirection.getZ());
                Vec3 baseSpawnPos = fanPos.add(directionVec.scale(0.5));
                Vec3 spawnPos;
                if (direction == Direction.UP) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 2.0;
                    double offsetZ = (level.random.nextDouble() - 0.5) * 2.0;
                    spawnPos = baseSpawnPos.add(offsetX, 0, offsetZ);
                } else {
                    Vec3 up = new Vec3(0, 1, 0);
                    Vec3 side = directionVec.cross(up).normalize();
                    double offsetSide = (level.random.nextDouble() - 0.5) * 2.0;
                    double offsetVertical = (level.random.nextDouble() - 0.5) * 2.0;
                    spawnPos = baseSpawnPos.add(side.scale(offsetSide)).add(0, offsetVertical, 0);
                }
                Vec3 velocity = directionVec.scale(0.25);
                level.addParticle(ParticleTypes.POOF, spawnPos.x, spawnPos.y, spawnPos.z,
                        velocity.x, velocity.y, velocity.z);
            }
        }

        AABB affectedArea = new AABB(pos.relative(direction, 1)).inflate(1.05)
                .expandTowards(
                        pushDirection.getX() * MAX_DISTANCE,
                        pushDirection.getY() * MAX_DISTANCE,
                        pushDirection.getZ() * MAX_DISTANCE
                );

        List<Entity> entities = level.getEntities(null, affectedArea).stream()
                .filter(it -> !(it instanceof Player player && player.isCreative() && player.getAbilities().flying))
                .toList();

        double multiplier = 1.0;
        if (direction == Direction.UP) {
            multiplier = 2.0;
        }

        for (Entity entity : entities) {
            double distance = entity.position().distanceTo(fanPos);
            double force = Mth.lerp(distance / MAX_DISTANCE, MAX_FORCE, MIN_FORCE);
            force *= multiplier;

            Vec3 currentMotion = entity.getDeltaMovement();
            Vec3 pushVelocity = new Vec3(
                    pushDirection.getX() * force,
                    pushDirection.getY() * force,
                    pushDirection.getZ() * force
            );

            Vec3 newMotion = currentMotion.add(pushVelocity.subtract(currentMotion)).scale(0.2);
            entity.setDeltaMovement(currentMotion.add(newMotion));
        }
    }

    public float getRotationAngle(float partialTick) {
        return (currentRotation + rotationSpeed * partialTick) % 360;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("On", fanOn);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fanOn = tag.getBoolean("On");
    }
}
