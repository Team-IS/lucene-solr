package org.apache.lucene.meta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Version;

/**
 * This class is responsible for creating
 * and writing an xml meta-data file , 
 * that contains information about the 
 * utils(Analyzer , Similarity , Version) , 
 * that were used during indexing.
 *
 */
public class MetaDataWriter {

	/**
	 * An IndexWriterConfig object , that will be used
	 * in order to get the information about the utils
	 * that were used during index time.
	 */
	private IndexWriterConfig iwc;
	
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
		this(null , "/");
	}
	
	/**
	 * Overloaded Constructor.
	 * 
	 * @param iwc The IndexWriterConfig object that will be used.
	 */
	public MetaDataWriter(IndexWriterConfig iwc) {
		this(iwc , "");
	}
	
	/**
	 * Overloaded Constructor.
	 * 
	 * @param iwc The IndexWriterConfig object that will be used.
	 * @param path The path of the xml file.
	 */
	public MetaDataWriter(IndexWriterConfig iwc , String path) {
		this.iwc = iwc;
		this.filePath = path;
	}
	
	/**
	 * Sets the IndexWriterConfig object , to the one
	 * given as parameter.
	 * 
	 * @param iwc The new IndexWriterConfig object.
	 */
	public void setIwc(IndexWriterConfig iwc) {
		this.iwc = iwc;
	}
	
	/**
	 * Sets the path that the xml file will be created , 
	 * to the one given as parameter.
	 * 
	 * @param filePath The new path where the xml file will
	 * 			be created.
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
		Analyzer analyzer = iwc.getAnalyzer();
		
		//Get the similarity.
		Similarity similarity = iwc.getSimilarity();
		
		//Get the version.
		Version version = iwc.getVersion();
		
		//Create the final file path.
		String path = filePath+fileName;
		
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(path)));
			
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\n");
			
			bw.write("<indexinfo>\n\n");
			
			bw.write("\t<uses-version>\n");
			bw.write("\t\t" + version.toString() + "\n");
			bw.write("\t</uses-version>\n\n");
			
			bw.write("\t<uses-analyzer>\n");
			bw.write("\t\t" + analyzer.toString() + "\n");
			bw.write("\t</uses-analyzer>\n\n");
			
			bw.write("\t<uses-similarity>\n");
			bw.write("\t\t" + similarity.toString() + "\n");
			bw.write("\t</uses-similarity>\n\n");
			
			bw.write("</indexinfo>");
			
			bw.close();
		}
		
		catch(Exception e) {
			System.err.println("Exception occured while writing the xml metadata file : \n");
			e.printStackTrace();
		}
	}
}