package es.almovagar;

import es.almovagar.core.Config;
import es.almovagar.core.MasterListenerAdapter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;

import static es.almovagar.core.Config.loadProperties;
import static es.almovagar.utils.BuilderUtils.buildInk;

/**
 * The type es.almovagar.Bot.
 *
 * @author <a href="mailto:wyeks@live.com">wyeks</a> Discord es.almovagar.Bot Utils for Wolf Team channel administration
 */
public class Bot
{
    private static final Logger LOG = JDALogger.getLog(Bot.class);
    private static final String MOCKED = "app.mocked";
    private static final String TOKEN = "app.token";
    public static boolean botMocked;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws LoginException the login exception
     */
    public static void main(String[] args) throws LoginException
    {
        // Start Props
        loadProperties();
        botMocked = Boolean.parseBoolean(Config.getParameter(MOCKED));
        LOG.info("Start Almogavar es.almovagar.Bot. es.almovagar.Bot Mocked: " + botMocked);
        LOG.info(buildInk());

        // es.almovagar.Bot Application

        // JDA Functions
//        final JDABuilder builder = JDABuilder.create(Config.getParameter(TOKEN), GUILD_MEMBERS);
        final JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Config.getParameter(TOKEN));
        builder.addEventListeners(new MasterListenerAdapter());
        builder.build();
    }
}