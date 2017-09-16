package pl.edu.tirex.guilds;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import pl.edu.tirex.guilds.database.Database;
import pl.edu.tirex.guilds.engine.GuildEngine;
import pl.edu.tirex.guilds.listeners.BlockInteractListener;
import pl.edu.tirex.guilds.listeners.EnterGuildTerritoryListener;
import pl.edu.tirex.guilds.listeners.PlayerJoinQuitListener;
import pl.edu.tirex.guilds.repository.GuildRepository;
import pl.edu.tirex.guilds.storage.GuildStorage;
import pl.edu.tirex.guilds.storage.UserStorage;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class BukkitGuild extends JavaPlugin
{
    private static final boolean PRODUCTION = false;

    private Database database;
    private GuildEngine engine;
    private GuildRepository guildRepository;
    private GuildStorage guildStorage;
    private UserStorage userStorage;

    @Override
    public void onEnable()
    {
        this.getLogger().info("YEAH!");

        this.database = new Database("jdbc:mysql://localhost:3306/tirex?useSSL=true");
        try
        {
            this.database.connect("root", "samoloty2");

            if (!PRODUCTION)
            {
                this.database.update("DROP TABLE IF EXISTS `guilds`");
            }

            this.guildRepository = new GuildRepository(this.database);
            this.guildRepository.createTable();

            if (!PRODUCTION)
            {
                Guild guild = new Guild(UUID.randomUUID(), "HELLO", "Hello Kitty");
                guild.setVector(new Vector(-463, 2, 462));
                guild.setSize(50);
                guild.setWorld("world");
                this.guildRepository.save(guild);

//                guild = new Guild(UUID.randomUUID(), "SNAP", "1");
//                guild.setVector(new Vector(100, 200, 430));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP2", "11");
//                guild.setVector(new Vector(100, 200, 440));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP3", "12");
//                guild.setVector(new Vector(100, 200, 450));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP4", "13");
//                guild.setVector(new Vector(100, 200, 460));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP5", "14");
//                guild.setVector(new Vector(100, 200, 461));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP6", "15");
//                guild.setVector(new Vector(100, 200, 462));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP61", "151");
//                guild.setVector(new Vector(100, 200, 463));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP62", "152");
//                guild.setVector(new Vector(100, 200, 464));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP7", "16");
//                guild.setVector(new Vector(100, 200, 465));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP8", "17");
//                guild.setVector(new Vector(100, 200, 466));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "SNAP9", "18");
//                guild.setVector(new Vector(100, 200, 469));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "2", "2");
//                guild.setVector(new Vector(100, 200, 470));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "3", "3");
//                guild.setVector(new Vector(100, 200, 474));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "4", "4");
//                guild.setVector(new Vector(100, 200, 475));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);
//
//                guild = new Guild(UUID.randomUUID(), "5", "5");
//                guild.setVector(new Vector(100, 200, 476));
//                guild.setWorld("world");
//                this.guildRepository.save(guild);

                try
                {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e)
        {
            this.getLogger().log(Level.WARNING, "Error while connecting to database!", e);
            return;
        }

        this.guildStorage = new GuildStorage(this.guildRepository);
        this.guildStorage.load();

        this.userStorage = new UserStorage();

        this.engine = new GuildEngine(this.userStorage, this.guildStorage);

        this.getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this.engine, this.userStorage), this);
        this.getServer().getPluginManager().registerEvents(new EnterGuildTerritoryListener(this.engine, this.guildStorage), this);
        this.getServer().getPluginManager().registerEvents(new BlockInteractListener(this.engine), this);

        this.getCommand("guild").setExecutor(new GuildCommand(this.userStorage, guildStorage));

        System.out.println(this.guildStorage.getByTag("hello"));
        System.out.println(this.guildStorage.getByTag("SnAp"));

        System.out.println(this.guildStorage.getGuilds());
        System.out.println(this.guildStorage.getGuilds().size());
    }
}
