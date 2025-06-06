package com.nhlstenden.jabberpoint.slide;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;

class SlideComponentTest
{
    static class TestSlideComponent extends SlideComponent {

        public TestSlideComponent(int level) {
            super(level);
        }

        @Override
        public void draw(Graphics graphics, Rectangle area, ImageObserver observer)
        {

        }

        @Override
        public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale) {
            return new Rectangle(10, 10, 100, 50); // dummy value
        }

        @Override
        public String getContent() {
            return "test content";
        }
    }
    @Test
    void testLevelGetterAndSetter() {
        TestSlideComponent comp = new TestSlideComponent(2);
        assertEquals(2, comp.getLevel());

        comp.setLevel(5);
        assertEquals(5, comp.getLevel());
    }
}