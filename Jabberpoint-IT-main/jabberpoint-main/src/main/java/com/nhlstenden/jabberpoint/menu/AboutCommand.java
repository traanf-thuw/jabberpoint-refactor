package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.slide.SlideViewerFrame;

class AboutCommand implements MenuCommand {
    private SlideViewerFrame frame;

    public AboutCommand(SlideViewerFrame frame) {
        this.frame = frame;
    }

    @Override
    public void execute() {
        AboutBox.show(frame);
    }
}
