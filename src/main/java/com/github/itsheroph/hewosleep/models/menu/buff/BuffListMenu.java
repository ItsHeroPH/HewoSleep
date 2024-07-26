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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class BuffListMenu extends HewoMenu {

    public BuffListMenu(HewoPlayerMenu playerMenu) {

        super(playerMenu);

    }

    @Override
    public String getName() {

        return "&3&l" + this.getPage() + " &7List";

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

        switch (this.getPage()) {

            case "Buffs":

                PotionEffect[] buffs = this.getUser().getWorld().getBuffConfig().getBuffsList().toArray(new PotionEffect[0]);
                for(int i = 0; i < buffs.length; i++) {

                    ItemStack potionItem = HewoMenuItem.create(Material.POTION)
                            .setDisplayName("&a" + buffs[i].getType().getName().replaceAll("_", " "))
                            .setLore("&7&oTime: &3" + (buffs[i].getDuration()  / 20) + "s",
                                    "&7&oLevel: &3" + (buffs[i].getAmplifier() + 1)
                            ).addFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();

                    PotionMeta meta = (PotionMeta) potionItem.getItemMeta();
                    meta.addCustomEffect(buffs[i], true);

                    potionItem.setItemMeta(meta);

                    this.setItem(i, potionItem);

                }
                break;

            case "DeBuffs":

                PotionEffect[] deBuffs = this.getUser().getWorld().getBuffConfig().getDeBuffsList().toArray(new PotionEffect[0]);
                for(int i = 0; i < deBuffs.length; i++) {

                    ItemStack potionItem = HewoMenuItem.create(Material.POTION)
                            .setDisplayName("&c" + deBuffs[i].getType().getName().replaceAll("_", " "))
                            .setLore("&7&oTime: &3" + (deBuffs[i].getDuration()  / 20) + "s",
                                    "&7&oLevel: &3" + (deBuffs[i].getAmplifier() + 1)
                            ).addFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();

                    PotionMeta meta = (PotionMeta) potionItem.getItemMeta();
                    meta.addCustomEffect(deBuffs[i], true);

                    potionItem.setItemMeta(meta);

                    this.setItem(i, potionItem);

                }
                break;

        }

        this.setItem(26,
                HewoMenuItem.create(Material.OAK_DOOR)
                        .setDisplayName("&4&lGo Back")
                        .glow()
                        .build()
        );

        this.fillEmpty();
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        if(event.getSlot() == 26) {

            this.back();

        }

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

        Player player = this.getPlayerMenu().getOwner();

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

    }

    private SleepUser getUser() {

        return this.getPlayerMenu().getData("user", SleepUser.class);

    }

    private String getPage() {

        return (String) this.getPlayerMenu().getData("page");

    }

}
