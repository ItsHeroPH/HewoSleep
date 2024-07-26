package com.github.itsheroph.hewosleep.models.menu.bypass;

import com.github.itsheroph.hewosleep.models.SleepUser;
import com.github.itsheroph.hewosleep.models.configuration.BypassConfig;
import com.github.itsheroph.hewoutil.gui.HewoPlayerMenu;
import com.github.itsheroph.hewoutil.gui.item.HewoMenuItem;
import com.github.itsheroph.hewoutil.gui.menu.HewoMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class BypassMenu extends HewoMenu {

    public BypassMenu(HewoPlayerMenu playerMenu, SleepUser user) {

        super(playerMenu);
        this.getPlayerMenu().setData("user", user);

    }

    @Override
    public String getName() {

        return "&3&lBypass &7&lConfiguration";

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

        BypassConfig config = this.getUser().getWorld().getBypassConfig();

        ItemStack creative = HewoMenuItem.create(Material.GRASS_BLOCK)
                .setDisplayName("&3&lCreative")
                .setLore((config.isCreativeIgnored() ? "&6&lIgnore: &fTrue" : "&6&lIgnore: &fFalse"),
                        "&3&l[Right Click] &fset ignore to &7True",
                        "&3&l[Left Click] &fset ignore to &7False"
                ).build();

        ItemStack survival = HewoMenuItem.create(Material.IRON_SWORD)
                .setDisplayName("&4&lSurvival")
                .setLore((config.isSurvivalIgnored() ? "&6&lIgnore: &fTrue" : "&6&lIgnore: &fFalse"),
                        "&3&l[Right Click] &fset ignore to &7True",
                        "&3&l[Left Click] &fset ignore to &7False"
                ).addFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP).build();

        ItemStack adventure = HewoMenuItem.create(Material.MAP)
                .setDisplayName("&b&lAdventure")
                .setLore((config.isAdventureIgnored() ? "&6&lIgnore: &fTrue" : "&6&lIgnore: &fFalse"),
                        "&3&l[Right Click] &fset ignore to &7True",
                        "&3&l[Left Click] &fset ignore to &7False"
                ).build();

        ItemStack spectator = HewoMenuItem.create(Material.ENDER_EYE)
                .setDisplayName("&8&lSpectator")
                .setLore((config.isSpectatorIgnored() ? "&6&lIgnore: &fTrue" : "&6&lIgnore: &fFalse"),
                        "&3&l[Right Click] &fset ignore to &7True",
                        "&3&l[Left Click] &fset ignore to &7False"
                ).build();

        ItemStack afk = HewoMenuItem.create(Material.FISHING_ROD)
                .setDisplayName("&7&lAFK")
                .setLore((config.isAfkPlayerIgnored() ? "&6&lIgnore: &fTrue" : "&6&lIgnore: &fFalse"),
                        "&3&l[Right Click] &fset ignore to &7True",
                        "&3&l[Left Click] &fset ignore to &7False"
                ).build();

        ItemStack vanish = HewoMenuItem.create(Material.BARRIER)
                .setDisplayName("&7&lVanish")
                .setLore((config.isVanishPlayerIgnored() ? "&6&lIgnore: &fTrue" : "&6&lIgnore: &fFalse"),
                        "&3&l[Right Click] &fset ignore to &7True",
                        "&3&l[Left Click] &fset ignore to &7False"
                ).build();

        this.setItem(1, creative);
        this.setItem(3, survival);
        this.setItem(5, adventure);
        this.setItem(7, spectator);
        this.setItem(21, afk);
        this.setItem(23, vanish);

        this.fillEmpty();

    }

    @Override
    public void onClick(InventoryClickEvent event) {

        BypassConfig config = this.getUser().getWorld().getBypassConfig();
        Player player = this.getPlayerMenu().getOwner();

        if(event.getClick() == ClickType.RIGHT) {

            switch(event.getSlot()) {

                case 1:

                    config.setIgnoreCreative(true);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;
                case 3:

                    config.setIgnoreSurvival(true);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 5:

                    config.setIgnoreAdventure(true);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 7:

                    config.setIgnoreSpectator(true);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 21:

                    config.setIgnoreAfkPlayer(true);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 23:

                    config.setIgnoreVanishPlayer(true);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

            }

        } else if(event.getClick() == ClickType.LEFT) {

            switch(event.getSlot()) {

                case 1:

                    config.setIgnoreCreative(false);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;
                case 3:

                    config.setIgnoreSurvival(false);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 5:

                    config.setIgnoreAdventure(false);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 7:

                    config.setIgnoreSpectator(false);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 21:

                    config.setIgnoreAfkPlayer(false);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

                case 23:

                    config.setIgnoreVanishPlayer(false);
                    this.setMenuItems();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);

                    break;

            }

        }

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

        Player player = this.getPlayerMenu().getOwner();

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);

    }

    private SleepUser getUser() {

        return this.getPlayerMenu().getData("user", SleepUser.class);

    }
}
