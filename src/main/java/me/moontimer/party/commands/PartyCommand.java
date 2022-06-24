package me.moontimer.party.commands;

import me.moontimer.party.PartyMain;
import me.moontimer.party.manager.Party;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Locale;

public class PartyCommand extends Command {
    public PartyCommand(String name) {
        super("party");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        //party / p help
        //party invite name
        //party create
        //party leave
        ProxiedPlayer player = (ProxiedPlayer) sender;

        switch (args.length) {

            case 1:
                switch (args[0].toLowerCase(Locale.ROOT)) {
                    case "create":
                        PartyMain.getInstance().getPartyManager().createParty(player);
                        player.sendMessage(new TextComponent(PartyMain.PREFIX + "Es wurde eine Party für dich erstellt"));
                        break;
                    case "leave":
                        PartyMain.getInstance().getPartyManager().getParty(player).leaveFromParty(player);
                        break;
                    case "invite":
                        player.sendMessage(new TextComponent(PartyMain.PREFIX + "Du musst einen Namen angeben"));
                }
                break;
            case 2:
                String argument = args[0];
                String argPlayerString = args[1];
                ProxiedPlayer argPlayer = ProxyServer.getInstance().getPlayer(argPlayerString);
                switch (argument.toLowerCase(Locale.ROOT)) {
                    case "invite":
                        PartyMain.getInstance().getPartyManager().invite(player, argPlayer);
                        break;
                    case "kick":
                        PartyMain.getInstance().getPartyManager().getParty(player).leaveFromParty(argPlayer);
                        break;
                    case "accept":
                        PartyMain.getInstance().getPartyManager().acceptInvite(player, argPlayer);
                        break;
                    case "deny":
                        PartyMain.getInstance().getPartyManager().denyInvite(player, argPlayer);
                        break;
                }
                break;

            default:
                player.sendMessage(new TextComponent(PartyMain.PREFIX + "/party create - " +
                        "§7Erstellt eine Party"));
                player.sendMessage(new TextComponent(PartyMain.PREFIX + "/party leave - " +
                        "§7Verlässt die Party"));
                player.sendMessage(new TextComponent(PartyMain.PREFIX + "/party invite [name] - " +
                        "§7Lädt ein Spieler zur Party ein"));
                player.sendMessage(new TextComponent(PartyMain.PREFIX + "/party kick [name] - " +
                        "§7Kickt den gewünschten Spieler aus der Party"));
                player.sendMessage(new TextComponent(PartyMain.PREFIX + "/party accept [name] - " +
                        "§7Nimmt die Party anfrage an"));
                player.sendMessage(new TextComponent(PartyMain.PREFIX + "/party deny [name] - " +
                        "§7Lehnt die Party Anfrage des Spielers ab"));
                break;

        }
    }
}
