package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

import javax.swing.*;

public class GotoSlideCommand implements MenuCommand
{
    private Presentation presentation;
    private SlideViewerFrame frame;
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
        String slideNumberStr = JOptionPane.showInputDialog(frame, PAGENR);
        if (slideNumberStr != null)
        {
            try
            {
                int slideNumber = Integer.parseInt(slideNumberStr.trim()) - 1;
                if (slideNumber >= 0 && slideNumber < presentation.getShowList().size())
                {
                    presentation.setSlideNumber(slideNumber);
                    frame.repaint();  // Refresh the viewer
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Slide number out of range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
