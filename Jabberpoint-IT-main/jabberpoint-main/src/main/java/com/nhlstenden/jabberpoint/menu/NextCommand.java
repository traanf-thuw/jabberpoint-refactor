package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;

/**
 * <p>Go to next content page<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class NextCommand<T extends Content> implements MenuCommand<T>
{
    @Override
    public void execute(CommandContext<T> context)
    {
        context.getContent().next();
    }
}
