package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

/**
 * <p>Classify the context (presentation, frame, file handler) for the command<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class CommandContext
{
    private final Presentation presentation;
    private final SlideViewerFrame frame;
    private final FileHandler fileHandler;

    public CommandContext(Presentation presentation, SlideViewerFrame frame, FileHandler fileHandler)
    {
        this.presentation = presentation;
        this.frame = frame;
        this.fileHandler = fileHandler;
    }

    public Presentation getPresentation()
    {
        return presentation;
    }

    public SlideViewerFrame getFrame()
    {
        return frame;
    }

    public FileHandler getFileHandler()
    {
        return fileHandler;
    }
}

