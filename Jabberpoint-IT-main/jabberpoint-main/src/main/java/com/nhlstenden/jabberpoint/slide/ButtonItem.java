package com.nhlstenden.jabberpoint.slide;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.List;

public class ButtonItem extends SlideComponent
{
    private final String label;
    ActionListener actionListener;
    final Rectangle bounds;

    public ButtonItem(int style, String label)
    {
        super(style);
        this.label = label;
        this.bounds = new Rectangle();
    }

    public void setActionListener(ActionListener listener)
    {
        this.actionListener = listener;
    }

    public void handleClick(Point clickPoint)
    {
        if (this.bounds.contains(clickPoint) && this.actionListener != null)
        {
            this.actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "click"));
        }
    }

    @Override
    public void draw(Graphics graphics, Rectangle area, ImageObserver observer)
    {
        Graphics2D g2d = (Graphics2D) graphics;

        // Store clickable bounds
        this.bounds.setRect(area.x, area.y, area.width, area.height);

        // Draw button background
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRoundRect(area.x, area.y, area.width, area.height, 8, 8);

        // Draw button text
        g2d.setColor(Color.BLACK);
        FontMetrics metrics = g2d.getFontMetrics();
        int textX = area.x + (area.width - metrics.stringWidth(this.label)) / 2;
        int textY = area.y + ((area.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(this.label, textX, textY);
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale)
    {
        FontMetrics metrics = g.getFontMetrics();
        int width = metrics.stringWidth(this.label) + 20; // Padding
        int height = metrics.getHeight() + 10;
        return new Rectangle(0, 0, width, height);
    }

    @Override
    public String getContent()
    {
        return null;
    }
}