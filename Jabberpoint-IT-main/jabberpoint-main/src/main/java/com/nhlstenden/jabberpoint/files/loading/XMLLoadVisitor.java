package com.nhlstenden.jabberpoint.files.loading;

import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.Slide;
import com.nhlstenden.jabberpoint.slide.SlideComponent;
import com.nhlstenden.jabberpoint.slide.SlideItemFactory;
import com.nhlstenden.jabberpoint.slide.SlideItemType;
import com.nhlstenden.jabberpoint.style.DefaultStyle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLLoadVisitor implements ContentVisitor
{
    private final Element root;

    public XMLLoadVisitor(File file) throws Exception
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(file);
        this.root = document.getDocumentElement();
    }

    @Override
    public void visitPresentation(Presentation presentation)
    {
        presentation.setTitle(getText());
        NodeList slideNodes = root.getElementsByTagName("slide");

        for (int i = 0; i < slideNodes.getLength(); i++)
        {
            Element slideElement = (Element) slideNodes.item(i);
            Slide slide = new Slide(new DefaultStyle());
            slide.setTitle(getText(slideElement));
            presentation.append(slide);

            NodeList itemNodes = slideElement.getElementsByTagName("item");
            for (int j = 0; j < itemNodes.getLength(); j++)
            {
                Element item = (Element) itemNodes.item(j);
                SlideComponent component = createComponent(item);
                slide.addSlideItem(component);
            }
        }
    }

    private String getText()
    {
        return root.getElementsByTagName("showtitle").item(0).getTextContent();
    }

    private String getText(Element parent)
    {
        return parent.getElementsByTagName("title").item(0).getTextContent();
    }

    private SlideComponent createComponent(Element item)
    {
        int level = parseLevel(item.getAttribute("level"));
        String type = item.getAttribute("kind");
        String content = item.getTextContent();

        SlideItemType itemType = switch (type.toLowerCase())
        {
            case "text" -> SlideItemType.TEXT;
            case "image" -> SlideItemType.BITMAP;
            default -> throw new IllegalArgumentException("Unknown slide item type: " + type);
        };

        return SlideItemFactory.getInstance().createSlideItem(itemType, level, content);
    }

    private int parseLevel(String value)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            return 1;
        }
    }
}
