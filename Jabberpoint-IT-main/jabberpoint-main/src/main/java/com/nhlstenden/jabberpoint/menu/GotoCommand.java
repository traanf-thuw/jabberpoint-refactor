package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

import javax.swing.*;

/**
 * <p>Go to a specific content page<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class GotoCommand<T extends Content> implements MenuCommand<T>
{
    private static final String PAGENR = "Page number?";

    @Override
    public void execute(CommandContext<T> context)
    {
        SlideViewerFrame frame = context.getFrame();
        T content = context.getContent();

        String slideNumberStr = JOptionPane.showInputDialog(frame, PAGENR);
        if (slideNumberStr != null)
        {
            try
            {
                int showListNumber = Integer.parseInt(slideNumberStr.trim()) - 1;
                if (showListNumber >= 0 && showListNumber < content.getShowListSize())
                {
                    content.setShowListNumber(showListNumber);
                    frame.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Slide number out of range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(frame, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
