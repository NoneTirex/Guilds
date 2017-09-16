package pl.edu.tirex.guilds;

import org.bukkit.util.Vector;

import java.util.UUID;

public class Guild
{
    private static final int DEFAULT_SIZE = 50;

    private final UUID uniqueId;
    private String tag;
    private String name;
    private int size = DEFAULT_SIZE;
    private Vector vector = new Vector();
    private String world;

    public Guild(UUID uniqueId, String tag, String name)
    {
        this.uniqueId = uniqueId;
        this.tag = tag;
        this.name = name;
    }

    public UUID getUniqueId()
    {
        return uniqueId;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public Vector getVector()
    {
        return vector;
    }

    public void setVector(Vector vector)
    {
        this.vector = vector;
    }

    public String getWorld()
    {
        return world;
    }

    public void setWorld(String world)
    {
        this.world = world;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Guild{");
        sb.append("tag='").append(tag).append('\'');
        sb.append(", vector=").append(vector);
        sb.append('}');
        return sb.toString();
    }
}
