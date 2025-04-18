package com.nhlstenden.jabberpoint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * <p>This is the KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2025/04/02 Thu Tran - Bocheng Peng
 */
public class KeyController extends KeyAdapter
{
    private final Presentation presentation; //Commands are given to the presentation

    public KeyController(Presentation p)
    {
        this.presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, '+' -> this.presentation.nextSlide();
            case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, '-' -> this.presentation.prevSlide();
            case 'q', 'Q' -> System.exit(0);
            //Should not be reached
            default ->
            {
            }
        }
    }
}
