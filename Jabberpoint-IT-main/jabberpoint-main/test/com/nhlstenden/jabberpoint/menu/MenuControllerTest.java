package com.nhlstenden.jabberpoint.menu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuControllerTest
{
    CommandContext context;
    MenuController controller;

    @BeforeEach
    void setup()
    {
        context = mock(CommandContext.class);
        controller = new MenuController(context);
    }

    @Test
    void testCreateCommand_knownActions()
    {
        assertTrue(controller.createCommand("Open") instanceof OpenContentCommand<?>);
        assertTrue(controller.createCommand("New") instanceof NewContentCommand<?>);
        assertTrue(controller.createCommand("Save") instanceof SaveContentCommand<?>);
        assertTrue(controller.createCommand("Exit") instanceof ExitApplicationCommand);
        assertTrue(controller.createCommand("Next") instanceof NextCommand<?>);
        assertTrue(controller.createCommand("Prev") instanceof PreviousCommand<?>);
        assertTrue(controller.createCommand("Go to") instanceof GotoCommand<?>);
        assertTrue(controller.createCommand("About") instanceof AboutCommand);
    }

    @Test
    void testCreateCommand_unknownAction_returnsNull()
    {
        assertNull(controller.createCommand("UnknownAction"));
    }

    @Test
    void testActionPerformed_executesCommand()
    {
        // Spy on a command so we can verify execute() call
        MenuCommand commandSpy = spy(new OpenContentCommand());

        // Create a controller subclass overriding createCommand to return our spy
        MenuController controllerSpy = new MenuController(context)
        {
            @Override
            public MenuCommand createCommand(String action)
            {
                return commandSpy;
            }
        };

        // Simulate action event with any command
        ActionEvent event = new ActionEvent(this, 0, "Open");
        controllerSpy.actionPerformed(event);

        // Verify execute was called once with context
        verify(commandSpy, times(1)).execute(context);
    }

    @Test
    void testActionPerformed_nullCommand_doesNothing()
    {
        // Create a controller subclass returning null for createCommand
        MenuController controllerSpy = new MenuController(context)
        {
            @Override
            public MenuCommand createCommand(String action)
            {
                return null;
            }
        };

        ActionEvent event = new ActionEvent(this, 0, "Unknown");
        // Just ensure no exception and no calls made
        controllerSpy.actionPerformed(event);
    }
}
