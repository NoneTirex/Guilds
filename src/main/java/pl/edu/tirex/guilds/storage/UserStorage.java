package pl.edu.tirex.guilds.storage;

import pl.edu.tirex.guilds.User;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserStorage
{
    private final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private final Map<UUID, User> users = new HashMap<>();

    public void add(User user)
    {
        this.userThreadLocal.set(user);
        this.users.put(user.getUniqueId(), user);
    }

    public User getUser(UUID uniqueId)
    {
        User user = this.userThreadLocal.get();
        if (user != null && user.getUniqueId().equals(uniqueId))
        {
            return user;
        }
        user = this.users.get(uniqueId);
        if (user != null)
        {
            this.userThreadLocal.set(user);
        }
        return user;
    }

    public void remove(User user)
    {
        this.remove(user.getUniqueId());
    }

    public void remove(UUID uniqueId)
    {
        User user = this.userThreadLocal.get();
        if (user != null && user.getUniqueId().equals(uniqueId))
        {
            this.userThreadLocal.remove();
        }
        this.users.remove(uniqueId);
    }
}
