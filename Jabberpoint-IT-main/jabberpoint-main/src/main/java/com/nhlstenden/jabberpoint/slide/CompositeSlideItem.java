package com.nhlstenden.jabberpoint.slide;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class CompositeSlideItem extends SlideComponent
{
    private final List<SlideComponent> children;

    public CompositeSlideItem(int level)
    {
        super(level);
        this.children = new ArrayList<>();
    }

    @Override
    public void draw(Graphics graphics, Rectangle area, ImageObserver observer)
    {
        // Divide area among children and draw each
        int childCount = this.children.size();
        if (childCount == 0) return;

        int areaWidth = area.width / childCount;

        for (int i = 0; i < childCount; i++)
        {
            Rectangle childArea = new Rectangle(
                    area.x + i * areaWidth,
                    area.y,
                    areaWidth,
                    area.height
            );
            this.children.get(i).draw(graphics, childArea, observer);
        }
    }

    @Override
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale)
    {
        // Combine bounding boxes of all children
        Rectangle combinedBox = null;
        for (SlideComponent child : this.children)
        {
            Rectangle childBox = child.getBoundingBox(graphics, observer, scale);
            if (combinedBox == null)
            {
                combinedBox = childBox;
            }
            else
            {
                combinedBox = combinedBox.union(childBox);
            }
        }
        return combinedBox != null ? combinedBox : new Rectangle(0, 0, 0, 0);
    }

    public List<SlideComponent> getChildren()
    {
        return new ArrayList<>(this.children);
    }

    public boolean hasChildren()
    {
        return !this.children.isEmpty();
    }

    @Override
    public String getContent()
    {
        return null;
    }

    public void addChild(SlideComponent child)
    {
        this.children.add(child);
    }

    public void removeChild(SlideComponent child)
    {
        this.children.remove(child);
    }
}