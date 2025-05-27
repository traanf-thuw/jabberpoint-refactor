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
    private static final String PAGENR = "Page number?";

    @Override
    public void execute(CommandContext context)
    {
        SlideViewerFrame frame = context.getFrame();
        Presentation presentation = context.getPresentation();

        String slideNumberStr = JOptionPane.showInputDialog(frame, PAGENR);
        if (slideNumberStr != null)
        {
            try
            {
                int slideNumber = Integer.parseInt(slideNumberStr.trim()) - 1;
                if (slideNumber >= 0 && slideNumber < presentation.getShowList().size())
                {
                    presentation.setSlideNumber(slideNumber);
                    frame.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Slide number out of range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
