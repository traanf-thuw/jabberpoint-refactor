package com.nhlstenden.jabberpoint;

import java.awt.image.ImageObserver;

import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.List;

public abstract class AbstractSlideItem {
    private int level;
    private LevelStyle style;

    public AbstractSlideItem(int level) {
        this.level = level;
    }

    // Abstract methods for drawing and getting bounding box
    public abstract void draw(Graphics graphics, Rectangle area, ImageObserver observer);
    public abstract Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale);

    // Composite pattern methods
    public abstract List<AbstractSlideItem> getChildren();
    public abstract void addChild(AbstractSlideItem child);
    public abstract void removeChild(AbstractSlideItem child);

    // Getters and setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LevelStyle getStyle() {
        return style;
    }

    public void setStyle(LevelStyle style) {
        this.style = style;
    }
}
