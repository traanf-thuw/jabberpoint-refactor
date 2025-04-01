package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.style.DefaultStyle;

class NewPresentationCommand implements MenuCommand {
    private Presentation presentation;

    public NewPresentationCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void execute() {
        // Clear current presentation and create a new one
        presentation.clear();
        // Add default slide
        presentation.append(new Slide(new DefaultStyle()));
    }
}
