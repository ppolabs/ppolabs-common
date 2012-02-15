package com.ppolabs.mindbend.benchmark;

import java.util.concurrent.ExecutionException;

public interface Benchmark {

    public String getName();

    public BenchmarkResult run(int size, double seconds) throws ExecutionException, InterruptedException;

}
