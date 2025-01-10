package com.anticheatgpt2.checks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class FlyCheck implements Listener {

    private static final long MAX_AIR_TIME_MS = 3000; // Максимальное время в воздухе (3 секунды)
    private static final double MAX_VERTICAL_SPEED = 1.0; // Максимальная вертикальная скорость
    private final Map<Player, Long> airTimeMap = new HashMap<>(); // Время нахождения игрока в воздухе

    public static boolean check(Player player) {
        return false;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Игнорируем игроков в особых состояниях
        if (player.isFlying() || player.isInsideVehicle() || player.getAllowFlight() || isInLiquid(player) || isOnClimbable(player)) {
            airTimeMap.remove(player);
            return;
        }

        // Проверка вертикальной скорости
        double verticalSpeed = Math.abs(event.getTo().getY() - event.getFrom().getY());
        if (verticalSpeed > MAX_VERTICAL_SPEED) {
            alert(player, "Подозрение на Fly! Слишком большая вертикальная скорость.");
            return;
        }

        // Проверка времени в воздухе
        if (!player.isOnGround()) {
            airTimeMap.putIfAbsent(player, System.currentTimeMillis());
            long airTime = System.currentTimeMillis() - airTimeMap.get(player);

            if (airTime > MAX_AIR_TIME_MS) {
                alert(player, "Подозрение на Fly! Игрок слишком долго находится в воздухе.");
                airTimeMap.remove(player); // Сброс времени в воздухе
            }
        } else {
            airTimeMap.remove(player); // Сбрасываем счетчик, если игрок на земле
        }
    }

    // Метод для отправки предупреждения
    private void alert(Player player, String message) {
        player.sendMessage("§c" + message);
        System.out.println("[AntiCheat] " + player.getName() + ": " + message);
    }

    // Проверка, находится ли игрок в жидкости
    private boolean isInLiquid(Player player) {
        Material material = player.getLocation().getBlock().getType();
        return material == Material.WATER || material == Material.LAVA;
    }

    // Проверка, находится ли игрок на лестнице или лиане
    private boolean isOnClimbable(Player player) {
        Material material = player.getLocation().getBlock().getType();
        return material == Material.LADDER || material == Material.VINE;
    }
}
