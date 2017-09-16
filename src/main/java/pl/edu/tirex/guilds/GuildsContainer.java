package pl.edu.tirex.guilds;

import java.util.Collection;
import java.util.HashSet;

public class GuildsContainer
{
    public static final int CONTAINER_SIZE = 512;

    private final int x;
    private final int z;
    private final Collection<Guild> guilds = new HashSet<>();

    public GuildsContainer(int x, int z)
    {
        this.x = x;
        this.z = z;
    }

    public boolean add(Guild guild)
    {
        return this.guilds.add(guild);
    }

    public boolean remove(Guild guild)
    {
        return this.guilds.remove(guild);
    }

    public boolean contains(Guild guild)
    {
        return this.guilds.contains(guild);
    }

    public Collection<Guild> getGuilds()
    {
        return guilds;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("GuildsContainer{");
        sb.append("x=").append(x);
        sb.append(", z=").append(z);
        sb.append('}');
        return sb.toString();
    }
}
