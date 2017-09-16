package pl.edu.tirex.guilds.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Database
{
    private final String url;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private Connection connection;

    public Database(String url)
    {
        this.url = url;
    }

    public void connect(String user, String password) throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            this.connection = DriverManager.getConnection(this.url, user, password);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isConnected()
    {
        try
        {
            return this.connection != null && !this.connection.isClosed();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void select(String sql, SQLConsumer<ResultSet> consumer, Object... parameters)
    {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql))
        {
            this.setParameters(preparedStatement, parameters);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                consumer.accept(resultSet);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateAsync(String sql, Object... parameters)
    {
        this.executor.execute(() ->
        {
            try
            {
                this.update(sql, parameters);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        });
    }

    public int update(String sql, Object... parameters) throws SQLException
    {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql))
        {
            this.setParameters(preparedStatement, parameters);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException
    {
        for (int i = 0; i < parameters.length; i++)
        {
            Object parameter = parameters[i];
            if (parameter instanceof UUID)
            {
                preparedStatement.setString(i + 1, parameter.toString());
            }
            else
            {
                preparedStatement.setObject(i + 1, parameter);
            }
        }
    }
}
