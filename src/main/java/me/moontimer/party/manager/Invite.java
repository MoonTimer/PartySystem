package me.moontimer.party.manager;

import me.moontimer.party.PartyMain;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public class Invite {

    private final ProxiedPlayer player;
    private final Party party;
    private boolean valid;

    private ScheduledTask task;

    public Invite(ProxiedPlayer player, Party party, int min) {
        this.player = player;
        this.party = party;
        valid = true;
        startInvite();
    }
    private void acceptInvite() {
        if (valid) {
            task.cancel();
            party.removeInvite(this);
            party.acceptInvite(this);
            player.sendMessage(new TextComponent("Du hast die Party Anfrage angenommen"));
        }
    }

    private void denyInvite() {
        if (valid) {
            task.cancel();
            party.removeInvite(this);
            player.sendMessage(new TextComponent("Du hast die Party Anfrage abgelehnt"));
        }
    }

    private void startInvite() {
        player.sendMessage(new TextComponent("Du wurdest von " + party.getLeader().getName() + " in eine Party eingeladen"));

        TextComponent accept = new TextComponent("§l§a[Annehmen]");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + party.getLeader().getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Nehme die Anfrage an").create()));

        TextComponent deny = new TextComponent("§l§4[Ablehnen]");
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + party.getLeader().getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Lehne die Anfrage ab").create()));

        TextComponent full = new TextComponent();
        full.addExtra(accept);
        full.addExtra(new TextComponent(" §r§7oder "));
        full.addExtra(deny);
        player.sendMessage(full);
        task = ProxyServer.getInstance().getScheduler().schedule(PartyMain.getInstance(), () -> {
            party.removeInvite(this);
            valid = false;
        }, 5, TimeUnit.MINUTES);
    }


    public ProxiedPlayer getPlayer() {
        return player;
    }
}
