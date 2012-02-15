package com.ppolabs.mindbend.benchmark;

/**
 * User: pmar@ppolabs.com
 * Date: 2/8/12
 * Time: 7:33 PM
 */
public interface ArrayBenchmark {

    public String getName();
    public String[] getMethods();
    public BenchmarkResult run(int size, double seconds, String method);

}
