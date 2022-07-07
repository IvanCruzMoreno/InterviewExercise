package oracle.interview.metrics;

import oracle.interview.exceptions.RetryException;

public interface MetricWriter {

    void writeMetricsContainer(TargetMetricsContainer metricsContainer);
    void retry(TargetMetricsContainer metricsContainer, int totalTries) throws RetryException;
}
