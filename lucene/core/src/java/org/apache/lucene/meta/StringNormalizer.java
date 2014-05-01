package org.apache.lucene.meta;

/**
 * This interface will keep all the necessary
 * functions that we need in order to normalize
 * the string representations of the utils that 
 * we are going to store in the xml file.  
 */
public interface StringNormalizer {

   /**
    * The analyzer class and all of its subclasses does
    * not contain a custom toString() method. So every 
    * time we call the toString method, on an analyzer 
    * object, the result is a String that ends with a 
    * character '@' followed by a hexadecimal value.
    * 
    * This method will normalize the analyzer returned 
    * by removing the part that starts with '@' . 
    * 
    * @param analyzer The analyzer to be normalized.
    * 
    * @return the normalized analyzer.
    */
   public String normalizeAnalyzer(String analyzer);
   
}
