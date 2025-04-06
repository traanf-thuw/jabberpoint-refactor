package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.style.LevelStyle;
import com.nhlstenden.jabberpoint.style.Style;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * A slide with enhanced drawing functionality using the Composite pattern
 *
 * @version 1.8 2025/03/26
 */
public class Slide
{
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;

    protected String title;
    protected List<SlideComponent> items;
    protected Style style;

    public Slide(Style style)
    {
        this.items = new ArrayList<>();
        this.style = style;
    }

    /**
     * Adds an AbstractSlideItem to the slide
     *
     * @param item The new slide item to add
     */
    public void addSlideItem(SlideComponent item)
    {
        // Set the style for the item based on its level
        LevelStyle levelStyle = this.style.getStyle(item.getLevel());
        item.setStyle(levelStyle);
        this.items.add(item);
    }

    /**
     * Creates a TextItem from a string and adds it to the slide
     *
     * @param level   The level of the TextItem
     * @param message The text of the new TextItem
     */
    public void addText(int level, String message)
    {
        TextItem textItem = new TextItem(level, message);
        this.addSlideItem(textItem);
    }

    /**
     * Creates a BitmapItem and adds it to the slide
     *
     * @param level     The level of the BitmapItem
     * @param imagePath The path to the image
     */
    public void addImage(int level, String imagePath)
    {
        BitmapItem bitmapItem = new BitmapItem(level, imagePath);
        this.addSlideItem(bitmapItem);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String newTitle)
    {
        this.title = newTitle;
    }

    public SlideComponent getSlideItem(int number)
    {
        return this.items.get(number);
    }

    public List<SlideComponent> getSlideItems()
    {
        return new ArrayList<>(this.items);
    }

    public int getSize()
    {
        return this.items.size();
    }

    /**
     * Draws the slide using the Composite pattern
     *
     * @param graphics The graphics object for the Slide
     * @param area     The rendering area for the Slide
     * @param view     The observer for the Slide
     */
    public void draw(Graphics graphics, Rectangle area, ImageObserver view)
    {
        float scale = getScale(area);
        int currentY = area.y;

        // Draw title
        if (this.title != null && !this.title.isEmpty())
        {
            TextItem titleItem = new TextItem(0, this.title);
            LevelStyle titleStyle = this.style.getStyle(0);
            titleItem.setStyle(titleStyle);

            Rectangle titleArea = new Rectangle(
                    area.x,
                    currentY,
                    area.width,
                    (int) (titleStyle.getFontSize() * scale * 1.5)
            );
            titleItem.draw(graphics, titleArea, view);

            // Move Y down after title
            currentY += (int) (titleStyle.getFontSize() * scale * 2);
        }

        // Draw slide items with vertical spacing and level-based sizing
        for (SlideComponent item : this.items)
        {
            // Get style for this item's level
            LevelStyle itemStyle = this.style.getStyle(item.getLevel());

            // Calculate font size based on level (smaller for deeper levels)
            int fontSize = (int) (itemStyle.getFontSize() * scale * (1.5 - (item.getLevel() * 0.2)));

            // Create area for this item
            Rectangle itemArea = new Rectangle(
                    area.x,
                    currentY,
                    area.width,
                    (int) (fontSize * 1.5)  // Increased height to prevent overlap
            );

            // Temporarily modify the style to adjust font size
            LevelStyle adjustedStyle = new LevelStyle(
                    itemStyle.getFontName(),
                    itemStyle.getIndent(),
                    itemStyle.getColor(),
                    fontSize,
                    itemStyle.getLeading()
            );
            item.setStyle(adjustedStyle);

            // Draw the item
            item.draw(graphics, itemArea, view);

            // Move Y down, adding some extra spacing
            currentY += (int) (fontSize * 1.5);
        }
    }

    /**
     * Returns the scale to draw a slide
     *
     * @param area The area for the Slide
     * @return The scale
     */
    private float getScale(Rectangle area)
    {
        return Math.min(
                ((float) area.width) / ((float) WIDTH),
                ((float) area.height) / ((float) HEIGHT)
        );
    }

    /**
     * Creates a composite slide item from all current items
     *
     * @return A CompositeSlideItem containing all slide items
     */
    public CompositeSlideItem createComposite()
    {
        CompositeSlideItem composite = new CompositeSlideItem(1);
        for (SlideComponent item : this.items)
        {
            composite.addChild(item);
        }
        return composite;
    }
}