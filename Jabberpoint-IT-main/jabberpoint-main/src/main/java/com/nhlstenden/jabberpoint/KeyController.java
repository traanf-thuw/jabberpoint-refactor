package com.nhlstenden.jabberpoint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
 * <p>This is the KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman, Rick Vinke
 * @version 1.7 2023/01/14 Rick Vinke
 */
public class KeyController extends KeyAdapter
{
    private final Presentation presentation; //Commands are given to the presentation

    public KeyController(Presentation p)
    {
        presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, '+' -> presentation.nextSlide();
            case KeyEvent.VK_PAGE_UP, KeyEvent.VK_UP, '-' -> presentation.prevSlide();
            case 'q', 'Q' -> System.exit(0);
            //Should not be reached
            default ->
            {
            }
        }
    }
}
