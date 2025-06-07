package com.nhlstenden.jabberpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

class KeyControllerTest
{
    private Presentation mockPresentation;
    private KeyController keyController;

    @BeforeEach
    void setUp()
    {
        mockPresentation = mock(Presentation.class);
        keyController = new KeyController(mockPresentation);
    }

    @Test
    void keyPressed_pageDown_callsNextSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, KeyEvent.VK_PAGE_DOWN, ' ');
        keyController.keyPressed(event);
        verify(mockPresentation).next();
    }

    @Test
    void keyPressed_downArrow_callsNextSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, KeyEvent.VK_DOWN, ' ');
        keyController.keyPressed(event);
        verify(mockPresentation).next();
    }

    @Test
    void keyPressed_enter_callsNextSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, KeyEvent.VK_ENTER, ' ');
        keyController.keyPressed(event);
        verify(mockPresentation).next();
    }

    @Test
    void keyPressed_plus_callsNextSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, '+', '+');
        keyController.keyPressed(event);
        verify(mockPresentation).next();
    }

    @Test
    void keyPressed_pageUp_callsPrevSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, KeyEvent.VK_PAGE_UP, ' ');
        keyController.keyPressed(event);
        verify(mockPresentation).previous();
    }

    @Test
    void keyPressed_upArrow_callsPrevSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, KeyEvent.VK_UP, ' ');
        keyController.keyPressed(event);
        verify(mockPresentation).previous();
    }

    @Test
    void keyPressed_minus_callsPrevSlide()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, '-', '-');
        keyController.keyPressed(event);
        verify(mockPresentation).previous();
    }

    @Test
    void keyPressed_unknownKey_doesNothing()
    {
        KeyEvent event = new KeyEvent(mock(Component.class), 1, 1, 0, KeyEvent.VK_F1, ' ');
        keyController.keyPressed(event);
        verifyNoInteractions(mockPresentation);
    }
}