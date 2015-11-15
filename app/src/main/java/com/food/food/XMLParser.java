package com.food.food;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.util.Log;
import android.widget.ArrayAdapter;

public class XMLParser {

    private static XMLParserHandler saxHandler;

    public static ArrayList<CatalogItems> parse(InputStream is) {
        ArrayList<CatalogItems> catalogItems = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();

            // create a SAXXMLHandler
            saxHandler = new XMLParserHandler();
            Reader isr = new InputStreamReader(is);
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(isr);
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(inputSource);
            catalogItems = saxHandler.getCatalogItems();

        } catch (Exception ex) {
            Log.d("Error", "Error in XMLParser");
        }

        return catalogItems;
    }

    public static HashMap<Integer, ArrayList<Dish>> getDishHashMap(){
        return saxHandler.getDishes();
    }
}