package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;

class NextSlideCommand implements MenuCommand
{
    private Presentation presentation;

    public NextSlideCommand(Presentation presentation)
    {
        this.presentation = presentation;
    }

    @Override
    public void execute()
    {
        presentation.nextSlide();
    }
}
