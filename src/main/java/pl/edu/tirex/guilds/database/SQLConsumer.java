package pl.edu.tirex.guilds.database;

import java.sql.SQLException;
import java.util.function.Consumer;

@FunctionalInterface
public interface SQLConsumer<T>
{
    void accept(T t) throws SQLException;
}
