package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.AboutBox;
import com.nhlstenden.jabberpoint.SlideViewerFrame;

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
