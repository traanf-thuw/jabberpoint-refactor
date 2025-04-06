package com.nhlstenden.jabberpoint.style;

import java.awt.*;

/**
 * <p>Contains the default style of the older versions.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class DefaultStyle extends Style
{
	private static final String FONT_NAME = "Helvetica";
    public DefaultStyle()
    {
        super();
        this.styles.put(0, new LevelStyle(FONT_NAME, 5, Color.red, 48, 20));
        this.styles.put(1, new LevelStyle(FONT_NAME, 10, Color.blue, 40, 10));
        this.styles.put(2, new LevelStyle(FONT_NAME, 20, Color.black, 36, 10));
        this.styles.put(3, new LevelStyle(FONT_NAME, 50, Color.black, 30, 10));
        this.styles.put(4, new LevelStyle(FONT_NAME, 70, Color.black, 24, 10));
    }
}
