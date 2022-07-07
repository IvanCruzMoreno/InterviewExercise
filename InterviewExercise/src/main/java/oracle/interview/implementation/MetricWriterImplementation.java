package oracle.interview.implementation;

import java.sql.SQLException;

import oracle.interview.exceptions.RetryException;
import oracle.interview.metrics.MetricStorage;
import oracle.interview.metrics.MetricWriter;
import oracle.interview.metrics.TargetMetricsContainer;

public class MetricWriterImplementation implements MetricWriter {

    private final MetricStorage storage;
      
    public MetricWriterImplementation(MetricStorage storage) {
        this.storage = storage;
    }

    @Override
    public void writeMetricsContainer(TargetMetricsContainer metricsContainer) {
        // TODO: write this metricsContainer to the MetricStorage. Hint
        //      storage.write(metricsContainer);
        //  partially works.  Since the write could fail, retry the write on failure
        //  as appropriate.
    	int tries = 3;
    	
    	try {
			storage.write(metricsContainer);
		} catch (SQLException e) {
			try {
				retry(metricsContainer, tries);
			} catch (RetryException e1) {
				e1.getErrorMessage();
			}
		}

    }
    
    @Override
    public void retry(TargetMetricsContainer metricsContainer, int totalTries) throws RetryException {
    	
    	int retryCount = 0;
    	
    	while(retryCount < totalTries) {
    		try {
				storage.write(metricsContainer);
				return;
			} catch (SQLException e) {
				retryCount++;
			}
    	}
    	
    	throw new RetryException("error writing to database", retryCount);
    }
}
