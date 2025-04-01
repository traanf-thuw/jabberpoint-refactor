package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.style.LevelStyle;

public class SlideItemFactory
{
    // Singleton instance of the factory
    private static SlideItemFactory instance;

    // Private constructor to prevent direct instantiation
    private SlideItemFactory()
    {
    }

    // Singleton access method
    public static synchronized SlideItemFactory getInstance()
    {
        if (instance == null)
        {
            instance = new SlideItemFactory();
        }
        return instance;
    }

    /**
     * Creates a SlideItem based on the type and parameters
     *
     * @param type    The type of SlideItem to create
     * @param level   The level of the SlideItem
     * @param content The content for the SlideItem
     * @return A new SlideItem instance
     */
    public SlideComponent createSlideItem(SlideItemType type, int level, String content)
    {
        return switch (type)
        {
            case TEXT -> new TextItem(level, content);
            case BITMAP -> new BitmapItem(level, content);
        };
    }

    /**
     * Enum to represent different types of SlideItems
     */
    public enum SlideItemType
    {
        TEXT,
        BITMAP
    }

    /**
     * Additional factory method for creating items with default styling
     */
    public SlideComponent createSlideItem(SlideItemType type, LevelStyle style)
    {
        return switch (type)
        {
            case TEXT -> new TextItem(style);
            case BITMAP -> new BitmapItem(style);
        };
    }
}
