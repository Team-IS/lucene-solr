package org.apache.lucene.meta;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MetaDataParser {
	
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
        
			String finalPath = this.filePath + fileName;
			//Parse xml file
			saxParser.parse(finalPath, handler);
			
			return handler.getMetaData();
		}
		
		catch(Exception e) {
			System.err.println("Exception occured while parsing the xml metadata file : \n");
			e.printStackTrace();
		}
		
		return null;
	}
}
