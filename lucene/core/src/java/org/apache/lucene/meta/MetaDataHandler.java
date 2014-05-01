package org.apache.lucene.meta;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is responsible for handling the xml file 
 * that contains the metadata information that were used
 * during indexing. 
 */
public class MetaDataHandler extends DefaultHandler {

     /**
      * An array that will hold the metadata information.
      * 
      * metadata[0] = Version that was used.
      * metadata[1] = Analyzer that was used.
      * metadata[2] = Similarity function that was used.
      */
     private String[] metadata;
     
     /**
      * A flag that indicates if we are parsing
      * through the version tag.
      */
     private boolean parsingVersion;
     
     /**
      * A flag that indicates if we are parsing
      * through the analyzer tag.
      */
     private boolean parsingAnalyzer;
     
     /**
      * A flag that indicates if we are parsing
      * through the similarity tag.
      */
     private boolean parsingSimilarity;
     
     /**
      * Default constructor.
      */
     public MetaDataHandler() {
          this.metadata = new String[3];
          this.parsingAnalyzer = false;
          this.parsingSimilarity = false;
          this.parsingVersion = false;
     }

     /**
      *  Gets the metadata array.
      *  
      *  @return metadata An array that contains the metadata.
      */
     public String[] getMetaData() {
          return this.metadata;
     }
     
     @Override
         public void startElement(String uri , String localName , String qName , Attributes atts) throws SAXException {
           
          if(qName.equals("uses-version")) {
               this.parsingVersion = true;
          }
         
          else if(qName.equals("uses-analyzer")) {
               this.parsingAnalyzer = true;
          }
         
          else if(qName.equals("uses-similarity")) {
               this.parsingSimilarity = true;
          }
     }
    
     @Override
     public void characters(char ch[], int start, int length) throws SAXException {
         
          if(this.parsingVersion) {
               this.metadata[0] = new String(ch,start,length).trim();
          }
         
          else if(this.parsingAnalyzer) {
               this.metadata[1] = new String(ch,start,length).trim();
          }
         
          else if(this.parsingSimilarity) {
               this.metadata[2] = new String(ch,start,length).trim();
          }
     }

     @Override
     public void endElement(String uri, String localName, String qName) throws SAXException {
         
          if(qName.equals("uses-version")) {
               this.parsingVersion = false;
          }
         
          else if(qName.equals("uses-analyzer")) {
               this.parsingAnalyzer = false;
          }
         
          else if(qName.equals("uses-similarity")) {
               this.parsingSimilarity = false;
          }
     }   
}
