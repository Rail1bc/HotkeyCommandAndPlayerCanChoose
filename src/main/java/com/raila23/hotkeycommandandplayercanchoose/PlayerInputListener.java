package com.raila23.hotkeycommandandplayercanchoose;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.Iterator;

public class PlayerInputListener implements Listener {
    private HotkeyCommandAndPlayerCanChoose plugin;
    private PlayerChoose PC;

    public PlayerInputListener(HotkeyCommandAndPlayerCanChoose plugin) {
        this.plugin = plugin;
        PC = new PlayerChoose(this.plugin);
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        boolean isOp = player.isOp();
        Iterator var4;
        String cmd1233;
        if(PC.PlayerNoNeedAll(name))return;
        if (event.getPlayer().isSneaking() && this.plugin.Shift_Hand ) {
            if(PC.PlayerNoNeedShiftWithHand(name))return;
            event.setCancelled(true);
            try {
                player.setOp(true);
                var4 = this.plugin.ShiftHand.iterator();

                while(var4.hasNext()) {
                    cmd1233 = (String)var4.next();
                    Bukkit.dispatchCommand(player, cmd1233);
                }

                player.setOp(isOp);
            } catch (Exception var18) {
            } finally {
                player.setOp(isOp);
            }
        }
        else if (this.plugin.Hand && !player.isSneaking()) {
            if(PC.PlayerNoNeedHand(name))return;
            event.setCancelled(true);
            player.setSprinting(false);

            try {
                player.setOp(true);
                var4 = this.plugin.F.iterator();

                while(var4.hasNext()) {
                    cmd1233 = (String)var4.next();
                    Bukkit.dispatchCommand(player, cmd1233);
                }

                player.setOp(isOp);
            } catch (Exception var16) {
            } finally {
                player.setOp(isOp);
            }
        }

    }

    @EventHandler
    public void onQ(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        boolean isOp;
        Iterator var4;
        String cmd0;
        if(PC.PlayerNoNeedAll(name))return;
        if (this.plugin.Switch_ShiftQ && event.getPlayer().isSneaking() ) {
            if(PC.PlayerNoNeedShiftWithQ(name))return;
            event.setCancelled(true);
            isOp = player.isOp();

            try {
                player.setOp(true);
                var4 = this.plugin.ShiftQ.iterator();

                while(var4.hasNext()) {
                    cmd0 = (String)var4.next();
                    Bukkit.dispatchCommand(player, cmd0);
                }

                player.setOp(isOp);
            } catch (Exception var18) {
            } finally {
                player.setOp(isOp);
            }
        }
        else if (this.plugin.Switch_Q && !player.isSneaking()) {
            if(PC.PlayerNoNeedQ(name))return;
            event.setCancelled(true);
            isOp = player.isOp();

            try {
                player.setOp(true);
                var4 = this.plugin.Q.iterator();

                while(var4.hasNext()) {
                    cmd0 = (String)var4.next();
                    Bukkit.dispatchCommand(player, cmd0);
                }

                player.setOp(isOp);
            } catch (Exception var16) {
            } finally {
                player.setOp(isOp);
            }
        }

    }
}
