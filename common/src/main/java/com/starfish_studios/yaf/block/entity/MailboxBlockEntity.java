package com.starfish_studios.yaf.block.entity;

import com.starfish_studios.yaf.YetAnotherFurniture;
import com.starfish_studios.yaf.block.MailboxBlock;
import com.starfish_studios.yaf.block.properties.FlagStatus;
import com.starfish_studios.yaf.inventory.MailboxMenu;
import com.starfish_studios.yaf.registry.YAFBlockEntities;
import com.starfish_studios.yaf.registry.YAFSoundEvents;
import com.starfish_studios.yaf.world.YAFSavedData;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class MailboxBlockEntity extends BlockEntity
        implements Container,
        Nameable {
    public boolean lockTarget = false;
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;
    public String targetString = "";
    private int sendDelay = 20 * 2;
    @Nullable
    private Component name;
    public static ResourceLocation packetChannel = new ResourceLocation(YetAnotherFurniture.MOD_ID, "sync_mailbox_fail");
    public static ResourceLocation packetChannel2 = new ResourceLocation(YetAnotherFurniture.MOD_ID, "sync_mailbox_text_reset");

    public boolean failedToSend = false;

    public MailboxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(YAFBlockEntities.MAILBOX.get(), blockPos, blockState);
        this.items = NonNullList.withSize(5, ItemStack.EMPTY);

        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
                MailboxBlockEntity.this.playSound(blockState, YAFSoundEvents.MAILBOX_OPEN.get());
                MailboxBlockEntity.this.updateBlockState(blockState, true);
            }

            protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
                MailboxBlockEntity.this.playSound(blockState, YAFSoundEvents.MAILBOX_CLOSE.get());
                MailboxBlockEntity.this.updateBlockState(blockState, false);
            }

            protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof MailboxMenu) {
                    Container container = ((MailboxMenu)player.containerMenu).getContainer();
                    return container == MailboxBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    public Component getMailboxName() {
        return this.getDisplayName();
    }


    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.items);
        if (!targetString.isEmpty()) {
            compoundTag.putString("Target", targetString);
        }
        compoundTag.putInt("SendDelay", sendDelay);
        if (this.name != null) {
            compoundTag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        compoundTag.putBoolean("FailedToSend", failedToSend);
        compoundTag.putBoolean("LockTarget", lockTarget);
    }

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        if (compoundTag.contains("Target")) {
            targetString = compoundTag.getString("Target");
        }
        sendDelay = compoundTag.getInt("SendDelay");
        if (compoundTag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(compoundTag.getString("CustomName"));
        }
        failedToSend = compoundTag.getBoolean("FailedToSend");
        lockTarget = compoundTag.getBoolean("LockTarget");
    }

    private static boolean canMergeItems(ItemStack stack1, ItemStack stack2) {
        return stack1.getCount() <= stack1.getMaxStackSize() && ItemStack.isSameItemSameTags(stack1, stack2);
    }

    private void serverTick() {
        boolean validFlagState = this.getBlockState().getValue(MailboxBlock.FLAG_STATUS) == FlagStatus.UP;
        if (validFlagState && !items.isEmpty() && !targetString.isEmpty()) {

            var targetMailbox = getTargetMailbox();

            if (targetMailbox != null) {

                if (sendDelay > 0) {
                    sendDelay--;
                } else {
                    sendDelay = 20 * 2;

                    if (sendToMailboxItems(targetMailbox)) {
                        failedToSend = false;
                        sendMessageState(failedToSend);
                        this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(MailboxBlock.FLAG_STATUS, FlagStatus.DOWN), 3);
                        if (!lockTarget) {
                            targetString = "";
                            if (level instanceof ServerLevel serverLevel) {
                                var list = serverLevel.getChunkSource().chunkMap.getPlayers(new ChunkPos(getBlockPos()), false);
                                for (ServerPlayer serverPlayer : list) {
                                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                                    buf.writeBlockPos(getBlockPos());
                                    NetworkManager.sendToPlayer(serverPlayer, packetChannel2, buf);
                                }
                            }
                            setChanged();
                        }
                    } else {
                        sendMessageState(failedToSend);
                    }

                    setChanged();
                }
            } else {
                sendMessageState(failedToSend);
            }
        }
    }

    private void sendMessageState(boolean fail){

        if (level instanceof ServerLevel serverLevel) {
            var list = serverLevel.getChunkSource().chunkMap.getPlayers(new ChunkPos(getBlockPos()), false);
            for (ServerPlayer serverPlayer : list) {
                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                buf.writeBlockPos(getBlockPos());
                buf.writeBoolean(fail);
                NetworkManager.sendToPlayer(serverPlayer, packetChannel, buf);
            }
        }
    }

    private MailboxBlockEntity getTargetMailbox() {
        if (level == null || level.isClientSide) {
            return null;
        }

        GlobalPos globalPos = resolveBlockPos(targetString);
        if (globalPos == null) {
            return null;
        }

        var server = level.getServer();
        if (server == null) {
            return null;
        }

        var dimLevel = server.getLevel(globalPos.dimension());
        if (dimLevel == null || dimLevel.isClientSide) {
            return null;
        }

        var be = dimLevel.getBlockEntity(globalPos.pos());
        if (be instanceof MailboxBlockEntity) {
            return (MailboxBlockEntity) be;
        }

        return null;
    }

    private boolean sendToMailboxItems(MailboxBlockEntity targetMailbox) {
        boolean validFlagState = this.getBlockState().getValue(MailboxBlock.FLAG_STATUS) == FlagStatus.UP;
        boolean validTargetFlag = targetMailbox.getBlockState().getValue(MailboxBlock.FLAG_STATUS) == FlagStatus.DOWN;

        if (!validFlagState || !validTargetFlag) {
            return false;
        }

        boolean sentMail = false;

        for (int o = 0; o < this.items.size(); o++) {
            ItemStack toSendStack = this.items.get(o).copy();

            if (!toSendStack.isEmpty()) {
                toSendStack.getOrCreateTag().putString("mailboxTooltip", "From: " + this.getMailboxName().getString());
                toSendStack.getOrCreateTag().putInt("mailboxTooltipTimer", -1);

                boolean merged = false;

                for (int i = 0; i < targetMailbox.items.size(); i++) {
                    ItemStack targetStack = targetMailbox.items.get(i);

                    if (!targetStack.isEmpty() && canMergeItems(targetStack, toSendStack)) {
                        int spaceAvailable = targetStack.getMaxStackSize() - targetStack.getCount();
                        int transferAmount = Math.min(toSendStack.getCount(), spaceAvailable);

                        if (transferAmount > 0) {
                            targetStack.grow(transferAmount);
                            targetStack.getOrCreateTag().putString("mailboxTooltip", "From: " + this.getMailboxName().getString());
                            targetStack.getOrCreateTag().putInt("mailboxTooltipTimer", -1);

                            toSendStack.shrink(transferAmount);
                            sentMail = true;

                            if (toSendStack.isEmpty()) {
                                items.set(o, ItemStack.EMPTY);
                                merged = true;
                                break;
                            }
                        }
                    }
                }
                if (!merged) {
                    for (int i = 0; i < targetMailbox.items.size(); i++) {
                        if (targetMailbox.items.get(i).isEmpty()) {
                            targetMailbox.items.set(i, toSendStack);
                            items.set(o, ItemStack.EMPTY);
                            sentMail = true;
                            break;
                        }
                    }
                }
            }
        }
        return sentMail;
    }

    private GlobalPos resolveBlockPos(String name) {
        List<Function<String, GlobalPos>> resolvers = List.of(
                this::checkCoordinates,
                this::checkPlayerName,
                this::checkMailboxName
        );

        for (Function<String, GlobalPos> resolver : resolvers) {
            GlobalPos blockPos = resolver.apply(name);
            if (blockPos != null) {
                return blockPos;
            }
        }

        return null;
    }

    private GlobalPos checkMailboxName(String name) {
        if (level instanceof ServerLevel serverLevel) {
            var data = YAFSavedData.getMailboxes(serverLevel);

            return data.mailboxes.stream()
                    .filter(mailbox -> mailbox.name().equals(name))
                    .map(YAFSavedData.MailboxData::globalPos)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private GlobalPos checkPlayerName(String name) {
        if (level instanceof ServerLevel serverLevel) {
            var data = YAFSavedData.getMailboxes(serverLevel);

            return data.mailboxes.stream()
                    .filter(mailbox -> mailbox.playerName().equals(name))
                    .map(YAFSavedData.MailboxData::globalPos)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private GlobalPos checkCoordinates(String name) {
        if (name.matches("-?\\d+ -?\\d+ -?\\d+")) {
            String[] coords = name.split(" ");

            if (coords.length == 3) {
                try {
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);
                    int z = Integer.parseInt(coords[2]);

                    assert this.level != null;
                    return GlobalPos.of(this.level.dimension(), new BlockPos(x, y, z));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid coordinate format: " + name);
                }
            } else {
                System.out.println("Coordinate name format is invalid: " + name);
            }
        }
        return null;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.mailbox");
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, slot, amount);
        if (!itemStack.isEmpty()) {
            this.setChanged();
        }
        return itemStack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }

    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }

    }

    void updateBlockState(BlockState blockState, boolean bl) {
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), blockState.setValue(MailboxBlock.OPEN, bl), 3);
    }

    void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vec3i vec3i = blockState.getValue(MailboxBlock.FACING).getNormal();
        double d = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        assert this.level != null;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public static void sendItemsTick(Level level, BlockPos pos, BlockState state, MailboxBlockEntity mailboxBlockEntity) {
        if (!level.isClientSide) {
            mailboxBlockEntity.serverTick();
        }
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public @NotNull Component getName() {
        assert name != null;
        return name;
    }

    public void setCustomName(Component translatable) {
        this.name = translatable;
    }
}
