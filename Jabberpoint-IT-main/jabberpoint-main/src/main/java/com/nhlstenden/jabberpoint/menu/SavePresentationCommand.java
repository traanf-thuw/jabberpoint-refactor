package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

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

    @Override
    public void execute()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showSaveDialog(frame);

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
                new FileHandler().saveFile(presentation, filename);
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(frame, IOEX + e.getMessage(), SAVEERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
