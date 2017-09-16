package pl.edu.tirex.guilds;

import org.bukkit.Location;

import java.util.UUID;

public class User
{
    private final UUID uniqueId;
    private String name;
    private Guild guild;

    private Location lastLocation;

    public User(UUID uniqueId, String name)
    {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public UUID getUniqueId()
    {
        return uniqueId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Guild getGuild()
    {
        return guild;
    }

    public void setGuild(Guild guild)
    {
        this.guild = guild;
    }

    public Location getLastLocation()
    {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation)
    {
        this.lastLocation = lastLocation;
    }
}
