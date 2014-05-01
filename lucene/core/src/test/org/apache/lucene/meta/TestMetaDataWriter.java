package org.apache.lucene.meta;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.meta.MetaDataWriter;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A class for testing the MetaDateWriter class.
 */
public class TestMetaDataWriter {

   private MetaDataWriter mdw;
   
   private IndexWriterConfig iwc;
   
   private Analyzer analyzer;
   
   /**
    * Method called before every test.
    * It initializes our fields.
    */
   @Before
   public void setUp() throws Exception {
    analyzer = new EnglishAnalyzer(Version.LUCENE_50);
    
    iwc = new IndexWriterConfig(Version.LUCENE_50 , analyzer);
    
    mdw = new MetaDataWriter(iwc);
   }

   /**
    * Method called after every test.
    * It sets our fields to null.
    */
   @After
   public void tearDown() throws Exception {
    analyzer = null;
    
    iwc = null;
    
    mdw = null;
   }

   /**
    * Test the behavior of the normalizeAnalyzer method , 
    * in case that an input string contains the @.
    */
   @Test
   public void testNormalizeAnalyzer1() {
    String an = "EnglishAnalyzer@123456" ;
    
    String expected = "EnglishAnalyzer" ;
    
    String normalized = mdw.normalizeAnalyzer(an);
    
    assertEquals(expected, normalized);
   }
   
   /**
    * Test the behavior of the normalizeAnalyzer method , 
    * in case that an input string does not contain the @.
    */
   @Test
   public void testNormalizeAnalyzer2() {
    String an = "EnglishAnalyzer" ;
    
    String expected = "EnglishAnalyzer" ;
    
    String normalized = mdw.normalizeAnalyzer(an);
    
    assertEquals(expected, normalized);
   }
   
   /**
    * Test the writing to an xml file , in a directory
    * that we do not have permissions.
    */
   @Test
   public void testWriteMetaData1() {
       mdw.setFilePath("/");
       mdw.writeMetaData();
       assertTrue(true);
   }
   
   /**
    * Test we write to the xml file , an expected expression.
    */
   @Test
   public void testWriteMetaData2() {
      mdw.writeMetaData();
      
      String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
        + "<indexinfo><uses-version>LUCENE_50</uses-version>"
        + "<uses-analyzer>org.apache.lucene.analysis.en.EnglishAnalyzer</uses-analyzer>"
        + "<uses-similarity>DefaultSimilarity</uses-similarity></indexinfo>";
      
      String actual = "";
      
      try {
         BufferedReader br = new BufferedReader(new FileReader(new File("metadata.xml")));
      
         String line = "";
      
         while((line = br.readLine()) != null) {
            actual += line.trim();
         }
      
         br.close();
      
         assertEquals(expected.trim() , actual.trim());
      }
      
      catch(Exception e) {
         e.printStackTrace();
      }
  }
   
   /**
    * Test we write to the xml file , an expected expression , 
    * if we change the IndexWriterConfig object.
    */
   @Test
   public void testWriteMetaData3() {
      mdw = new MetaDataWriter();
      mdw.setIwc(iwc);
      mdw.writeMetaData();
      
      String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
        + "<indexinfo><uses-version>LUCENE_50</uses-version>"
        + "<uses-analyzer>org.apache.lucene.analysis.en.EnglishAnalyzer</uses-analyzer>"
        + "<uses-similarity>DefaultSimilarity</uses-similarity></indexinfo>";
      
      String actual = "";
      
      try {
         BufferedReader br = new BufferedReader(new FileReader(new File("metadata.xml")));
      
         String line = "";
      
         while((line = br.readLine()) != null) {
            actual += line.trim();
         }
      
         br.close();
      
         assertEquals(expected.trim() , actual.trim());
      }
      
      catch(Exception e) {
         e.printStackTrace();
      }
  }
   
   /**
    * Test if the xml file created is read only.
    */
   @Test
   public void testWriteMetaData4() {
      mdw.writeMetaData();
      
      File file = new File("metadata.xml");
      
      assertFalse(file.canWrite());
   }
   
   
   /**
    * Test if the xml file created , really exists.
    */
   @Test
   public void testWriteMetaData5() {
      mdw.writeMetaData();
       
      File file = new File("metadata.xml");
       
      assertTrue(file.exists());
   }
    
   /**
    * Test that the xml file exists , if it is created in a new directory.
    */
   @Test
   public void testWriteMetaData6() {
      mdw = new MetaDataWriter(iwc , "metadataDirectory");
      mdw.writeMetaData();
       
      File file = new File("metadataDirectory/metadata.xml");
       
      assertTrue(file.exists());
   }
    
  /**
   * Test that the xml file exists , if it is created in a new directory.
   */
   @Test
   public void testWriteMetaData7() {
      mdw.setFilePath("metadataDirectory2");
      mdw.writeMetaData();
       
      File file = new File("metadataDirectory2/metadata.xml");
       
      assertTrue(file.exists());
   }
}
