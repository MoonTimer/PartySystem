package me.moontimer.party.listener;

import me.moontimer.party.PartyMain;
import me.moontimer.party.manager.Party;
import me.moontimer.party.manager.PartyManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnServerSwitchListener implements Listener {

    @EventHandler
    public void onConnect(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (PartyMain.getInstance().getPartyManager().getParty(player) != null && PartyMain.getInstance().getPartyManager().getParty(player).getLeader().equals(player)) {
            Party party = PartyMain.getInstance().getPartyManager().getParty(player);
            for (ProxiedPlayer players : party.getPlayers()) {
                Server target = player.getServer();
                if (!players.getServer().equals(target)) {
                    players.connect(target.getInfo());
                }
            }
        }
    }
}
