package com.nhlstenden.jabberpoint.files.saving;

import com.nhlstenden.jabberpoint.ContentVisitor;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.slide.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLSaveVisitor implements ContentVisitor
{
    private final File file;
    private Document doc;
    private Element root;

    public XMLSaveVisitor(File file) throws Exception
    {
        this.file = file;
        this.initializeDocument();
    }

    private void initializeDocument() throws ParserConfigurationException
    {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        this.doc = builder.newDocument();
        this.root = doc.createElement("presentation");
        doc.appendChild(root);
    }

    @Override
    public void visitPresentation(Presentation presentation)
    {
        try
        {
            this.addPresentationTitle(presentation);
            this.addAllSlides(presentation);
            this.writeXmlToFile();
        }
        catch (TransformerException e)
        {
            showSaveErrorDialog(e.getMessage());
        }
    }

    private void addPresentationTitle(Presentation presentation)
    {
        Element showTitle = doc.createElement("showtitle");
        showTitle.setTextContent(presentation.getTitle());
        root.appendChild(showTitle);
    }

    private void addAllSlides(Presentation presentation)
    {
        for (Slide slide : presentation.getShowList())
        {
            Element xmlSlide = createSlideElement(slide);
            root.appendChild(xmlSlide);
        }
    }

    private Element createSlideElement(Slide slide)
    {
        Element xmlSlide = doc.createElement("slide");
        addSlideTitle(slide, xmlSlide);
        addSlideItems(slide, xmlSlide);
        return xmlSlide;
    }

    private void addSlideTitle(Slide slide, Element xmlSlide)
    {
        Element titleElement = doc.createElement("title");
        titleElement.setTextContent(slide.getTitle());
        xmlSlide.appendChild(titleElement);
    }

    private void addSlideItems(Slide slide, Element xmlSlide)
    {
        for (SlideComponent item : slide.getSlideItems())
        {
            Element xmlItem = createItemElement(item);
            xmlSlide.appendChild(xmlItem);
        }
    }

    private Element createItemElement(SlideComponent item)
    {
        Element xmlItem = doc.createElement("item");
        setItemAttributes(xmlItem, item);
        xmlItem.setTextContent(item.getContent());
        return xmlItem;
    }

    private void setItemAttributes(Element xmlItem, SlideComponent item)
    {
        xmlItem.setAttribute("level", String.valueOf(item.getLevel()));
        xmlItem.setAttribute("kind", getItemType(item));
    }

    private String getItemType(SlideComponent item)
    {
        return item instanceof TextItem ? "text" : "image";
    }

    private void writeXmlToFile() throws TransformerException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }

    private void showSaveErrorDialog(String errorMessage)
    {
        JOptionPane.showMessageDialog(
                null,
                "Failed to save XML: " + errorMessage,
                "Save Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
