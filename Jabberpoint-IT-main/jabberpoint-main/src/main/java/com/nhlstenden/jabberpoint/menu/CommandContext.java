package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;
import com.nhlstenden.jabberpoint.files.FileHandler;
import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

/**
 * <p>Classify the context (presentation, frame, file handler) for the command<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class CommandContext<T extends Content>
{
    private final T content;
    private final SlideViewerFrame frame;
    private final FileHandler<T> fileHandler;

    public CommandContext(T content, SlideViewerFrame frame, FileHandler<T> fileHandler)
    {
        this.content = content;
        this.frame = frame;
        this.fileHandler = fileHandler;
    }

    public T getContent()
    {
        return this.content;
    }

    public SlideViewerFrame getFrame()
    {
        return this.frame;
    }

    public FileHandler<T> getFileHandler()
    {
        return this.fileHandler;
    }
}