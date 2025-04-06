package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import com.nhlstenden.jabberpoint.files.FileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

class OpenPresentationCommand implements MenuCommand
{
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";
    private final Presentation presentation;
    private final SlideViewerFrame frame;

    public OpenPresentationCommand(Presentation presentation, SlideViewerFrame frame)
    {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showOpenDialog(this.frame);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();

            try
            {
                FileHandler fileHandler = new FileHandler();  // Use FileHandler like the original
                this.presentation.clear();  // Clear existing slides before loading
                fileHandler.loadFile(this.presentation, file.getAbsolutePath());  // Use FileHandler to find the right loader

                this.presentation.setSlideNumber(0);  // Ensure first slide is displayed
                this.frame.setTitle(this.presentation.getShowTitle());
                this.frame.repaint();  // Refresh UI
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(this.frame, IOEX + e.getMessage(), LOADERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
