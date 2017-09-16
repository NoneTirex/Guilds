package pl.edu.tirex.guilds.storage;

import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.bukkit.util.Vector;
import pl.edu.tirex.guilds.Guild;
import pl.edu.tirex.guilds.GuildsContainer;
import pl.edu.tirex.guilds.repository.GuildRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuildStorage
{
    private final GuildRepository guildRepository;
    private final Long2ObjectMap<GuildsContainer> guilds = new Long2ObjectOpenHashMap<>();
    private final Map<String, Guild> guildByTag = new HashMap<>();

    public GuildStorage(GuildRepository guildRepository)
    {
        this.guildRepository = guildRepository;
    }

    public void load()
    {
        for (Guild guild : this.guildRepository.loadGuilds())
        {
            this.add(guild);
        }
    }

    public void add(Guild guild)
    {
        int size = guild.getSize();
        int containersCount = ((size - 1) / GuildsContainer.CONTAINER_SIZE) + 1;
        int guildX = guild.getVector().getBlockX();
        int guildZ = guild.getVector().getBlockZ();
        for (int offsetX = -containersCount; offsetX <= containersCount; offsetX++)
        {
            for (int offsetZ = -containersCount; offsetZ <= containersCount; offsetZ++)
            {
                GuildsContainer guildsContainer = this.getContainer(guildX + (offsetX * size), guildZ + (offsetZ * size));
                if (guildsContainer == null)
                {
                    guildsContainer = new GuildsContainer(this.calc(guildX + (offsetX * size)), this.calc(guildZ + (offsetZ * size)));
                    this.putContainer(guildX + (offsetX * size), guildZ + (offsetZ * size), guildsContainer);
                }
                guildsContainer.add(guild);
            }
        }
        this.guildByTag.put(guild.getTag().toLowerCase(), guild);
    }

    private void putContainer(int x, int z, GuildsContainer guildsContainer)
    {
        this.guilds.put(this.containerHash(x, z), guildsContainer);
    }

    public Guild getByTag(String tag)
    {
        return this.guildByTag.get(tag.toLowerCase());
    }

    public Guild get(Location location)
    {
        return this.get(location.toVector());
    }

    public Guild get(Vector vector)
    {
        GuildsContainer container = this.getContainer(vector.getBlockX(), vector.getBlockZ());
        if (container == null)
        {
            return null;
        }
        for (Guild guild : container.getGuilds())
        {
            Vector guildVector = guild.getVector();
            double x = Math.sqrt(Math.pow(vector.getBlockX() - guildVector.getBlockX(), 2));
            double z = Math.sqrt(Math.pow(vector.getBlockZ() - guildVector.getBlockZ(), 2));
            if (x <= guild.getSize() && z <= guild.getSize())
            {
                return guild;
            }
        }
        return null;
    }

    private GuildsContainer getContainer(int x, int z)
    {
        return this.guilds.get(this.containerHash(x, z));
    }

    private int containerHash(int x, int z)
    {
        int containerX = calc(x);
        int containerZ = calc(z);
        return Objects.hash(containerX, containerZ);
    }

    private int calc(int number)
    {
        return Math.floorDiv(number, GuildsContainer.CONTAINER_SIZE);
    }

    public boolean isNearbyArea(Vector center, int distance)
    {
        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();
        int containersCount = ((distance - 1) / GuildsContainer.CONTAINER_SIZE) + 1;
        GuildsContainer lastContainer = null;
        for (int offsetX = -containersCount; offsetX <= containersCount; offsetX++)
        {
            for (int offsetZ = -containersCount; offsetZ <= containersCount; offsetZ++)
            {
                GuildsContainer guildsContainer = this.getContainer(centerX + (offsetX * distance), centerZ + (offsetZ * distance));
                if (guildsContainer == null || guildsContainer.equals(lastContainer))
                {
                    continue;
                }
                System.out.println(guildsContainer);
                lastContainer = guildsContainer;
                for (Guild guild : guildsContainer.getGuilds())
                {
                    Vector guildVector = guild.getVector();
                    double x = Math.sqrt(Math.pow(centerX - guildVector.getBlockX(), 2));
                    double z = Math.sqrt(Math.pow(centerZ - guildVector.getBlockZ(), 2));
                    if (x <= distance && z <= distance)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Long2ObjectMap<GuildsContainer> getGuilds()
    {
        return guilds;
    }
}
