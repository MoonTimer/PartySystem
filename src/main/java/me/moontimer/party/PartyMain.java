package me.moontimer.party;

import me.moontimer.party.commands.PartyCommand;
import me.moontimer.party.listener.OnServerSwitchListener;
import me.moontimer.party.listener.PlayerLeaveListener;
import me.moontimer.party.manager.PartyManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class PartyMain extends Plugin {

    private static PartyMain instance;

    public static String PREFIX = "§cFireRush §8» §7";
    private PartyManager partyManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        partyManager = new PartyManager();
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PartyCommand("party"));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new OnServerSwitchListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerLeaveListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PartyMain getInstance() {
        return instance;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }
}
