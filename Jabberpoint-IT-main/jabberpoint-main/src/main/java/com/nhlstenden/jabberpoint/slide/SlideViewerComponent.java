package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.Content;

import java.awt.*;
import java.io.Serial;
import javax.swing.JComponent;

/**
 * <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class SlideViewerComponent extends JComponent
{
    private Content content;
    private final Font labelFont;
    private final SlideViewerFrame frame;
    @Serial
    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    public SlideViewerComponent(Content content, SlideViewerFrame frame)
    {
        setBackground(BGCOLOR);
        this.content = content;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);  // Could be generalized if needed
    }

    public void update(Content content)
    {
        this.content = content;
        repaint();
        frame.setTitle(content.getTitle());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);

        g.setFont(labelFont);
        g.setColor(COLOR);
        g.drawString("Item " + (this.content.getShowListSize()) + " shown", XPOS, YPOS);

        Rectangle area = new Rectangle(0, YPOS, getWidth(), getHeight() - YPOS);
        this.content.accept(new ContentRenderVisitor(g, area, this));
    }
}
