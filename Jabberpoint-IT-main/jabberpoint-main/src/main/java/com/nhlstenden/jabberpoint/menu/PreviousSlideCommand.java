package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;

public class PreviousSlideCommand implements MenuCommand
{
    private final Presentation presentation;

    public PreviousSlideCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void execute()
    {
        this.presentation.prevSlide();
    }
}
