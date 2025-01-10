package com.anticheatgpt2.checks;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpeedCheck {

    private static final double MAX_SPEED = 0.25; // Максимальная скорость для спидхака (в блоках за тик)

    public static boolean check(Player player) {
        Vector velocity = player.getVelocity();

        // Расчет горизонтальной скорости игрока
        double horizontalSpeed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());

        // Проверка: если скорость игрока превышает допустимую
        if (horizontalSpeed > MAX_SPEED && !player.isFlying()) {
            return true;
        }

        return false;
    }
}
