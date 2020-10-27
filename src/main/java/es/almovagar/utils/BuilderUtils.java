package es.almovagar.utils;

import es.almovagar.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class BuilderUtils
{
    private static final Logger LOG = JDALogger.getLog(BuilderUtils.class);

    /**
     * Returns a message card to publish a specialization event
     * <p/>
     *
     * @param specialization name
     * @param placesStr      Number places for this event {@link String}
     * @return {@link MessageEmbed} a builded card for chat bot
     */
    public static MessageEmbed buildCardPlacesMessage(final String specialization, final String placesStr)
    {
        Validate.notNull(specialization, "Parameter specialization cannot be null");
        Validate.notNull(placesStr, "Parameter places cannot be null");

        final int places = Integer.parseInt(placesStr);
        final String title = "Nuevas vacantes para ".concat("Name");
        final StringBuilder description = new StringBuilder();
        description.append("<@&536857111368433675> Se necesita personal para la especialización de ")
                .append("NAme")
                .append(". \n\nLos interesados deberán rellenar este formulario: \n")
                .append("<url>")
                .append("\n\nEl instructor se pondrá en contacto con los seleccionados. *(Rango mínimo de Soldado)*");

        HashMap<String, String> fieldsValues = new HashMap<>();
        fieldsValues.put("Plazas disponibles", String.valueOf(places));

        return createMessage(title,
                description.toString(), Color.red, fieldsValues, "", true);
    }

    /**
     * Build a message status of all servers with values parameters info
     * <p/>
     *
     * @param server  {@link String}
     * @param status  {@link String}
     * @param name    {@link String}
     * @param players {@link String}
     * @return {@link MessageEmbed} a message status of all servers
     */
    public static MessageEmbed buildStatusServerMessage(final String server, final String status,
                                                        final String name, final String players)
    {
        final boolean active = status.startsWith("true");
        final HashMap<String, String> fieldsValues = new HashMap<>();
        fieldsValues.put("Nombre", name);
        fieldsValues.put("Estado", active ? "Activo" : "Inactivo");
        fieldsValues.put("Jugadores activos", players);

        final Color color = active ? Color.green : Color.red;

        return createMessageStatus("Estado del servidor " + server, color, fieldsValues, null);
    }

    /**
     * Read and return file <a href="file:../resources/help_message.md">/resources/help_message.md</a>
     * <p/>
     *
     * @return {@link String} a help message to discord
     */
    public static String buildHelpingsMessage()
    {
        String result = "";
        /*ClassLoader classLoader = es.almovagar.Bot.class.getClassLoader();
        try
        {
            result = IOUtils.toString(
                    Objects.requireNonNull(classLoader.getResourceAsStream(HELP_MESSAGE_FILE)), StandardCharsets.UTF_8);
        }
        catch (final IOException ioe)
        {
            LOG.error("Error I/O when the file help_message.md was being read");
        }*/

        return result;
    }

    public static String buildInk()
    {
        String result = "";
        ClassLoader classLoader = Bot.class.getClassLoader();
        try
        {
            result = IOUtils.toString(
                    Objects.requireNonNull(classLoader.getResourceAsStream("ink")), StandardCharsets.UTF_8);
        }
        catch (final IOException ioe)
        {
            // LOG.error("Error I/O when the file help_message.md was being read");
        }

        return result;
    }

    public static MessageEmbed buildErrorPermissionMessage()
    {
        return createMessage("Error", "No tienes permisos para ejecutar este comando", null, null, null, false);
    }

    public static MessageEmbed buildErrorCommandNotFoundMessage()
    {
        return createMessage("Error", "Ese comando no existe", null, null, null, false);
    }

    public static MessageEmbed buildUpdateInProcessMessage()
    {
        return createMessage("Update Arma 3", "La actualización está en proceso...", Color.orange, null, null, false);
    }

    public static MessageEmbed buildUpdateErrorMessage()
    {
        return createMessage("Error", "No ningún LOG sobre la actualización", null, null, null, false);
    }

    public static MessageEmbed buildUpdateFinishMessage()
    {
        return createMessage("Update Arma 3", "La actualización ha finalizado", Color.green, null, null, false);
    }


    public static MessageEmbed buildStartSucessMessage(final String alias)
    {
        return createMessage("Mapa " + alias, "Se está iniciado el mapa " + alias + ", tardará unos minutos", Color.green, null, null, false);
    }

    public static MessageEmbed buildStartErrorMessage(final String alias)
    {
        return createMessage("Mapa " + alias, "Hay ocurrido un error al iniciar " + alias, Color.red, null, null, false);
    }

    public static MessageEmbed buildStopSucessMessage()
    {
        return createMessage("Stop Arma 3", "Servidor/es parado/s con éxito", Color.green, null, null, false);
    }

    public static MessageEmbed buildStopErrorMessage()
    {
        return createMessage("Stop Arma 3", "Hay ocurrido un error al parar los servidores ", Color.red, null, null, false);
    }

    public static MessageEmbed buildOperationEventMessage()
    {
        return createMessage("Operación", "Se solitita intervención de las fuerzas de @Wolfteam a las 22.00h",
                Color.green, null, null, false);
    }

    /**
     * Create a Message object from API Discord
     * <p/>
     *
     * @param title       {@link String}
     * @param description {@link String}
     * @param color       {@link Color}
     * @param fieldsValue {@link HashMap}
     * @param image       {@link String}
     * @return {@link MessageEmbed}
     */
    public static MessageEmbed createMessage(final String title, final String description, final Color color,
                                             final HashMap<String, String> fieldsValue, final String image, final boolean enableFooter)
    {
        // Create the EmbedBuilder instance
        final EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(title, null);

        eb.setColor(null == color ? Color.red : color);

        if (null != description)
        {
            eb.setDescription(description);
        }

        if (MapUtils.isNotEmpty(fieldsValue))
        {
            for (final Map.Entry<String, String> entry : fieldsValue.entrySet())
            {
                eb.addField(entry.getKey(), entry.getValue(), true);
            }
            eb.addBlankField(true); // Separation before image
        }

        if (enableFooter)
        {
            eb.addBlankField(true); // Does the card more width
            eb.setAuthor("", null, null);
            eb.setFooter("__por Wolf Team__", "https://media.discordapp.net/attachments/564454752705052683/571084650202791938/wolf_logo_sm.png");
            eb.setTimestamp(Instant.now());
        }

        if (null != image)
        {
            eb.setImage(image);
        }

        //eb.setThumbnail("https://media.discordapp.net/attachments/564454752705052683/571084650202791938/wolf_logo_sm.png");

        return eb.build();
    }

    /**
     * TODO: Refactor
     *
     * @param title
     * @param color
     * @param fieldsValue
     * @param image
     * @return
     */
    public static MessageEmbed createMessageStatus(final String title, final Color color,
                                                   final HashMap<String, String> fieldsValue, final String image)
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title, null);
        eb.setColor(color);

        if (MapUtils.isNotEmpty(fieldsValue))
        {
            for (final Map.Entry<String, String> entry : fieldsValue.entrySet())
            {
                if (entry.getKey().equals("Nombre"))
                {
                    eb.addField(entry.getKey(), entry.getValue(), false);
                    eb.addBlankField(false);
                }
                else
                {
                    eb.addField(entry.getKey(), entry.getValue(), true);
                }
            }
        }
        // eb.addBlankField(true); // Does the card more width
        // eb.setTimestamp(Instant.now());

        if (null != image)
        {
            eb.setImage(image);
        }

        eb.setThumbnail("https://media.discordapp.net/attachments/564454752705052683/571084650202791938/wolf_logo_sm.png");

        return eb.build();
    }

    private MessageEmbed generate(double tps)
    {
        EmbedBuilder eb = new EmbedBuilder();
        double lagPercentage = Math.round((1.0D - tps / 20.0D) * 100.0D);
        eb.addField("TPS:", "`" + new DecimalFormat("#.####").format(tps) + "`", true);
        eb.addField("Lag Percentage: ", lagPercentage + "%", true);
        eb.addField("Free RAM: ", Runtime.getRuntime().freeMemory() / 1024L / 1024L + "mb", true);
        eb.addField("Total Memory: ", Runtime.getRuntime().totalMemory() / 1024L / 1024L + "mb", true);
        eb.addField("Allocated Memory: ", Runtime.getRuntime().totalMemory() / 1024L / 1024L + "mb", true);
        eb.addBlankField(true);
        return eb.build();
    }

}
