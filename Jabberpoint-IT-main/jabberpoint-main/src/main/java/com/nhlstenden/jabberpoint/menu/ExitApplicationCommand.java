package com.nhlstenden.jabberpoint.menu;

/**
 * <p>Exit the app<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
class ExitApplicationCommand implements MenuCommand
{
    @Override
    public void execute()
    {
        System.exit(0);
    }
}
