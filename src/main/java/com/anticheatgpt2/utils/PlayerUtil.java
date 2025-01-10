package com.anticheatgpt2.utils;

import org.bukkit.entity.Player;

public class PlayerUtil {

    public static boolean isHoldingItem(Player player) {
        return player.getInventory().getItemInMainHand() != null;
    }

    public static boolean isInAir(Player player) {
        return !player.isOnGround();
    }
}
