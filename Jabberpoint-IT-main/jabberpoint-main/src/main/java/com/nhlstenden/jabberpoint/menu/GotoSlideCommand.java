package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

import javax.swing.*;

/**
 * <p>Go to a specific slide<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class GotoSlideCommand implements MenuCommand
{
    private final Presentation presentation;
    private final SlideViewerFrame frame;
    private static final String PAGENR = "Page number?";

    public GotoSlideCommand(Presentation presentation, SlideViewerFrame frame)
    {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        // Ask for slide number input
        String slideNumberStr = JOptionPane.showInputDialog(this.frame, PAGENR);
        if (slideNumberStr != null)
        {
            try
            {
                int slideNumber = Integer.parseInt(slideNumberStr.trim()) - 1;
                if (slideNumber >= 0 && slideNumber < this.presentation.getShowList().size())
                {
                    this.presentation.setSlideNumber(slideNumber);
                    this.frame.repaint();  // Refresh the viewer
                }
                else
                {
                    JOptionPane.showMessageDialog(this.frame, "Slide number out of range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this.frame, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
