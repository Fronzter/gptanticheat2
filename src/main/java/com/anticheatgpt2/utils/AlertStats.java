package com.anticheatgpt2.utils;

import java.util.HashMap;
import java.util.Map;

public class AlertStats {

    private static final Map<String, Map<String, Integer>> playerAlerts = new HashMap<>();

    // Метод для увеличения количества алертов для конкретного игрока
    public static void incrementAlert(String playerName, String alertType) {
        Map<String, Integer> alerts = playerAlerts.getOrDefault(playerName, new HashMap<>());
        alerts.put(alertType, alerts.getOrDefault(alertType, 0) + 1);
        playerAlerts.put(playerName, alerts);
    }

    // Метод для получения статистики по алертам игрока
    public static String getAlertStats(String playerName) {
        Map<String, Integer> alerts = playerAlerts.getOrDefault(playerName, new HashMap<>());
        StringBuilder stats = new StringBuilder();

        for (Map.Entry<String, Integer> entry : alerts.entrySet()) {
            stats.append(entry.getValue()).append("x ").append(entry.getKey()).append(", ");
        }

        if (stats.length() > 0) {
            stats.delete(stats.length() - 2, stats.length()); // Убираем последнюю запятую
        }

        return stats.toString();
    }
}
