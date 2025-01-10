package com.anticheatgpt2.checks;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReachCheck implements Listener {

    private static final double MAX_REACH_DISTANCE = 3.0; // Максимальная допустимая дистанция удара (в блоках)

    public static boolean check(Player player) {
        return false;
    }

    // Проверка на аномально большое расстояние удара
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        // Если удар был нанесен по игроку
        if (event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            Entity attacker = event.getDamager();

            // Если атакующий — это игрок
            if (attacker instanceof Player) {
                Player attackerPlayer = (Player) attacker;

                // Расчет дистанции между атакующим и целью
                double distance = attacker.getLocation().distance(target.getLocation());

                // Проверяем, если дистанция между игроками больше допустимого значения
                if (distance > MAX_REACH_DISTANCE) {
                    // Подозрительный удар с дальностью больше разрешенной
                    event.setCancelled(true); // Отменяем повреждение
                    attackerPlayer.sendMessage("§cПодозрительный удар! Возможно, манипуляции с reach.");
                }
            }
        }
    }
}
