package com.nhlstenden.jabberpoint.style;

import java.awt.Color;
import java.awt.Font;

/**
 * Contains data for the Style of a item-level
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
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