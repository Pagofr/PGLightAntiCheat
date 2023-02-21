package pagofr.lightanticheat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pagofr.lightanticheat.extra.Alerts;
import pagofr.lightanticheat.extra.Metrics;
import pagofr.lightanticheat.extra.Tps;
import pagofr.lightanticheat.checks.Listeners;
import pagofr.lightanticheat.checks.combat.CombatCheck;
import pagofr.lightanticheat.checks.interaction.BlockBreakingCheck;
import pagofr.lightanticheat.checks.interaction.BlockPlacementCheck;
import pagofr.lightanticheat.checks.movement.MovementCheck;
import pagofr.lightanticheat.players.LACPlayer;
import pagofr.lightanticheat.players.ViolationUpdate;
import pagofr.lightanticheat.usage.Commands;
import pagofr.lightanticheat.usage.Config;

import java.util.Objects;

public class LightAntiCheat extends JavaPlugin {

    private static LightAntiCheat lightAntiCheat;

    @Override
    public void onEnable() {
        lightAntiCheat = this;
        saveDefaultConfig();
        Config.updateByConfigUpdater();
        reloadConfig();
        Config.getValuesFromConfig();
        Objects.requireNonNull(LightAntiCheat.getInstance().getCommand("lightanticheat")).setExecutor(new Commands());
        LACPlayer.addAllPlayers();
        Tps.enableTpsCalculation();
        loadBukkitListeners();
        ViolationUpdate.enableViolationUpdate();
        Alerts.checkForUnsupportedVersion();
        Alerts.checkForUpdates();
        new Metrics(this, 12841);
    }

    public static LightAntiCheat getInstance() {
        return lightAntiCheat;
    }

    private void loadBukkitListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MovementCheck(), this);
        pluginManager.registerEvents(new CombatCheck(), this);
        pluginManager.registerEvents(new BlockPlacementCheck(), this);
        pluginManager.registerEvents(new BlockBreakingCheck(), this);
        pluginManager.registerEvents(new Listeners(), this);
    }

}
