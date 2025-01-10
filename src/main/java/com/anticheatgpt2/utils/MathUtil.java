package com.anticheatgpt2.utils;

import org.bukkit.entity.Player;

public class MathUtil {

    // Метод для вычисления реальной скорости игрока по осям X и Z
    public static double calculatePlayerSpeed(Player player) {
        double deltaX = player.getLocation().getX() - player.getLocation().getX();
        double deltaZ = player.getLocation().getZ() - player.getLocation().getZ();

        // Используем стандартную формулу для вычисления расстояния между точками на плоскости
        return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }
}
