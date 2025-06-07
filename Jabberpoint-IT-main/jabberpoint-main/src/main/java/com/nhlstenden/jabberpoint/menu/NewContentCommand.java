package com.nhlstenden.jabberpoint.menu;

import com.nhlstenden.jabberpoint.Content;

/**
 * <p>Create new content<p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class NewContentCommand<T extends Content> implements MenuCommand<T>
{
    @Override
    public void execute(CommandContext<T> context)
    {
        T content = context.getContent();
        content.clear();  // Reset content generically
        content.accept(new NewContentVisitor<>(context));
        context.getFrame().repaint();  // UI refresh
    }
}
