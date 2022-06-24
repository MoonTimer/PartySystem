package me.moontimer.party.listener;

import me.moontimer.party.PartyMain;
import me.moontimer.party.manager.Party;
import me.moontimer.party.manager.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (PartyMain.getInstance().getPartyManager().getParty(player) != null && PartyMain.getInstance().getPartyManager().getParty(player).getLeader().equals(player)) {
            PartyMain.getInstance().getPartyManager().getParty(player).deleteParty();
        }
    }
}
