package oracle.interview.implementation;


import java.time.Instant;


import org.junit.jupiter.api.Test;

import oracle.interview.metrics.MetricWriter;
import oracle.interview.metrics.RandomlyFailingMetricStorage;
import oracle.interview.metrics.TargetMetricsContainer;

class MetricWriterImplementationTest {

	@Test
	void testWriteMetricsContainer() {
		MetricWriter writer = new MetricWriterImplementation(new RandomlyFailingMetricStorage());
		TargetMetricsContainer metricsContainer = new TargetMetricsContainer("red.oracle.co", "host");
		metricsContainer.addMetric("cpu", Instant.now(), 80);
		metricsContainer.addMetric("cpu", Instant.now(), 30);
		metricsContainer.addMetric("cpu", Instant.now(), 20);
		
		writer.writeMetricsContainer(metricsContainer);
	}
	
	

}
