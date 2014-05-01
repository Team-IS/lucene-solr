package org.apache.lucene.meta;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * This class is responsible for initializing the parsing process.
 *
 */
public class MetaDataParser {
   
   /**
    * The location where the xml file exists. 
    */
   private String filePath;
   
   /**
    * Default Constructor.
    */
   public MetaDataParser() {
      this("");
   }
   
   /**
    * Overloaded Constructor.
    * 
    * @param path The path where the xml is stored.
    */
   public MetaDataParser(String path) {
      this.filePath = path;
   }
   
   public String[] parse() {
      
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser(); 
         MetaDataHandler handler = new MetaDataHandler();
         
         //Parse xml file
         saxParser.parse(this.filePath, handler);
         
         return handler.getMetaData();
      }
      
      catch(Exception e) {
         System.err.println("Exception occured while parsing the xml metadata file : \n");
         e.printStackTrace();
      }
      
      return null;
   }
}
