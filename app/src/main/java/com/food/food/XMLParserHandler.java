package com.food.food;




import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.HashMap;


public class XMLParserHandler extends DefaultHandler{

    private HashMap<Integer, ArrayList<Dish>> dishes;
    private ArrayList<CatalogItems> catalogItems;
    private String tempVal;
    private Dish tempDish;
    private String nameDescr;
    private String key;
    private CatalogItems tempCatalogItems;

    public XMLParserHandler(){
        dishes = new HashMap<>();
        catalogItems = new ArrayList<>();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("category")){
            tempCatalogItems.setName(tempVal);
            catalogItems.add(tempCatalogItems);
        } else if (qName.equalsIgnoreCase("url")) {
            tempDish.setUlrLocation(tempVal);
        } else if (qName.equalsIgnoreCase("name")){
            tempDish.setName(tempVal);
        } else if (qName.equalsIgnoreCase("price")){
            tempDish.setPrice(tempVal);
        } else if (qName.equalsIgnoreCase("description")){
            tempDish.setDescription(tempVal);
        } else if (qName.equalsIgnoreCase("picture")){
            tempDish.setUlrPicture(tempVal);
        } else if (qName.equalsIgnoreCase("categoryID")){
            key = tempVal;
            tempDish.setCategory(Integer.valueOf(tempVal));
        } else if (qName.equalsIgnoreCase("param")){
            tempDish.getTotalDescription().put(nameDescr, tempVal);
        } else if (qName.equalsIgnoreCase("offer")){
            if(dishes.containsKey(Integer.valueOf(key))){
                dishes.get(Integer.valueOf(key)).add(tempDish);
            } else {
                ArrayList<Dish> dd = new ArrayList<>();
                dd.add(tempDish);
                dishes.put(Integer.valueOf(key), dd);
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tempVal = "";
        if(qName.equalsIgnoreCase("category")){
            tempCatalogItems = new CatalogItems();
            tempCatalogItems.setId(Integer.valueOf(attributes.getValue("id")));
        } else if(qName.equalsIgnoreCase("offer")){
            tempDish = new Dish();
            tempDish.setOfferId(Integer.valueOf(attributes.getValue("id")));
        } else if(qName.equalsIgnoreCase("param")){
                nameDescr = attributes.getValue("name");
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public ArrayList<CatalogItems> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(ArrayList<CatalogItems> catalogItems) {
        this.catalogItems = catalogItems;
    }

    public HashMap<Integer, ArrayList<Dish>> getDishes() {
        return dishes;
    }

    public void setDishes(HashMap<Integer, ArrayList<Dish>> dishes) {
        this.dishes = dishes;
    }
}
