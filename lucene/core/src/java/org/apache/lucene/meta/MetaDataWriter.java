package org.apache.lucene.meta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.LiveIndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Version;

/**
 * This class is responsible for creating
 * and writing an xml meta-data file , 
 * that contains information about the 
 * utils(Analyzer , Similarity , Version) , 
 * that were used during indexing.
 */
public class MetaDataWriter implements StringNormalizer {

   /**
    * An LiveIndexWriterConfig object , that will be used
    * in order to get the information about the utils
    * that were used during index time.
    */
   private LiveIndexWriterConfig liwc;
   
   /**
    * The path of the xml file that will be created and 
    * will hold the information. 
    */
   private String filePath;
   
   /**
    * The name of the xml file that will keep the 
    * meta-data information.
    */
   private static final String fileName = "metadata.xml";
   
   /**
    * Default constructor.
    */
   public MetaDataWriter() {
      this(null , "");
   }
   
   /**
    * Overloaded Constructor.
    * 
    * @param liwc The LiveIndexWriterConfig object that will be used.
    */
   public MetaDataWriter(LiveIndexWriterConfig liwc) {
      this(liwc , "");
   }
   
   /**
    * Overloaded Constructor.
    * 
    * @param liwc The LiveIndexWriterConfig object that will be used.
    * @param path The path of the xml file.
    */
   public MetaDataWriter(LiveIndexWriterConfig liwc , String path) {
      this.liwc = liwc;
      this.filePath = path;
   }
   
   /**
    * Sets the LiveIndexWriterConfig object , to the one
    * given as parameter.
    * 
    * @param liwc The new LiveIndexWriterConfig object.
    */
   public void setIwc(LiveIndexWriterConfig liwc) {
      this.liwc = liwc;
   }
   
   /**
    * Sets the path that the xml file will be created , 
    * to the one given as parameter.
    * 
    * @param filePath The new path where the xml file will
    *          be created.
    */
   public void setFilePath(String filePath) {
      this.filePath = filePath;
   }
   
   /**
    * Writes the metadata that were used during indexing
    * to an xml file in the path given.
    */
   public void writeMetaData() {
      
      //Get the analyzer.
      Analyzer analyzer = liwc.getAnalyzer();
      
      //Get the similarity.
      Similarity similarity = liwc.getSimilarity();
      
      //Get the version.
      Version version = liwc.getVersion();
      
      //Create the final file path.
      String path = (filePath.equals(""))?fileName:filePath + "/" + fileName;
      
      File xmlFolder = null;
      File xmlFile = null;
      BufferedWriter bw = null;
      
      try {
         
         //If the metadata file path does not exist , create it.
         xmlFolder = new File(filePath);
         
         if(!(xmlFolder.exists())) xmlFolder.mkdir();
         
         xmlFile = new File(path);
         
         //If the xml file allready exists , delete it.
         if(xmlFile.exists()) {
             xmlFile.delete();
         }
         
         //Create the xml file.
         xmlFile.createNewFile();
         
         bw = new BufferedWriter(new FileWriter(new File(path)));
         
         bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\n");
         
         bw.write("<indexinfo>\n\n");
         
         bw.write("\t<uses-version>\n");
         bw.write("\t\t" + version.toString() + "\n");
         bw.write("\t</uses-version>\n\n");
         
         bw.write("\t<uses-analyzer>\n");
         bw.write("\t\t" + this.normalizeAnalyzer(analyzer.toString()) + "\n");
         bw.write("\t</uses-analyzer>\n\n");
      
         bw.write("\t<uses-similarity>\n");
         bw.write("\t\t" + similarity.toString() + "\n");
         bw.write("\t</uses-similarity>\n\n");
         
         bw.write("</indexinfo>");
         
         //Mark the xml metadata file as read-only.
         xmlFile.setReadOnly();
         
         bw.close();
      }
      
      catch(Exception e) {
         System.err.println("Exception occured while writing the xml metadata file : \n");
         e.printStackTrace();
      }
   }
   
   @Override
   public String normalizeAnalyzer(String analyzer) {
      int index = analyzer.indexOf('@');
      
      if (index < 0) {
         return analyzer;
      }
      
      else {
         return analyzer.substring(0, index);
      }
   }
}
