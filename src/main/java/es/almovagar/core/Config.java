package es.almovagar.core;

import es.almovagar.Bot;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config
{
    private static final Logger LOG = JDALogger.getLog(Config.class);
    private static Properties properties = null;

    public static void loadProperties()
    {
        try (final InputStream input = Bot.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            properties = new Properties();
            properties.load(input);
        }
        catch (final IOException ex)
        {
            LOG.error("Error when properties was loading");
        }
    }

    public static String getParameter(final String property)
    {
        return properties.getProperty(property);
    }
}
