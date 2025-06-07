package com.nhlstenden.jabberpoint.slide;

/**
 * Factory to create TextItem and BitmapItem
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
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
}
