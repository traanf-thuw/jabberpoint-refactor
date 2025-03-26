package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.SlideViewerFrame;
import com.nhlstenden.jabberpoint.files.saving.XMLSaver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

class SavePresentationCommand implements MenuCommand {
    private static final String IOEX = "IO Exception: ";
    private static final String SAVEERR = "Save Error";
    private Presentation presentation;
    private SlideViewerFrame frame;

    public SavePresentationCommand(Presentation presentation, SlideViewerFrame frame) {
        this.presentation = presentation;
        this.frame = frame;
    }

    @Override
    public void execute() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                new XMLSaver().savePresentation(presentation, file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, IOEX + e.getMessage(), SAVEERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
