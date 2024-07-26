package com.github.itsheroph.hewosleep.models.menu.buff;

import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewoutil.gui.HewoPlayerMenu;
import com.github.itsheroph.hewoutil.gui.item.HewoMenuItem;
import com.github.itsheroph.hewoutil.gui.menu.HewoMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BuffMenu extends HewoMenu {

    public BuffMenu(HewoPlayerMenu playerMenu, SleepUser user) {

        super(playerMenu);

        this.getPlayerMenu().setData("user", user);

    }

    @Override
    public String getName() {

        return "&3&lHewoSleep V2";

    }

    @Override
    public int getSlots() {

        return 27;

    }

    @Override
    public boolean cancelClicks() {

        return true;

    }

    @Override
    public void setMenuItems() {

        ItemStack buff = HewoMenuItem.create(Material.SPLASH_POTION)
                .setDisplayName("&a&lBuffs")
                .setLore("&7&oSee the list of sleeping buffs")
                .addEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 1))
                .addFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .build();

        ItemStack deBuff = HewoMenuItem.create(Material.SPLASH_POTION)
                .setDisplayName("&c&lDeBuffs")
                .setLore("&7&oSee the list of debuffs")
                .addEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 1))
                .addFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .build();

        this.setItem(12, buff);
        this.setItem(14, deBuff);
        this.fillEmpty();

    }

    @Override
    public void onClick(InventoryClickEvent event) {

        switch(event.getSlot()) {

            case 12:

                this.getPlayerMenu().setData("page", "Buffs");
                new BuffListMenu(this.getPlayerMenu()).open();

                break;
            case 14:

                this.getPlayerMenu().setData("page", "DeBuffs");
                new BuffListMenu(this.getPlayerMenu()).open();

                break;

        }

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

        Player player = this.getPlayerMenu().getOwner();

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

    }

}
