package com.anticheatgpt2.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AlertUtil {

    // Метод для отправки предупреждения (алерта)
    public static void sendAlert(Player player, String message, String cheatType) {
        // Отправка сообщения игроку
        player.sendMessage("§c[AntiCheat] §7" + message);

        // Отправка сообщения в консоль
        Bukkit.getLogger().info("§c[AntiCheat] §7" + player.getName() + " подозревается в " + cheatType + ": " + message);

        // Вы можете добавить дополнительные способы уведомления, например, в чат команды, отправка сообщений в другие каналы и т.д.
    }
}
