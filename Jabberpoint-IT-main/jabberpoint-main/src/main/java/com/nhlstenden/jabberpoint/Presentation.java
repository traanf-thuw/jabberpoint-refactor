package com.nhlstenden.jabberpoint;

import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideComponent;
import com.nhlstenden.jabberpoint.slide.SlideItemFactory;
import com.nhlstenden.jabberpoint.slide.SlideItemFactory.SlideItemType;
import com.nhlstenden.jabberpoint.slide.SlideViewerComponent;
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
        showTitle = "";
        slideViewComponent = null;
        factory = SlideItemFactory.getInstance();
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
        factory = SlideItemFactory.getInstance();
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
        if (slideIndex < 0 || slideIndex >= showList.size())
        {
            throw new IllegalArgumentException("Invalid slide index");
        }
        showList.get(slideIndex).addSlideItem(component);
    }

    public void addSlideItem(int slideIndex, SlideItemType type, int level, String content)
    {
        SlideComponent component = factory.createSlideItem(type, level, content);
        addSlideComponent(slideIndex, component);
    }

    public void addSlideItem(int slideIndex, SlideItemType type, LevelStyle style)
    {
        SlideComponent component = factory.createSlideItem(type, style);
        addSlideComponent(slideIndex, component);
    }

    public void append(Slide slide)
    {
        showList.add(slide);
    }

    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }
        return showList.get(number);
    }

    public Slide getCurrentSlide()
    {
        return getSlide(currentSlideNumber);
    }

    public int getSize()
    {
        return showList.size();
    }

    public void clear()
    {
        showList = new ArrayList<>();
        setSlideNumber(-1);
    }

    public void setSlideNumber(int number)
    {
        if (number < 0)
        {
            currentSlideNumber = 0; // Ensure it doesn't go below 0
        }
        else if (number >= getSize())
        {
            currentSlideNumber = getSize() - 1; // Prevent exceeding available slides
        }
        else
        {
            currentSlideNumber = number;
        }

        if (slideViewComponent != null)
        {
            slideViewComponent.update(this, getCurrentSlide());
        }
    }


    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide()
    {
        if (currentSlideNumber > 0)
        {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide()
    {
        if (currentSlideNumber < (showList.size() - 1))
        {
            setSlideNumber(currentSlideNumber + 1);
        }
    }
}
