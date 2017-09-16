package pl.edu.tirex.guilds.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.edu.tirex.guilds.Guild;
import pl.edu.tirex.guilds.engine.GuildEngine;
import pl.edu.tirex.guilds.storage.GuildStorage;

public class EnterGuildTerritoryListener implements Listener
{
    private final GuildEngine engine;
    private final GuildStorage guildStorage;

    public EnterGuildTerritoryListener(GuildEngine engine, GuildStorage guildStorage)
    {
        this.engine = engine;
        this.guildStorage = guildStorage;
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event)
    {
        this.engine.changeTerritoryDetection(event.getPlayer(), event.getTo());
    }

    @EventHandler(ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event)
    {
        Guild guild = this.guildStorage.get(event.getTo());
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND && !this.engine.hasPermission(event.getPlayer(), guild))
        {
            event.setCancelled(true);
            return;
        }
        if (guild == null)
        {
            return;
        }
        this.engine.changeTerritoryDetection(event.getPlayer(), guild);
    }
}