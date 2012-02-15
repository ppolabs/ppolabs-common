package com.ppolabs.mindbend.benchmark.matrix;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import org.jblas.FloatMatrix;
import static org.jblas.FloatMatrix.randn;

public class ATLASFloatBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "ATLAS matrix multiplication, float precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        FloatMatrix A = randn(size, size);
        FloatMatrix B = randn(size, size);
        FloatMatrix C = randn(size, size);

        Timer t = new Timer();
        t.start();

        while (!t.ranFor(seconds)) {
            A.mmuli(B, C);
            counter++;
            ops += 2L * size * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
