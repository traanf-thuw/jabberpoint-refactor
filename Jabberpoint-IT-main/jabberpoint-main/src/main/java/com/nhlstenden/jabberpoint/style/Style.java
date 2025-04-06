package com.nhlstenden.jabberpoint.style;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Contains data for the Styles.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class Style
{
    protected Map<Integer, LevelStyle> styles;

    public Style()
    {
        this.styles = new HashMap<>();
    }

    public void addStyle(int level, LevelStyle style)
    {
        this.styles.put(level, style);
    }

    public void removeStyle(int level)
    {
        this.styles.remove(level);
    }

    /**
     * Gets the Style from the styles map.
     * If the given level does not exist, fall back to another style in the map.
     * And if there is none do a last fallback to a default style.
     *
     * @param level The item-level of the style.
     * @return The style of the given level.
     */
    public LevelStyle getStyle(int level)
    {
        if (!this.styles.containsKey(level))
        {
            //It does not exist, look for another style to use.
            for (LevelStyle levelStyle : this.styles.values())
            {
                return levelStyle;
            }
        }
        else
        {
            //It exists.
            return this.styles.get(level);
        }
        //Last fallback, use hardcoded style.
        return new LevelStyle("Helvetica", 0, Color.BLACK, 48, 20);
    }
}
