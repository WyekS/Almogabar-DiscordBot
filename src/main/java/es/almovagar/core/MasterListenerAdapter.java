package es.almovagar.core;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.slf4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Wolf Master.
 * It contains the main flow bot
 */
public class MasterListenerAdapter extends ListenerAdapter
{
    private static final Logger LOG = JDALogger.getLog(MasterListenerAdapter.class);

    /**
     * Instantiates a new W master.
     */
    public MasterListenerAdapter()
    {
        // TODO
    }

    public void onMessageReceived(final MessageReceivedEvent event)
    {
        final Message messageEvent = event.getMessage();
        LOG.info(messageEvent.getContentRaw());
        executeProcess(event);
    }

    private void executeProcess(final MessageReceivedEvent event)
    {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() ->
        {
            processMessage(event);
            LOG.info("End thread " + Thread.currentThread().getName());
        });
        executor.shutdown();
    }

    private void processMessage(final MessageReceivedEvent event)
    {
        LOG.info("Filter passed. Generating an action...");
        final TextChannel senderChannel = event.getTextChannel();
        LOG.info(event.getMessage().getContentRaw());
        if (event.getMessage().getContentRaw().contains("almogavar"))
        {
            senderChannel.sendMessage("Abajo la monarquía!!!").queue();
        }
    }

}
