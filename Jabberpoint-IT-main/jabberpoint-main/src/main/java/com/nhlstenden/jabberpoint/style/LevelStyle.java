package com.nhlstenden.jabberpoint.style;

/**
 * <p>Contains data for the Style of a item-level.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2025/04/06 Thu Tran - Bocheng Peng
 */

import java.awt.Color;
import java.awt.Font;

public class LevelStyle
{
    private final String fontName;
    private final int indent;
    private final Color color;
    private final Font font;
    private final int fontSize;
    private final int leading;

    public LevelStyle(String fontName, int indent, Color color, int points, int leading)
    {
        this.fontName = fontName;
        this.indent = indent;
        this.color = color;
        this.fontSize = points;
        this.leading = leading;

        // Create font using the correct fontSize
        this.font = new Font(fontName, Font.BOLD, this.fontSize);
    }

    public String getFontName()
    {
        return this.fontName;
    }

    public int getIndent()
    {
        return this.indent;
    }

    public Color getColor()
    {
        return this.color;
    }

    public Font getFont()
    {
        return this.font;
    }

    public int getFontSize()
    {
        return this.fontSize;
    }

    public int getLeading()
    {
        return this.leading;
    }

    // Method to get font style (useful for creating new fonts if needed)
    public int getFontStyle()
    {
        return Font.BOLD;
    }
}