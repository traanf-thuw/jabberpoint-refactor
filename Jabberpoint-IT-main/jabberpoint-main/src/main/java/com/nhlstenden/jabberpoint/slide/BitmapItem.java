package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.slide.SlideComponent;
import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Bitmap item that can be used as a leaf in the Composite pattern
 *
 * @author Thu Tran - Bocheng
 * @version 1.8 2025/03/26
 */
public class BitmapItem extends SlideComponent
{
    private BufferedImage bufferedImage;
    private final String imageName;

    protected static final String FILE = "File ";
    protected static final String NOTFOUND = " not found";

    // Constructor for creating a bitmap item with a specific level and image name
    public BitmapItem(int level, String name)
    {
        super(level);
        imageName = name;
        try
        {
            bufferedImage = (name != null) ? ImageIO.read(new File(name)) : null;
        } catch (IOException e)
        {
            System.err.println(FILE + imageName + NOTFOUND);
        }
    }

    // Constructor for creating an empty bitmap item
    public BitmapItem(LevelStyle levelStyle)
    {
        this(0, null);
    }

    // Returns the filename of the image
    public String getName()
    {
        return imageName;
    }

    @Override
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale)
    {
        if (bufferedImage == null)
        {
            return new Rectangle(0, 0, 0, 0);
        }

        LevelStyle myStyle = getStyle(); // Assuming getStyle() method from parent class
        return new Rectangle(
                (int) (myStyle.getIndent() * scale),
                0,
                (int) (bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.getLeading() * scale)) +
                        (int) (bufferedImage.getHeight(observer) * scale)
        );
    }

    @Override
    public void draw(Graphics graphics, Rectangle area, ImageObserver observer)
    {
        if (bufferedImage == null)
        {
            return;
        }

        LevelStyle myStyle = getStyle(); // Assuming getStyle() method from parent class
        int width = area.x + (int) (myStyle.getIndent() * area.getWidth() / area.getWidth());
        int height = area.y + (int) (myStyle.getLeading() * area.getHeight() / area.getHeight());

        graphics.drawImage(
                bufferedImage,
                width,
                height,
                (int) (bufferedImage.getWidth(observer) * area.getWidth() / area.getWidth()),
                (int) (bufferedImage.getHeight(observer) * area.getHeight() / area.getHeight()),
                observer
        );
    }

    @Override
    public List<SlideComponent> getChildren()
    {
        // Leaf items have no children
        return Collections.emptyList();
    }

    @Override
    public boolean hasChildren()
    {
        return false;
    }

    @Override
    public void addChild(SlideComponent child)
    {
        // Leaf items cannot have children
        throw new UnsupportedOperationException("BitmapItem cannot have children");
    }

    @Override
    public void removeChild(SlideComponent child)
    {
        // Leaf items cannot have children
        throw new UnsupportedOperationException("BitmapItem cannot have children");
    }

    @Override
    public String toString()
    {
        return "BitmapItem[" + getLevel() + "," + imageName + "]";
    }
}