package com.nhlstenden.jabberpoint.menu;

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
class SavePresentationCommand implements MenuCommand
{
    private static final String IOEX = "IO Exception: ";
    private static final String SAVEERR = "Save Error";
    private Presentation presentation;
    private SlideViewerFrame frame;

    public SavePresentationCommand(Presentation presentation, SlideViewerFrame frame)
    {
        this.presentation = presentation;
        this.frame = frame;
    }

    public Presentation getPresentation()
    {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation)
    {
        this.presentation = presentation;
    }

    public SlideViewerFrame getFrame()
    {
        return this.frame;
    }

    public void setFrame(SlideViewerFrame frame)
    {
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showSaveDialog(this.frame);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            try
            {
                if (fileChooser.getFileFilter().getDescription().equalsIgnoreCase("xml files"))
                {
                    filename += ".xml";
                }
                new FileHandler().saveFile(this.presentation, filename);
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(this.frame, IOEX + e.getMessage(), SAVEERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
