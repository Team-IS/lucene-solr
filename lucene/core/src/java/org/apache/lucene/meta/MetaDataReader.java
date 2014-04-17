package org.apache.lucene.meta;

/**
 * This class is responsible for reading the xml file 
 * containing the metadata and checking if the same 
 * utils are being used for indexing and searching. 
 */
public class MetaDataReader {

	/**
	 * The analyzer used during indexing.
	 */
	private String indexAnalyzer;
	
	/**
	 * The version of Lucene that was used during indexing.
	 */
	private String indexVersion;
	
	/**
	 * The similarity function that was used during indexing.
	 */
	private String indexSimilarity;
	
	/**
	 * The analyzer being used for search.
	 */
	private String searchAnalyzer;
	
	/**
	 * The version of Lucene being used for search.
	 */
	private String searchVersion;
	
	/**
	 * The similarity function being used for search.
	 */
	private String searchSimilarity;
	
	/**
	 * The parser that will be used for reading the xml file.
	 */
	private MetaDataParser parser;
	
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
	 * Constructor
	 * 
	 * @param similarity A string representation of the
	 * similarity function being used for search.
	 * 
	 * @param filepath The path where the xml file is.
	 */
	public MetaDataReader(String similarity, String filepath) {
		this(similarity , "" , "" , filepath);
	}
	
	/**
	 * Constructor
	 * 
	 * @param analyzer A string representation of the
	 * analyzer being used for search.
	 * 
	 * @param version A string representation of the
	 * version being used for search.
	 * 
	 * @param filepath The path where the xml file is.
	 */
	public MetaDataReader(String analyzer, String version , String filepath) {
		this("" , analyzer , version, filepath);
	}
	
	/**
	 *  Constructor
	 *  
	 * @param similarity A string representation of the
	 * similarity function being used for search.
	 * 
	 * @param analyzer A string representation of the
	 * analyzer being used for search.
	 * 
	 * @param version A string representation of the
	 * version being used for search.
	 * 
	 * @param filepath The path where the xml file is.
	 */
	public MetaDataReader(String similarity , String analyzer , String version , String filepath) {
		this.searchSimilarity = similarity;
		this.searchAnalyzer = normalizeAnalyzer(analyzer);
		this.searchVersion = version;
		this.filePath = filepath;
	}
	
	/**
	 * Returns a String representation of the version
	 * used during indexing. 
	 */
	public String getIndexVersion() {
		return this.indexVersion;
	}
	
	/**
	 * Returns a String representation of the version
	 * used during searching. 
	 */
	public String getSearchVersion() {
		return this.searchVersion;
	}
	
	/**
	 * Returns a String representation of the similarity 
	 * function used during indexing.  
	 */
	public String getIndexSimilarity() {
		return this.indexSimilarity;
	}
	
	/**
	 * Returns a String representation of the similarity 
	 * function used during searching. 
	 */
	public String getSearchSimilarity() {
		return this.searchAnalyzer;
	}
	
	/**
	 * Returns a String representation of the Analyzer 
	 * used during indexing.  
	 */
	public String getIndexAnalyzer() {
		return this.indexAnalyzer;
	}
	
	/**
	 * Returns a String representation of the Analyzer 
	 * used during searching. 
	 */
	public String getSearchAnalyzer() {
		return this.searchAnalyzer;
	}
	/**
	 * Reads metadata information from the xml file.
	 */
	public void readMetaData() {
		this.parser = new MetaDataParser(filePath + fileName);
		
		String[] metadata = parser.parse();
		
		this.indexVersion = metadata[0];
		this.indexAnalyzer = normalizeAnalyzer(metadata[1]);
		this.indexSimilarity = metadata[2];
	}
	
	/**
	 * Checks if the same Lucene Version is being used 
	 * for indexing and searching.
	 * 
	 * @return true if same verion
	 * 		   false otherwise.
	 */
	public boolean usesSameVersion() {
		return this.indexVersion.equals(searchVersion);
	}
	
	/**
	 * Checks if the same analyzer is being used 
	 * for indexing and searching.
	 * 
	 * @return true if same analyzer
	 * 		   false otherwise.
	 */
	public boolean usesSameAnalyzer() {	
		return this.indexAnalyzer.equals(searchAnalyzer);
	}
	
	/**
	 * Checks if the same similarity function is being used 
	 * for indexing and searching.
	 * 
	 * @return true if same similarity function
	 * 		   false otherwise.
	 */
	public boolean usesSameSimilarity() {
		return this.indexSimilarity.equals(searchSimilarity);
	}
	
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
	 * @return the normalized analyzer.
	 */
	private String normalizeAnalyzer(String analyzer) {
		int index = analyzer.indexOf('@');
		
		return analyzer.substring(0, index);
	}
	
}