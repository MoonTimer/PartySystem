package me.moontimer.party.manager;

import me.moontimer.party.PartyMain;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Party {

    private ProxiedPlayer leader;
    private final List<ProxiedPlayer> players;
    private final Map<ProxiedPlayer, Invite> invites;

    public Party(ProxiedPlayer leader) {
        this.leader = leader;
        players = new ArrayList<>();
        invites = new HashMap<>();
    }

    public void invite(ProxiedPlayer player) {
        if (!invites.containsKey(player) || players.contains(player)) {
            invites.put(player, new Invite(player, this, 5));
        }
    }

    public void acceptInvite(Invite invite) {
        players.add(invite.getPlayer());
        partyMessage(PartyMain.PREFIX + invite.getPlayer().getName() + " §aist der Party beigetreten");
    }

    public void partyMessage(String message) {
        leader.sendMessage(new TextComponent(message));
        for (ProxiedPlayer player : players) {
            player.sendMessage(new TextComponent(message));
        }
    }

    public void removeInvite(Invite invite) {
        invites.remove(invite.getPlayer());
    }

    public Invite getInvite(ProxiedPlayer player) {
        return invites.get(player);
    }

    public void deleteParty() {
        partyMessage(PartyMain.PREFIX + "Die Party wurde aufeglöst");
        PartyMain.getInstance().getPartyManager().getParties().remove(this);
    }

    public void leaveFromParty(ProxiedPlayer player) {
        players.remove(player);
        partyMessage(PartyMain.PREFIX + "Der Spieler " + player.getName() + " hat die Party verlassen");
    }


    public List<ProxiedPlayer> getPlayers() {
        return players;
    }

    public ProxiedPlayer getLeader() {
        return leader;
    }

    public Map<ProxiedPlayer, Invite> getInvites() {
        return invites;
    }
}
