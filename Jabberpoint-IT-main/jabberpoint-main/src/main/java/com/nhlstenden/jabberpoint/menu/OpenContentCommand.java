package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * <p>Open a presentation<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class OpenContentCommand<T extends Content> implements MenuCommand<T>
{
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";

    @Override
    public void execute(CommandContext<T> context)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int result = fileChooser.showOpenDialog(context.getFrame());

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            try
            {
                context.getContent().clear();
                context.getFileHandler().loadFile(context.getContent(), file.getAbsolutePath());
                context.getContent().setShowListNumber(0);
                context.getFrame().setTitle(context.getContent().getTitle());
                context.getFrame().repaint();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(context.getFrame(), IOEX + e.getMessage(), LOADERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
