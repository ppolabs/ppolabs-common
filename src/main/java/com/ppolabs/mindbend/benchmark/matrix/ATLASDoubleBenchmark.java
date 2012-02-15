package com.ppolabs.mindbend.benchmark.matrix;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import org.jblas.DoubleMatrix;
import static org.jblas.DoubleMatrix.randn;

public class ATLASDoubleBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "ATLAS matrix multiplication, DOUBLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        DoubleMatrix A = randn(size, size);
        DoubleMatrix B = randn(size, size);
        DoubleMatrix C = randn(size, size);


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
