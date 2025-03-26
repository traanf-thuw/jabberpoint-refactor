package com.nhlstenden.jabberpoint.menu;

class ExitApplicationCommand implements MenuCommand {
    @Override
    public void execute() {
        System.exit(0);
    }
}
