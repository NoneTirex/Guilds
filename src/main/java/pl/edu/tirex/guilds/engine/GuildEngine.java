package pl.edu.tirex.guilds.engine;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.edu.tirex.guilds.Guild;
import pl.edu.tirex.guilds.User;
import pl.edu.tirex.guilds.storage.GuildStorage;
import pl.edu.tirex.guilds.storage.UserStorage;

public class GuildEngine
{
    private final UserStorage userStorage;
    private final GuildStorage guildStorage;

    public GuildEngine(UserStorage userStorage, GuildStorage guildStorage)
    {
        this.userStorage = userStorage;
        this.guildStorage = guildStorage;
    }

    public Guild changeTerritoryDetection(Player player, Location to)
    {
        User user = this.userStorage.getUser(player.getUniqueId());
        if (user == null)
        {
            return null;
        }
        Location from = user.getLastLocation();
        if (from != null && from.getWorld().equals(to.getWorld()) && from.distance(to) < 0.8D)
        {
            return null;
        }
        if (from == null)
        {
            from = to;
        }
        user.setLastLocation(from);

        Guild guild = this.guildStorage.get(to);
        return this.changeTerritoryDetection(player, guild);
    }

    public Guild changeTerritoryDetection(Player player, Guild guild)
    {
        User user = this.userStorage.getUser(player.getUniqueId());
        if (user == null)
        {
            return guild;
        }
        Guild lastGuild = user.getLastLocation() != null ? this.guildStorage.get(user.getLastLocation()) : null;
        if (guild != null && lastGuild != null && guild.equals(lastGuild))
        {
            return guild;
        }
        if (lastGuild != null)
        {
            player.sendMessage("opuszczono teren gildii " + lastGuild.getTag());
        }
        if (guild != null)
        {
            player.sendMessage("wszedles na teren gildii " + guild.getTag());
        }
        return guild;
    }

    public boolean hasPermission(Player player, Guild guild)
    {
        if (guild == null)
        {
            return true;
        }
        //TODO check permissions
        return false;
    }

    public boolean canInteract(Player player, Block block)
    {
        User user = this.userStorage.getUser(player.getUniqueId());
        if (user == null)
        {
            return true;
        }
        Guild guild = this.guildStorage.get(block.getLocation());
        return this.hasPermission(player, guild);
    }
}
