package com.ppolabs.mindbend.benchmark;

public class BenchmarkResult {
    
    long numOps;
    double duration;
    int iterations;

    public BenchmarkResult(long numOps, double duration, int iterations) {
        this.numOps = numOps;
        this.duration = duration;
        this.iterations = iterations;
    }


    // print result assuming number of operations for matrix multiplication
    public void printResult() {
        System.out.printf("%6.3f GFLOPS (%d iterations in %.1f seconds)%n",
                numOps / duration / 1e9,
                iterations,
                duration);
    }

}
