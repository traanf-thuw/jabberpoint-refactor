package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;
import com.nhlstenden.jabberpoint.files.FileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

class OpenPresentationCommand implements MenuCommand {
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";
    private Presentation presentation;
    private SlideViewerFrame frame;

    public OpenPresentationCommand(Presentation presentation, SlideViewerFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                FileHandler fileHandler = new FileHandler();  // Use FileHandler like the original
                presentation.clear();  // Clear existing slides before loading
                fileHandler.loadFile(presentation, file.getAbsolutePath());  // Use FileHandler to find the right loader

                presentation.setSlideNumber(0);  // Ensure first slide is displayed
                frame.setTitle(presentation.getShowTitle());
                frame.repaint();  // Refresh UI
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, IOEX + e.getMessage(), LOADERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
