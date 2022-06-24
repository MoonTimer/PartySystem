package me.moontimer.party.manager;

import me.moontimer.party.PartyMain;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {

    public List<Party> parties;

    public PartyManager() {
        parties = new ArrayList<>();
    }

    public void createParty(ProxiedPlayer player) {
        parties.add(new Party(player));
    }



    public void invite(ProxiedPlayer inviter, ProxiedPlayer invitedPlayer) {
        if (getParty(inviter) == null) {
            createParty(inviter);
            inviter.sendMessage(new TextComponent(PartyMain.PREFIX + "§cDu besitzt noch keine Party, es wurde für dich eine erstellt und der Spieler eingeladen"));
        }
        Party party = getParty(inviter);
        if (party.getLeader().equals(inviter)) {
            if (!party.getPlayers().contains(invitedPlayer)) {
                party.invite(invitedPlayer);
                inviter.sendMessage(new TextComponent(PartyMain.PREFIX + invitedPlayer.getName() + " §awurde in deine Party eingeladen"));
            } else {
                inviter.sendMessage(new TextComponent(PartyMain.PREFIX + "Der Spieler ist bereits in deiner Party"));
            }
        } else {
            inviter.sendMessage(new TextComponent(PartyMain.PREFIX + "Dir gehört diese Party nicht, somit kannst du keine personen in die Party einladen"));
        }
    }

    public void acceptInvite(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Party party : parties) {
            if (party.getLeader().equals(leader)) {
                Invite invite = party.getInvite(player);
                if (invite != null) {
                    party.removeInvite(invite);
                    party.acceptInvite(invite);
                    player.sendMessage(new TextComponent(PartyMain.PREFIX + "Du hast die PartyAnfrage angenommen"));
                    return;
                }
            }
        }
        player.sendMessage(new TextComponent(PartyMain.PREFIX + "Du wurdest in keine Party von diesem spieler eingeladen"));
    }

    public void denyInvite(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Party party : parties) {
            if (party.getLeader().equals(leader)) {
                Invite invitation = party.getInvite(player);
                if (invitation != null) {
                    party.removeInvite(invitation);
                    player.sendMessage(new TextComponent(PartyMain.PREFIX + "Du hast diese Anfrage abgelehnt"));
                    return;
                }
            }
        }
        player.sendMessage(new TextComponent(PartyMain.PREFIX + "Du besitzt keine Anfrage von diesem Spieler"));
    }

    public Party getParty(ProxiedPlayer player) {
        for (Party party : parties) {
            if (party.getPlayers().contains(player) || party.getLeader().equals(player)) {
                return party;
            }
        }
        return null;
    }

    public List<Party> getParties() {
        return parties;
    }
}
