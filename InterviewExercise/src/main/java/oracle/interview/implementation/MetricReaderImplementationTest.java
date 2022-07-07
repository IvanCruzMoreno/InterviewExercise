package oracle.interview.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import oracle.interview.metrics.Main;
import oracle.interview.metrics.MetricReader;
import oracle.interview.metrics.TargetMetricsContainer;


class MetricReaderImplementationTest {

	FileInputStream fileInputStream;
	
	public MetricReaderImplementationTest() throws FileNotFoundException {
		fileInputStream = new FileInputStream(Main.findFile("metrics_data.xml"));
	}
	
	@Test
	void readMetricsTest() throws ParserConfigurationException, IOException, SAXException {
		MetricReader reader = new MetricReaderImplementation();
		
		List<TargetMetricsContainer> metrics = reader.readMetrics(fileInputStream);
		
		assertEquals(3, metrics.size());
		
		TargetMetricsContainer targetMetricsContainer = metrics.get(0);
		assertEquals("red.oracle.com", targetMetricsContainer.getTargetName());
		assertEquals("host", targetMetricsContainer.getTargetType());
		
		Map<String,Object> metric = targetMetricsContainer.getPayload().get(0);
		assertEquals("cpu", metric.get("type"));
		assertEquals(80, metric.get("value"));
	}
	
}







