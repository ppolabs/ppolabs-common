package com.ppolabs.mindbend.benchmark.matrix;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;

import static org.jblas.DoubleMatrix.randn;

public class JavaDoubleArrayBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "Java matrix multiplication, DOUBLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        double[] A = randn(size, size).data;
        double[] B = randn(size, size).data;
        double[] C = new double[size * size];

        Timer t = new Timer();
        t.start();
        while (!t.ranFor(seconds)) {
            mmuli(size, A, B, C);
            counter++;
            ops += 2L * size * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }

    // internal implementation of multiplication
    private void mmuli(int n, double[] A, double[] B, double[] C) {

        for (int i = 0; i < n * n; i++) {
            C[i] = 0;
        }

        for (int j = 0; j < n; j++) {
            int jn = j * n;
            for (int k = 0; k < n; k++) {
                int kn = k * n;
                double bkjn = B[k + jn];
                for (int i = 0; i < n; i++) {
                    C[i + jn] += A[i + kn] * bkjn;
                }
            }
        }
    }
}
