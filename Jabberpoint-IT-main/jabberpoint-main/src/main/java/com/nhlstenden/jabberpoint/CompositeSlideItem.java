package com.nhlstenden.jabberpoint;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class CompositeSlideItem extends AbstractSlideItem {
    private List<AbstractSlideItem> children = new ArrayList<>();

    public CompositeSlideItem(int level) {
        super(level);
    }

    @Override
    public void draw(Graphics graphics, Rectangle area, ImageObserver observer) {
        // Divide area among children and draw each
        int childCount = children.size();
        if (childCount == 0) return;

        int areaWidth = area.width / childCount;

        for (int i = 0; i < childCount; i++) {
            Rectangle childArea = new Rectangle(
                    area.x + i * areaWidth,
                    area.y,
                    areaWidth,
                    area.height
            );
            children.get(i).draw(graphics, childArea, observer);
        }
    }

    @Override
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale) {
        // Combine bounding boxes of all children
        Rectangle combinedBox = null;
        for (AbstractSlideItem child : children) {
            Rectangle childBox = child.getBoundingBox(graphics, observer, scale);
            if (combinedBox == null) {
                combinedBox = childBox;
            } else {
                combinedBox = combinedBox.union(childBox);
            }
        }
        return combinedBox != null ? combinedBox : new Rectangle(0, 0, 0, 0);
    }

    @Override
    public List<AbstractSlideItem> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public void addChild(AbstractSlideItem child) {
        children.add(child);
    }

    @Override
    public void removeChild(AbstractSlideItem child) {
        children.remove(child);
    }
}