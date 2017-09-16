package pl.edu.tirex.guilds.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.edu.tirex.guilds.User;
import pl.edu.tirex.guilds.engine.GuildEngine;
import pl.edu.tirex.guilds.storage.UserStorage;

public class PlayerJoinQuitListener implements Listener
{
    private final GuildEngine engine;
    private final UserStorage userStorage;

    public PlayerJoinQuitListener(GuildEngine engine, UserStorage userStorage)
    {
        this.engine = engine;
        this.userStorage = userStorage;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        User user = this.userStorage.getUser(player.getUniqueId());
        if (user == null)
        {
            user = new User(player.getUniqueId(), player.getName());
            this.userStorage.add(user);
        }
        this.engine.changeTerritoryDetection(player, player.getLocation());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        this.userStorage.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onKick(PlayerKickEvent event)
    {
        this.userStorage.remove(event.getPlayer().getUniqueId());
    }
}
