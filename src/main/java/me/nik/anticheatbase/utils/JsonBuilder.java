package me.nik.anticheatbase.utils;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.nik.anticheatbase.wrappers.WrapperPlayServerChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class JsonBuilder {

    // Main message text
    private static String text;
    // Basic JSON message, without any events
    private static String json = "{\"text\":\"" + text + "\"}";
    // Value of the hover events
    private String hover;
    // Value of the click events
    private String click;
    /**
     * Hover event type
     *
     * @see HoverEventType
     */
    private HoverEventType hoverAction;
    /**
     * Click event type
     *
     * @see ClickEventType
     */
    private ClickEventType clickAction;

    /**
     * @param text Main message text
     */
    public JsonBuilder(String text) {
        JsonBuilder.text = ChatUtils.format(text);
    }

    /**
     * @param type  Type of the event (show text, show achievement, etc.)
     * @param value Value of the text inside it
     */
    public JsonBuilder setHoverEvent(JsonBuilder.HoverEventType type, String value) {
        hover = ChatUtils.format(value);
        hoverAction = type;
        return this;
    }

    /**
     * @param type  Type of the event (suggest command, open URL, etc.)
     * @param value Value of the event
     */
    public JsonBuilder setClickEvent(JsonBuilder.ClickEventType type, String value) {
        click = value;
        clickAction = type;
        return this;
    }

    public JsonBuilder buildText() {

        if (!getClick().isPresent() && !getHover().isPresent()) json = "{\"text\":\"" + text + "\"}";

        if (!getClick().isPresent() && getHover().isPresent()) {

            if (hoverAction == HoverEventType.SHOW_ACHIEVEMENT) {

                json = "{\"text\":\"" + text + "\",\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"achievement." + hover + "\"}}";

            } else if (hoverAction == HoverEventType.SHOW_STATISTIC) {

                json = "{\"text\":\"" + text + "\",\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"stat." + hover + "\"}}";

            } else {

                json = "{\"text\":\"" + text + "\",\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"" + hover + "\"}}";
            }
        }

        if (getClick().isPresent() && getHover().isPresent()) {

            json = "{\"text\":\"" + text + "\",\"clickEvent\":{\"action\":\"" + clickAction.getActionName() + "\",\"value\":\"" + click + "\"},\"hoverEvent\":{\"action\":\"" + hoverAction.getActionName() + "\",\"value\":\"" + hover + "\"}}";
        }

        if (getClick().isPresent() && !getHover().isPresent()) {

            json = "{\"text\":\"" + text + "\",\"clickEvent\":{\"action\":\"" + clickAction.getActionName() + "\",\"value\":\"" + click + "\"}}";
        }

        return this;
    }

    /**
     * @param player Send the player the modified text in the builder
     */
    public void sendMessage(Player player) {

        WrapperPlayServerChat chat = new WrapperPlayServerChat();

        chat.setChatType(EnumWrappers.ChatType.CHAT);

        chat.setMessage(WrappedChatComponent.fromJson(json));

        chat.sendPacket(player);
    }

    /**
     * @param players Send the players the modified text in the builder
     */
    public void sendMessage(Collection<? extends UUID> players) {

        WrapperPlayServerChat chat = new WrapperPlayServerChat();

        chat.setChatType(EnumWrappers.ChatType.CHAT);

        chat.setMessage(WrappedChatComponent.fromJson(json));

        for (UUID uuid : players) {

            Player p = Bukkit.getPlayer(uuid);

            if (p == null) continue;

            chat.sendPacket(p);
        }
    }

    /**
     * @return The original message text, without any formatting
     */
    public String getUnformattedText() {
        return text;
    }

    /**
     * @return The JSON syntax, after all the modifying.
     */
    public String getJson() {
        return json;
    }

    /**
     * @return Optional of the hover value (to simplify null checks)
     */
    private Optional<String> getHover() {
        return Optional.of(hover);
    }

    /**
     * @return Optional of the click value (to simplify null checks)
     */
    private Optional<String> getClick() {
        return Optional.of(click);
    }

    /**
     * Click events
     */
    public enum ClickEventType {

        /**
         * Open a URL on click
         */
        OPEN_URL("open_url"),

        /**
         * Force the player to run a command on click
         */
        RUN_COMMAND("run_command"),

        /**
         * Add text into the player's chat field
         */
        SUGGEST_TEXT("suggest_command");

        // JSON name of the events
        private final String actionName;

        ClickEventType(String actionName) {
            this.actionName = actionName;
        }

        /**
         * @return JSON name of the event
         */
        public String getActionName() {
            return actionName;
        }
    }

    public enum HoverEventType {

        /**
         * Show text on hover
         */
        SHOW_TEXT("show_text"),

        /**
         * Show an item information on hover
         */
        SHOW_ITEM("show_item"),

        /**
         * Show an achievement on hover
         */
        SHOW_ACHIEVEMENT("show_achievement"),

        /**
         * Show a statistic on hover (the same action name is due to them being the same, however the prefix is different)
         * Since achievements take achievement.AchievementName, while statistics take stat.StatisticName
         */
        SHOW_STATISTIC("show_achievement");

        // JSON name of the event
        private final String actionName;

        HoverEventType(String actionName) {
            this.actionName = actionName;
        }

        /**
         * @return JSON name of the event
         */
        public String getActionName() {
            return actionName;
        }
    }
}