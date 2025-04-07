package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

/**
 * <p>Load the about box<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
class AboutCommand implements MenuCommand
{
    private final SlideViewerFrame frame;

    public AboutCommand(SlideViewerFrame frame)
    {
        this.frame = frame;
    }

    @Override
    public void execute()
    {
        AboutBox.show(this.frame);
    }
}
