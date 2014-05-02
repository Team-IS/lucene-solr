package org.apache.lucene.meta;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the reading process of the xml file.
 */
public class TestMetaDataReader {
  
  /**
   * The MetaDataReader object that we will used.
   */
  private MetaDataReader mdr;
  
  @Before
  public void setUp() throws Exception {
    mdr = new MetaDataReader("DefaultSimilarity", "org.apache.lucene.analysis.en.EnglishAnalyzer",
         "LUCENE_50", "metadataDirectory");
  }
  
  @After
  public void tearDown() throws Exception {
    mdr = null;
  }
  
  /**
   * Test the behavior of the normalizeAnalyzer method , 
   * in case that an input string contains the @.
   */
  @Test
  public void testNormalizeAnalyzer1() {
   String an = "EnglishAnalyzer@123456" ;
   
   String expected = "EnglishAnalyzer" ;
   
   String normalized = mdr.normalizeAnalyzer(an);
   
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
   
   String normalized = mdr.normalizeAnalyzer(an);
   
   assertEquals(expected, normalized);
  }
  
  /**
   * Tests the behavior of the two different getSimilarity methods.
   */
  @Test
  public void testGetSimilarity() {
    mdr = new MetaDataReader("BM25Similarity(k1=0.5 , k2=0.5", "metadataDirectory");
    mdr.readMetaData();
    
    assertEquals("DefaultSimilarity", mdr.getIndexSimilarity());
    assertEquals("BM25Similarity(k1=0.5 , k2=0.5", mdr.getSearchSimilarity());
  }
  
  /**
   * Tests the behavior of the two different getAnalyzer methods.
   */
  @Test
  public void testGetAnalyzer() {
    mdr = new MetaDataReader("org.apache.lucene.analysis.gr.GreekAnalyzer", 
        "LUCENE_46", "");
    mdr.readMetaData();
    
    assertEquals("org.apache.lucene.analysis.en.EnglishAnalyzer", mdr.getIndexAnalyzer());
    assertEquals("org.apache.lucene.analysis.gr.GreekAnalyzer", mdr.getSearchAnalyzer());
  }
  
  /**
   * Tests the behavior of the two different getVersion methods.
   */
  @Test
  public void testGetVersion() {
    mdr.readMetaData();
    
    assertEquals("LUCENE_50", mdr.getIndexVersion());
    assertEquals("LUCENE_50", mdr.getSearchVersion());
  }
  
  /**
   * Tests the behavior of the usesSameVersion() method when
   * the same versions are used during index and search.
   */
  @Test
  public void testUsesSameVersion1() {
    mdr.readMetaData();
    
    assertTrue(mdr.usesSameVersion()); 
  }
  
  /**
   * Tests the behavior of the usesSameVersion() method when
   * the different versions are used during index and search.
   */
  @Test
  public void testUsesSameVersion2() {
    mdr = new MetaDataReader("org.apache.lucene.analysis.gr.GreekAnalyzer", 
        "LUCENE_46", "");
    mdr.readMetaData();
    
    assertFalse(mdr.usesSameVersion()); 
  }
  
  /**
   * Tests the behavior of the usesSameAnalyzer() method when
   * the same analyzers are used during index and search.
   */
  @Test
  public void testUsesSameAnalyzer1() {
    mdr.readMetaData();
    
    assertTrue(mdr.usesSameAnalyzer()); 
  }
  
  /**
   * Tests the behavior of the usesSameAnalyzer() method when
   * the different analyzers are used during index and search.
   */
  @Test
  public void testUsesSameAnalyzer2() {
    mdr = new MetaDataReader("org.apache.lucene.analysis.gr.GreekAnalyzer", 
        "LUCENE_46", "");
    mdr.readMetaData();
    
    assertFalse(mdr.usesSameAnalyzer()); 
  }
  
  /**
   * Tests the behavior of the usesSameSimilarity() method when
   * the same similarity functions are used during index and search.
   */
  @Test
  public void testUsesSameSimilarity1() {
    mdr.readMetaData();
    
    assertTrue(mdr.usesSameSimilarity()); 
  }
  
  /**
   * Tests the behavior of the usesSameSimilarity() method when
   * the different similarity functions are used during index and search.
   */
  @Test
  public void testUsesSameSimilarity2() {
    mdr = new MetaDataReader("BM25Similarity(k1=0.5 , k2=0.5", "metadataDirectory");
    mdr.readMetaData();
    
    assertFalse(mdr.usesSameSimilarity()); 
  }
}
