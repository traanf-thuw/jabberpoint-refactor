package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;

/**
 * <p>Go to next slide<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
class NextSlideCommand implements MenuCommand
{
    private final Presentation presentation;

    public NextSlideCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void execute()
    {
        this.presentation.nextSlide();
    }
}
