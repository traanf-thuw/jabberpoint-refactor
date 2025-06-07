package com.nhlstenden.jabberpoint.slide;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nhlstenden.jabberpoint.style.LevelStyle;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class BitmapItemTest
{

    private static final String TEST_IMAGE_PATH = "test.png";
    private static final int TEST_WIDTH = 100;
    private static final int TEST_HEIGHT = 80;
    private static final float SCALE = 1.5f;

    // --- Constructor Tests ---

    @Test
    void constructor_validImage_createsItem() throws IOException
    {
        BufferedImage mockImage = mock(BufferedImage.class);
        try (var mockedIO = mockStatic(ImageIO.class))
        {
            mockedIO.when(() -> ImageIO.read(any(File.class))).thenReturn(mockImage);

            BitmapItem item = new BitmapItem(1, TEST_IMAGE_PATH);

            // Covers: super(level), imageName assignment, ImageIO.read success
            assertEquals(TEST_IMAGE_PATH, item.getName());
            assertSame(mockImage, item.bufferedImage);
        }
    }

    @Test
    void constructor_invalidImage_logsError() throws IOException
    {
        try (var mockedIO = mockStatic(ImageIO.class))
        {
            mockedIO.when(() -> ImageIO.read(any(File.class)))
                    .thenThrow(new IOException("File not found"));

            // Covers: IOException catch block
            BitmapItem item = new BitmapItem(1, "invalid.jpg");

            assertNull(item.bufferedImage);
            assertEquals("invalid.jpg", item.getName());
        }
    }

    @Test
    void constructor_nullName_createsNullImage()
    {
        // Covers: (name != null) ? ... : null branch
        BitmapItem item = new BitmapItem(1, null);
        assertNull(item.bufferedImage);
        assertNull(item.getName());
    }

    @Test
    void defaultConstructor_createsEmptyItem()
    {
        // Covers: this(0, null)
        BitmapItem item = new BitmapItem();
        assertNull(item.bufferedImage);
        assertNull(item.getName());
    }

    // --- Bounding Box Tests ---

    @Test
    void getBoundingBox_nullImage_returnsEmpty()
    {
        // Covers: bufferedImage == null check
        BitmapItem item = new BitmapItem(0, null);
        Rectangle result = item.getBoundingBox(mock(Graphics.class), mock(ImageObserver.class), SCALE);
        assertEquals(new Rectangle(0, 0, 0, 0), result);
    }

    @Test
    void getBoundingBox_validImage_calculatesCorrectly()
    {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth(any())).thenReturn(TEST_WIDTH);
        when(mockImage.getHeight(any())).thenReturn(TEST_HEIGHT);

        BitmapItem item = new BitmapItem(1, TEST_IMAGE_PATH)
        {
            @Override
            public LevelStyle getStyle()
            {
                // Covers: getStyle() usage
                return new LevelStyle("Times", 20, Color.BLACK, 10, 10); // indent=20, leading=10
            }
        };
        item.bufferedImage = mockImage;

        Rectangle result = item.getBoundingBox(
                mock(Graphics.class),
                mock(ImageObserver.class),
                SCALE
        );

        // Verify style calculations:
        // indent=20*1.5=30, leading=10*1.5=15
        // width=100*1.5=150, height=15+80*1.5=135
        assertEquals(new Rectangle(30, 0, 150, 135), result);
    }

    // --- Drawing Tests ---

    @Test
    void draw_nullImage_doesNothing()
    {
        // Covers: bufferedImage == null early return
        BitmapItem item = new BitmapItem();
        Graphics2D mockGraphics = mock(Graphics2D.class);
        item.draw(mockGraphics, new Rectangle(0, 0, 100, 100), mock(ImageObserver.class));
        verifyNoInteractions(mockGraphics);
    }

    @Test
    void draw_validImage_drawsCorrectly()
    {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth()).thenReturn(TEST_WIDTH);
        when(mockImage.getHeight()).thenReturn(TEST_HEIGHT);

        BitmapItem item = new BitmapItem(1, TEST_IMAGE_PATH);
        item.bufferedImage = mockImage;

        Graphics2D mockGraphics = mock(Graphics2D.class);
        Rectangle area = new Rectangle(0, 0, 200, 160);

        item.draw(mockGraphics, area, mock(ImageObserver.class));

        // Covers: scaleFactor calculation, centering, drawImage call
        ArgumentCaptor<Integer> xCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockGraphics).drawImage(
                eq(mockImage),
                xCaptor.capture(),
                anyInt(),
                anyInt(),
                anyInt(),
                any()
        );
        assertEquals(-100, xCaptor.getValue().intValue());
    }

    // --- Other Method Tests ---

    @Test
    void getContent_returnsImageName()
    {
        BitmapItem item = new BitmapItem(1, TEST_IMAGE_PATH);
        assertEquals(TEST_IMAGE_PATH, item.getContent());
    }

    @Test
    void toString_returnsCorrectFormat()
    {
        BitmapItem item = new BitmapItem(2, "image.jpg");
        assertEquals("BitmapItem[2,image.jpg]", item.toString());
    }
}