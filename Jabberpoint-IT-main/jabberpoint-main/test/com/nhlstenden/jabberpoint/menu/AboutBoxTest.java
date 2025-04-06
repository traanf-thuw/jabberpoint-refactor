package com.nhlstenden.jabberpoint.menu;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.Frame;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class AboutBoxTest
{

    private static final String EXPECTED_MESSAGE =
            "JabberPoint is a primitive slide-show program in Java(tm). \n" +
                    "It is freely copyable as long as you keep this notice and the splash screen intact.\n" +
                    "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
                    "Adapted by Gert Florijn (version 1.1) and Sylvia Stuurman (version 1.2 and higher) \n" +
                    "for the Open University of the Netherlands, 2002 -- now. \n" +
                    "Author's version available from http://www.darwinsys.com/";

    @Test
    void show_DisplaysCorrectAboutDialog()
    {
        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
        {
            Frame mockFrame = mock(Frame.class);

            // Execute
            AboutBox.show(mockFrame);

            // Verify
            mockedPane.verify(() ->
                    JOptionPane.showMessageDialog(
                            eq(mockFrame),
                            eq(EXPECTED_MESSAGE),
                            eq("About JabberPoint"),
                            eq(JOptionPane.INFORMATION_MESSAGE)
                    )
            );
        }
    }

    @Test
    void show_WorksWithNullParent()
    {
        try (MockedStatic<JOptionPane> mockedPane = mockStatic(JOptionPane.class))
        {
            // Execute
            AboutBox.show(null);

            // Verify
            mockedPane.verify(() ->
                    JOptionPane.showMessageDialog(
                            isNull(),
                            eq(EXPECTED_MESSAGE),
                            eq("About JabberPoint"),
                            eq(JOptionPane.INFORMATION_MESSAGE)
                    )
            );
        }
    }
}