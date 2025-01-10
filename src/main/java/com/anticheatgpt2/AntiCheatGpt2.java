package com.anticheatgpt2;

import com.anticheatgpt2.checks.FlyCheck;
import com.anticheatgpt2.checks.HitboxCheck; // Импортируем HitboxCheck
import com.anticheatgpt2.checks.SpeedCheck;
import com.anticheatgpt2.checks.ReachCheck;
import com.anticheatgpt2.utils.AlertUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class AntiCheatGpt2 extends JavaPlugin implements Listener {

    private Map<Player, Integer> alertCounts;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        alertCounts = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new FlyCheck(), this); // Регистрируем FlyCheck
        getServer().getPluginManager().registerEvents(new HitboxCheck(), this); // Регистрируем HitboxCheck
        getLogger().info("AntiCheatGpt2 включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AntiCheatGpt2 выключен!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        alertCounts.putIfAbsent(player, 0); // Инициализируем счетчик алертов при входе игрока
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Проверка на спидхак
        if (SpeedCheck.check(player)) {
            alertPlayer(player, "Обнаружен спидхак! Игрок двигается слишком быстро.", "Speed");
        }

        // Проверка на флай
        if (FlyCheck.check(player)) {
            alertPlayer(player, "Обнаружен флай! Игрок движется слишком быстро вертикально.", "Fly");
        }

        // Проверка на Rich (богатство)
        if (ReachCheck.check(player)) {
            alertPlayer(player, "Обнаружено богатство через читы! Игрок имеет слишком хорошие предметы.", "Rich");
        }
    }

    // Метод для отправки алертов и обновления счетчиков
    private void alertPlayer(Player player, String message, String cheatType) {
        // Увеличиваем счетчик алертов
        alertCounts.put(player, alertCounts.getOrDefault(player, 0) + 1);

        // Отправка сообщения игроку и в консоль
        AlertUtil.sendAlert(player, message, cheatType);
    }

    // Обработчик команды /anticheat
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("anticheat")) {
            if (args.length == 0) {
                sender.sendMessage("§7Используйте /anticheat info для информации о плагине.");
            } else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("§eAntiCheatGpt2 §7v1.0.0");
                sender.sendMessage("§7Доступные команды: /anticheat clear, /anticheat info");
            }
            return true;
        }

        // Команда очистки алертов
        if (command.getName().equalsIgnoreCase("anticheat") && args[0].equalsIgnoreCase("clear")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                alertCounts.put(player, 0); // Очищаем счетчик алертов
                player.sendMessage("§7Ваши алерты были очищены.");
            } else {
                sender.sendMessage("§cКоманду можно использовать только игрокам.");
            }
            return true;
        }

        return false;
    }
}
