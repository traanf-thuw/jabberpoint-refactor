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
public class SavePresentationCommand implements MenuCommand {
    @Override
    public void execute(CommandContext context) {
        SlideViewerFrame frame = context.getFrame();
        Presentation presentation = context.getPresentation();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            try {
                if (!filename.toLowerCase().endsWith(".xml")) {
                    filename += ".xml";
                }
                new FileHandler().saveFile(presentation, filename);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "IO Exception: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

