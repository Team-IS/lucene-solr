package org.apache.lucene.meta;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the parsing procedure.
 */
public class TestMetaDataParsing {
  
  /**
   * The relative path where the xml file exists.
   */
  private static final String path = "metadata.xml" ;
  
  /**
   * The MetaDataParser object that we will use for the parsing process.
   */
  private MetaDataParser mdp;
  
  /**
   * An array that holds the metadata returned from the parsing process.
   */
  private String[] metadata;
  
  
  @Before
  public void setUp() throws Exception {
    mdp = new MetaDataParser();
  }
  
  @After
  public void tearDown() throws Exception {
    mdp = null;
  }
  
  /**
   * Tests the behavior of the parse() method when the file path
   * is an empty String.
   */
  @Test
  public void testParse1() {
    metadata = mdp.parse();
    
    assertNull(metadata);
  }
  
  /**
   * Tests the behavior of the parse() method when specified file path is given.
   * Checks if String array is not null and if the results are as expected.
   */
  @Test
  public void testParse2() {
    mdp = new MetaDataParser(path);
    metadata = mdp.parse();
        
    assertNotNull(metadata);
    assertEquals("LUCENE_50" , metadata[0]);
    assertEquals("org.apache.lucene.analysis.en.EnglishAnalyzer" , metadata[1]);
    assertEquals("DefaultSimilarity" , metadata[2]);
  }
}
