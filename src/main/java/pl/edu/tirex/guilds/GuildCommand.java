package pl.edu.tirex.guilds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.edu.tirex.guilds.storage.GuildStorage;
import pl.edu.tirex.guilds.storage.UserStorage;

public class GuildCommand implements CommandExecutor
{
    private final UserStorage userStorage;
    private final GuildStorage guildStorage;

    public GuildCommand(UserStorage userStorage, GuildStorage guildStorage)
    {
        this.userStorage = userStorage;
        this.guildStorage = guildStorage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length < 1)
        {
            sender.sendMessage("Poprawne uzycie: /g <create>");
            return true;
        }
        if (args[0].equalsIgnoreCase("create"))
        {
            if (args.length < 3)
            {
                sender.sendMessage("Poprawne uzycie: /" + label + " create <tag> <nazwa>");
                return true;
            }
            // TODO only for players
            Player player = (Player) sender;
            User user = this.userStorage.getUser(player.getUniqueId());
            Guild guild = user.getGuild();
            if (guild != null)
            {
                sender.sendMessage("Nie mozesz uzyc tej komendy bedac w gildii!");
                return true;
            }
            if (this.guildStorage.isNearbyArea(player.getLocation().toVector(), 200))
            {
                sender.sendMessage("Znajdujesz sie w poblizu gildii");
                return true;
            }
        }
        return true;
    }
}
