package pl.edu.tirex.guilds.repository;

import org.bukkit.util.Vector;
import pl.edu.tirex.guilds.database.Database;
import pl.edu.tirex.guilds.Guild;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class GuildRepository
{
    private final Database database;

    public GuildRepository(Database database)
    {
        this.database = database;
    }

    public void createTable() throws SQLException
    {
        this.database.update("CREATE TABLE IF NOT EXISTS `guilds` (`uuid` VARCHAR (36) NOT NULL, `tag` VARCHAR (6) NOT NULL UNIQUE, `name` VARCHAR (32) NOT NULL UNIQUE, `x` DECIMAL(11, 2), `y` DECIMAL(11, 2), `z` DECIMAL(11, 2), `world` VARCHAR (32) NOT NULL, `size` INT, PRIMARY KEY (`uuid`))");
    }

    public void save(Guild guild)
    {
        Vector vector = guild.getVector();
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();
        this.database.updateAsync("INSERT INTO `guilds` (`uuid`, `tag`, `name`, `x`, `y`, `z`, `world`, `size`) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `uuid`=?,`tag`=?,`name`=?,`x`=?,`y`=?,`z`=?,`world`=?, `size`=?", guild.getUniqueId(), guild.getTag(), guild.getName(), x, y, z, guild.getWorld(), guild.getSize(), guild.getUniqueId(), guild.getTag(), guild.getName(), x, y, z, guild.getWorld(), guild.getSize());
    }

    public Collection<Guild> loadGuilds()
    {
        Collection<Guild> guilds = new ArrayList<>();
        this.database.select("SELECT * FROM `guilds`", resultSet ->
        {
            Guild guild = new Guild(UUID.fromString(resultSet.getString("uuid")), resultSet.getString("tag"), resultSet.getString("name"));
            guild.setVector(new Vector(resultSet.getDouble("x"), resultSet.getDouble("y"), resultSet.getDouble("z")));
            guild.setWorld(resultSet.getString("world"));
            guild.setSize(resultSet.getInt("size"));
            guilds.add(guild);
        });
        return guilds;
    }
}
