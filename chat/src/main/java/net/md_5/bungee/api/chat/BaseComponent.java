package net.md_5.bungee.api.chat;

import com.google.common.base.Joiner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@NoArgsConstructor
public abstract class BaseComponent
{

    @Setter(AccessLevel.NONE)
    BaseComponent parent;

    /**
     * The color of this component and any child components (unless overridden)
     */
    private ChatColor color;
    /**
     * Whether this component and any child components (unless overridden) is
     * bold
     */
    private Boolean bold;
    /**
     * Whether this component and any child components (unless overridden) is
     * italic
     */
    private Boolean italic;
    /**
     * Whether this component and any child components (unless overridden) is
     * underlined
     */
    private Boolean underlined;
    /**
     * Whether this component and any child components (unless overridden) is
     * strikethrough
     */
    private Boolean strikethrough;
    /**
     * Whether this component and any child components (unless overridden) is
     * obfuscated
     */
    private Boolean obfuscated;
    /**
     * The text to insert into the chat when this component (and child
     * components) are clicked while pressing the shift key
     */
    @Getter
    private String insertion;

    /**
     * Appended components that inherit this component's formatting and events
     */
    @Getter
    private List<BaseComponent> extra;

    /**
     * The action to preform when this component (and child components) are
     * clicked
     */
    @Getter
    private ClickEvent clickEvent;
    /**
     * The action to preform when this component (and child components) are
     * hovered over
     */
    @Getter
    private HoverEvent hoverEvent;

    BaseComponent(BaseComponent old)
    {
        setColor( old.getColorRaw() );
        setBold( old.isBoldRaw() );
        setItalic( old.isItalicRaw() );
        setUnderlined( old.isUnderlinedRaw() );
        setStrikethrough( old.isStrikethroughRaw() );
        setObfuscated( old.isObfuscatedRaw() );
        setInsertion( old.getInsertion() );
        setClickEvent( old.getClickEvent() );
        setHoverEvent( old.getHoverEvent() );
        if ( old.getExtra() != null )
        {
            for ( BaseComponent component : old.getExtra() )
            {
                addExtra( component.duplicate() );
            }
        }
    }

    /**
     * Clones the BaseComponent and returns the clone.
     *
     * @return The duplicate of this BaseComponent
     */
    public abstract BaseComponent duplicate();

    /**
     * Converts the components to a string that uses the old formatting codes
     * ({@link net.md_5.bungee.api.ChatColor#COLOR_CHAR}
     *
     * @param components the components to convert
     * @return the string in the old format
     */
    public static String toLegacyText(BaseComponent... components)
    {
        StringBuilder builder = new StringBuilder();
        for ( BaseComponent msg : components )
        {
            builder.append( msg.toLegacyText() );
        }
        return builder.toString();
    }

    /**
     * Converts the components into a string without any formatting
     *
     * @param components the components to convert
     * @return the string as plain text
     */
    public static String toPlainText(BaseComponent... components)
    {
        StringBuilder builder = new StringBuilder();
        for ( BaseComponent msg : components )
        {
            builder.append( msg.toPlainText() );
        }
        return builder.toString();
    }

    /**
     * Returns the color of this component. This uses the parent's color if this
     * component doesn't have one. {@link net.md_5.bungee.api.ChatColor#WHITE}
     * is returned if no color is found.
     *
     * @return the color of this component
     */
    public ChatColor getColor()
    {
        if ( color == null )
        {
            if ( parent == null )
            {
                return ChatColor.WHITE;
            }
            return parent.getColor();
        }
        return color;
    }

    /**
     * Returns the color of this component without checking the parents color.
     * May return null
     *
     * @return the color of this component
     */
    public ChatColor getColorRaw()
    {
        return color;
    }

    /**
     * Returns whether this component is bold. This uses the parent's setting if
     * this component hasn't been set. false is returned if none of the parent
     * chain has been set.
     *
     * @return whether the component is bold
     */
    public boolean isBold()
    {
        if ( bold == null )
        {
            return parent != null && parent.isBold();
        }
        return bold;
    }

    /**
     * Returns whether this component is bold without checking the parents
     * setting. May return null
     *
     * @return whether the component is bold
     */
    public Boolean isBoldRaw()
    {
        return bold;
    }

    /**
     * Returns whether this component is italic. This uses the parent's setting
     * if this component hasn't been set. false is returned if none of the
     * parent chain has been set.
     *
     * @return whether the component is italic
     */
    public boolean isItalic()
    {
        if ( italic == null )
        {
            return parent != null && parent.isItalic();
        }
        return italic;
    }

    /**
     * Returns whether this component is italic without checking the parents
     * setting. May return null
     *
     * @return whether the component is italic
     */
    public Boolean isItalicRaw()
    {
        return italic;
    }

    /**
     * Returns whether this component is underlined. This uses the parent's
     * setting if this component hasn't been set. false is returned if none of
     * the parent chain has been set.
     *
     * @return whether the component is underlined
     */
    public boolean isUnderlined()
    {
        if ( underlined == null )
        {
            return parent != null && parent.isUnderlined();
        }
        return underlined;
    }

    /**
     * Returns whether this component is underlined without checking the parents
     * setting. May return null
     *
     * @return whether the component is underlined
     */
    public Boolean isUnderlinedRaw()
    {
        return underlined;
    }

    /**
     * Returns whether this component is strikethrough. This uses the parent's
     * setting if this component hasn't been set. false is returned if none of
     * the parent chain has been set.
     *
     * @return whether the component is strikethrough
     */
    public boolean isStrikethrough()
    {
        if ( strikethrough == null )
        {
            return parent != null && parent.isStrikethrough();
        }
        return strikethrough;
    }

    /**
     * Returns whether this component is strikethrough without checking the
     * parents setting. May return null
     *
     * @return whether the component is strikethrough
     */
    public Boolean isStrikethroughRaw()
    {
        return strikethrough;
    }

    /**
     * Returns whether this component is obfuscated. This uses the parent's
     * setting if this component hasn't been set. false is returned if none of
     * the parent chain has been set.
     *
     * @return whether the component is obfuscated
     */
    public boolean isObfuscated()
    {
        if ( obfuscated == null )
        {
            return parent != null && parent.isObfuscated();
        }
        return obfuscated;
    }

    /**
     * Returns whether this component is obfuscated without checking the parents
     * setting. May return null
     *
     * @return whether the component is obfuscated
     */
    public Boolean isObfuscatedRaw()
    {
        return obfuscated;
    }

    public void setExtra(List<BaseComponent> components)
    {
        for ( BaseComponent component : components )
        {
            component.parent = this;
        }
        extra = components;
    }

    /**
     * Appends a text element to the component. The text will inherit this
     * component's formatting
     *
     * @param text the text to append
     */
    public void addExtra(String text)
    {
        addExtra( new TextComponent( text ) );
    }

    /**
     * Appends a component to the component. The text will inherit this
     * component's formatting
     *
     * @param component the component to append
     */
    public void addExtra(BaseComponent component)
    {
        if ( extra == null )
        {
            extra = new ArrayList<BaseComponent>();
        }
        component.parent = this;
        extra.add( component );
    }

    /**
     * Returns whether the component has any formatting or events applied to it
     *
     * @return Whether any formatting or events are applied
     */
    public boolean hasFormatting()
    {
        return color != null || bold != null
                || italic != null || underlined != null
                || strikethrough != null || obfuscated != null
                || hoverEvent != null || clickEvent != null;
    }

    /**
     * Converts the component into a string without any formatting
     *
     * @return the string as plain text
     */
    public String toPlainText()
    {
        StringBuilder builder = new StringBuilder();
        toPlainText( builder );
        return builder.toString();
    }

    void toPlainText(StringBuilder builder)
    {
        if ( extra != null )
        {
            for ( BaseComponent e : extra )
            {
                e.toPlainText( builder );
            }
        }
    }

    /**
     * Converts the component to a string that uses the old formatting codes
     * ({@link net.md_5.bungee.api.ChatColor#COLOR_CHAR}
     *
     * @return the string in the old format
     */
    public String toLegacyText()
    {
        StringBuilder builder = new StringBuilder();
        toLegacyText( builder );
        return builder.toString();
    }

    void toLegacyText(StringBuilder builder)
    {
        if ( extra != null )
        {
            for ( BaseComponent e : extra )
            {
                e.toLegacyText( builder );
            }
        }
    }

    protected static final ChatColor[] DECORATIONS = {
        ChatColor.BOLD,
        ChatColor.ITALIC,
        ChatColor.UNDERLINE,
        ChatColor.STRIKETHROUGH,
        ChatColor.MAGIC
    };

    protected static final Joiner JOINER = Joiner.on(", ");

    public Boolean hasFormatRaw(ChatColor format) {
        switch(format) {
            case BOLD: return isBoldRaw();
            case ITALIC: return isItalicRaw();
            case UNDERLINE: return isUnderlinedRaw();
            case STRIKETHROUGH: return isStrikethroughRaw();
            case MAGIC: return isObfuscatedRaw();
            case RESET: return null;
        }

        if(getColorRaw() == null) {
            return null;
        } else {
            return getColorRaw() == format;
        }
    }

    public Map<ChatColor, Boolean> getFormatsRaw() {
        EnumMap<ChatColor, Boolean> formats = new EnumMap(ChatColor.class);

        if(getColorRaw() != null) formats.put(getColorRaw(), true);

        for(ChatColor format : DECORATIONS) {
            final Boolean flag = hasFormatRaw(format);
            if(flag != null) formats.put(format, flag);
        }

        return formats;
    }

    protected void toStringTerminal(List<String> fields) {
        if(getColorRaw() != null) {
            fields.add("color=\"" + getColorRaw().name().toLowerCase() + "\"");
        }

        for(ChatColor format : DECORATIONS) {
            final Boolean flag = hasFormatRaw(format);
            if(flag != null) {
                fields.add(format.name().toLowerCase() + "=" + flag);
            }
        }

        if(getClickEvent() != null) {
            fields.add("clickEvent=" + getClickEvent());
        }
    }

    protected void toStringRecursive(List<String> fields) {
        if(getHoverEvent() != null) {
            fields.add("hoverEvent=" + getHoverEvent());
        }

        if(getExtra() != null && !getExtra().isEmpty()) {
            fields.add("extra=[" + Joiner.on(", ").join(getExtra()) + "]");
        }
    }

    private static final ThreadLocal<Set<BaseComponent>> visited = new ThreadLocal<Set<BaseComponent>>() {
        @Override protected Set<BaseComponent> initialValue() {
            return new HashSet<BaseComponent>();
        }
    };

    @Override
    public String toString() {
        List<String> fields = new ArrayList<String>();
        toStringTerminal(fields);
        try {
            if(visited.get().add(this)) {
                toStringRecursive(fields);
            } else {
                fields.add("... (cycle)");
            }
        } finally {
            visited.get().remove(this);
        }
        return getClass().getSimpleName() + "{" + JOINER.join(fields) + "}";
    }
}
