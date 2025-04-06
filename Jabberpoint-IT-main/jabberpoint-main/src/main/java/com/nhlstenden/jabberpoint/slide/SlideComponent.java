package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.List;

/**
 * The base component in the Composite Pattern.
 * Defines the interface for all slide items.
 */
public abstract class SlideComponent
{
    private int level;
    private LevelStyle style;

    public SlideComponent(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return this.level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public LevelStyle getStyle()
    {
        return this.style;
    }

    public void setStyle(LevelStyle style)
    {
        this.style = style;
    }

    // Core drawing and layout methods all components must implement
    public abstract void draw(Graphics graphics, Rectangle area, ImageObserver observer);

    public abstract Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale);

    // Composite pattern methods
    public abstract List<SlideComponent> getChildren();

    public abstract boolean hasChildren();

    public abstract String getContent();

    // Methods that might be overridden by composite implementations
    public void addChild(SlideComponent child)
    {
        throw new UnsupportedOperationException("Cannot add child to this component");
    }

    public void removeChild(SlideComponent child)
    {
        throw new UnsupportedOperationException("Cannot remove child from this component");
    }
}