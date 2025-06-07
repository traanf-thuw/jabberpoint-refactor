package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * <p>Save a presentation<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class SaveContentCommand<T extends Content> implements MenuCommand<T>
{
    @Override
    public void execute(CommandContext<T> context)
    {
        SlideViewerFrame frame = context.getFrame();
        Content content = context.getContent();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            try
            {
                if (!filename.toLowerCase().endsWith(".xml"))
                {
                    filename += ".xml";
                }
                new FileHandler().saveFile(content, filename);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(frame, "IO Exception: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}