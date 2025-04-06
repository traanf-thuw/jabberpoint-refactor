package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.slide.*;
import com.nhlstenden.jabberpoint.style.LevelStyle;

import java.util.ArrayList;
import java.util.List;

public class Presentation
{
    private String showTitle; // The title of the presentation
    private List<Slide> showList; // List of slides
    private int currentSlideNumber; // The number of the current slide
    private SlideViewerComponent slideViewComponent; // The view component of the slides
    private SlideItemFactory factory; // Factory instance

    public Presentation()
    {
        this.showTitle = "";
        this.slideViewComponent = null;
        this.factory = SlideItemFactory.getInstance();
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
        this.factory = SlideItemFactory.getInstance();
        clear();
    }

    public String getShowTitle()
    {
        return this.showTitle;
    }

    public void setShowTitle(String showTitle)
    {
        this.showTitle = showTitle;
    }

    public List<Slide> getShowList()
    {
        return this.showList;
    }

    public void setShowList(List<Slide> showList)
    {
        this.showList = showList;
    }

    public int getCurrentSlideNumber()
    {
        return this.currentSlideNumber;
    }

    public void setCurrentSlideNumber(int currentSlideNumber)
    {
        this.currentSlideNumber = currentSlideNumber;
    }

    public SlideViewerComponent getSlideViewComponent()
    {
        return this.slideViewComponent;
    }

    public void setSlideViewComponent(SlideViewerComponent slideViewComponent)
    {
        this.slideViewComponent = slideViewComponent;
    }

    public SlideItemFactory getFactory()
    {
        return this.factory;
    }

    public void setFactory(SlideItemFactory factory)
    {
        this.factory = factory;
    }

    public void addSlideComponent(int slideIndex, SlideComponent component)
    {
        if (slideIndex < 0 || slideIndex >= this.showList.size())
        {
            throw new IllegalArgumentException("Invalid slide index");
        }

        this.showList.get(slideIndex).addSlideItem(component);
    }

    // Delegating slide item creation to the factory
    public void addSlideItem(int slideIndex, SlideItemType type, int level, String content)
    {
        SlideComponent component = this.factory.createSlideItem(type, level, content);
        this.addSlideComponent(slideIndex, component);
    }

    public void append(Slide slide)
    {
        this.showList.add(slide);
    }

    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }

        return this.showList.get(number);
    }

    public Slide getCurrentSlide()
    {
        return this.getSlide(this.currentSlideNumber);
    }

    public int getSize()
    {
        return this.showList.size();
    }

    public void clear()
    {
        this.showList = new ArrayList<>();
        setSlideNumber(-1);
    }

    public void setSlideNumber(int number)
    {
        if (number < 0)
        {
            this.currentSlideNumber = 0; // Ensure it doesn't go below 0
        }
        else if (number >= getSize())
        {
            this.currentSlideNumber = getSize() - 1; // Prevent exceeding available slides
        }
        else
        {
            this.currentSlideNumber = number;
        }

        if (this.slideViewComponent != null)
        {
            this.slideViewComponent.update(this, getCurrentSlide());
        }
    }


    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide()
    {
        if (this.currentSlideNumber > 0)
        {
            setSlideNumber(this.currentSlideNumber - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide()
    {
        if (this.currentSlideNumber < (this.showList.size() - 1))
        {
            setSlideNumber(this.currentSlideNumber + 1);
        }
    }

    public void refreshView()
    {
        // This should trigger a UI update in your presentation component
        setSlideNumber(this.currentSlideNumber); // Forces redraw
    }
}
