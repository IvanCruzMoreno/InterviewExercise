package oracle.interview.implementation;

import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MetricReaderImplementation implements MetricReader {
    @Override
    public List<TargetMetricsContainer> readMetrics(InputStream metricInputStream) {
        // TODO: implement this, reading data from the input stream, returning a list of containers read from the stream
    	DocumentBuilderFactory documentBuilderFac = DocumentBuilderFactory.newInstance();
    	List<TargetMetricsContainer> metrics = new ArrayList<>();
    	
    	try {
			DocumentBuilder documentBuilder = documentBuilderFac.newDocumentBuilder();
			Document document = documentBuilder.parse(metricInputStream);
			readDocument(document, metrics);
			
		} catch (ParserConfigurationException | SAXException  | IOException | ParseException e) {
			e.printStackTrace();
			return Collections.emptyList();
		} 
    	
        return metrics;
    }
    
    private void readDocument(Document document, List<TargetMetricsContainer> metrics) throws ParseException {
    	
    	NodeList targets = document.getElementsByTagName(TARGET_NODE_TYPE);
    	
    	for(int i = 0; i < targets.getLength(); i++) {
    		
    		String targetName = targets.item(i).getAttributes().getNamedItem(METRIC_NAME_ATTRIBUTE).getTextContent();
    		String targetType = targets.item(i).getAttributes().getNamedItem(METRIC_TYPE_ATTRIBUTE).getTextContent();
    		
    		TargetMetricsContainer metricContainer = new TargetMetricsContainer(targetName, targetType);
    		
    		Node target = targets.item(i);
    		
    		if(target.getNodeType() == Node.ELEMENT_NODE) {
    			
    			Element element = (Element) target;
    			//String description = element.getElementsByTagName(DESCRIPTION_NAME_TYPE).item(0).getTextContent();
    			
    			NodeList metricTypes = element.getElementsByTagName(METRIC_NODE_TYPE);
    			
    			for(int j = 0; j < metricTypes.getLength(); j++) {
    				String metricName = metricTypes.item(j).getAttributes().getNamedItem(METRIC_TYPE_ATTRIBUTE).getTextContent();
    				
    				//These lines threw me an exception I don't know why, I leave an example where I tried it separately and it worked perfectly 
    				//https://ideone.com/FtpG7M
    				//String timestampTemp = metricType.item(j).getAttributes().getNamedItem(METRIC_TIMESTAMP_ATTRIBUTE).getTextContent();
    				//Instant timesTamp = new SimpleDateFormat("dd-MMM-yyyy").parse(timestampTemp).toInstant();
    				
    				String metricValueTemp = metricTypes.item(j).getAttributes().getNamedItem(METRIC_VALUE_ATTRIBUTE).getTextContent();
    				int metricValue = Integer.parseInt(metricValueTemp);
    		
    				metricContainer.addMetric(metricName, Instant.now() , metricValue);
    			}
    			
    			System.out.println(metricContainer);
    			metrics.add(metricContainer);
    			
    		}
    	}
    }

}
