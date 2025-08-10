//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.starfish_studios.yaf.inventory;

import com.starfish_studios.yaf.registry.YAFMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DrawerMenu extends AbstractContainerMenu {
    private static final int SLOT_COUNT = 5;
    private final Container drawer;

    public DrawerMenu(int i, Inventory inventory, FriendlyByteBuf buf) {
        this(i, inventory, new SimpleContainer(SLOT_COUNT), 0);
    }

    public Container getContainer() {
        return this.drawer;
    }

    public DrawerMenu(int i, Inventory inventory, Container container, int listOffset) {
        super(YAFMenus.DRAWER.get(), i);
        AbstractContainerMenu.checkContainerSize(container, SLOT_COUNT);
        this.drawer = container;
        container.startOpen(inventory.player);
        int j;
        int k;
        for(j = 0; j < 1; ++j) {
            for(k = 0; k < 5; ++k) {
                this.addSlot(new Slot(container, listOffset + k + j * 5, 44 + k * 18, 35 + j * 18));
            }
        }

        for(j = 0; j < 3; ++j) {
            for(k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for(j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }

    }

    public boolean stillValid(Player player) {
        return this.drawer.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < SLOT_COUNT) {
                if (!this.moveItemStackTo(itemStack2, SLOT_COUNT, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.drawer.stopOpen(player);
    }
}
