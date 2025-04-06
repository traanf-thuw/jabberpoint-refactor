package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.awt.*;
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
    BufferedImage bufferedImage;
    private final String imageName;

    protected static final String FILE = "File ";
    protected static final String NOTFOUND = " not found";

    // Constructor for creating a bitmap item with a specific level and image name
    public BitmapItem(int level, String name)
    {
        super(level);
        this.imageName = name;
        try
        {
            this.bufferedImage = (name != null) ? ImageIO.read(new File(name)) : null;
        } catch (IOException e)
        {
            System.err.println(FILE + this.imageName + NOTFOUND);
        }
    }

    // Constructor for creating an empty bitmap item
    public BitmapItem()
    {
        this(0, null);
    }

    // Returns the filename of the image
    public String getName()
    {
        return this.imageName;
    }

    @Override
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale)
    {
        if (this.bufferedImage == null)
        {
            return new Rectangle(0, 0, 0, 0);
        }

        LevelStyle myStyle = getStyle(); // Assuming getStyle() method from parent class
        return new Rectangle(
                (int) (myStyle.getIndent() * scale),
                0,
                (int) (this.bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.getLeading() * scale)) +
                        (int) (this.bufferedImage.getHeight(observer) * scale)
        );
    }

    @Override
    public void draw(Graphics graphics, Rectangle area, ImageObserver observer) {
        if (this.bufferedImage == null) return;

        Graphics2D g2d = (Graphics2D) graphics;

        // Calculate scaling while preserving aspect ratio
        double scaleFactor = Math.min(
                area.getWidth() / this.bufferedImage.getWidth() * 20,
                area.getHeight() / this.bufferedImage.getHeight() * 20
        );

        // Calculate scaled dimensions
        int scaledWidth = (int) (this.bufferedImage.getWidth() * scaleFactor);
        int scaledHeight = (int) (this.bufferedImage.getHeight() * scaleFactor);

        // Center the image in the available area
        int x = area.x + ((area.width - scaledWidth) / 2);
        int y = area.y + ((area.height - scaledHeight) / 2);

        g2d.drawImage(this.bufferedImage, x, y, scaledWidth, scaledHeight, observer);
    }

    @Override
    public String getContent()
    {
        return this.imageName;
    }

    @Override
    public String toString()
    {
        return "BitmapItem[" + getLevel() + "," + this.imageName + "]";
    }
}