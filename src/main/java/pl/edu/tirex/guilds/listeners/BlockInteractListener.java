package pl.edu.tirex.guilds.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.edu.tirex.guilds.engine.GuildEngine;

public class BlockInteractListener implements Listener
{
    private final GuildEngine engine;

    public BlockInteractListener(GuildEngine engine)
    {
        this.engine = engine;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if (!this.engine.canInteract(player, event.getBlock()))
        {
            player.sendMessage("Nie mozesz niszczyc na terenie tej gildii");
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        if (!this.engine.canInteract(player, event.getBlock()))
        {
            player.sendMessage("Nie mozesz budowac na terenie tej gildii");
            event.setCancelled(true);
            event.setBuild(false);
        }
    }
}
