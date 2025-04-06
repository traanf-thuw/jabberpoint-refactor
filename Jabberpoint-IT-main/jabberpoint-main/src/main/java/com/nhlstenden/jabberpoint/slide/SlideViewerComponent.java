package com.nhlstenden.jabberpoint.slide;

import com.nhlstenden.jabberpoint.Presentation;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class SlideViewerComponent extends JComponent
{

    private Slide slide; //The current slide
    private final Font labelFont; //The font for labels
    private Presentation presentation; //The presentation
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

    public SlideViewerComponent(Presentation pres, SlideViewerFrame frame)
    {
        setBackground(BGCOLOR);
        this.presentation = pres;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                handleClick(e.getPoint());
            }
        });
    }

    private void handleClick(Point clickPoint)
    {
        Slide currentSlide = this.presentation.getCurrentSlide();
        if (currentSlide == null) return;

        for (SlideComponent item : currentSlide.getSlideItems())
        {
            if (item instanceof ButtonItem)
            {
                ((ButtonItem) item).handleClick(clickPoint);
            }
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void update(Presentation presentation, Slide data)
    {
        if (data == null)
        {
            repaint();
            return;
        }
        this.presentation = presentation;
        this.slide = data;
        repaint();
        this.frame.setTitle(presentation.getShowTitle());
    }

    //Draw the slide
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (this.presentation.getSize() < 0 || slide == null)
        {
            return;
        }
        g.setFont(this.labelFont);
        g.setColor(COLOR);
        g.drawString("Slide " + (this.presentation.getCurrentSlideNumber() + 1) + " of " +
                this.presentation.getSize(), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        this.slide.draw(g, area, this);
    }
}
