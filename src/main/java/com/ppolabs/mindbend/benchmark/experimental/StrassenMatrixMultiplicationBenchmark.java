package com.ppolabs.mindbend.benchmark.experimental;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.experimental.strassen.StrassenMatrixMultiplication;

import static org.jblas.DoubleMatrix.randn;

/**
 * User: pmar@ppolabs.com
 * Date: 2/15/12
 * Time: 10:15 AM
 */
public class StrassenMatrixMultiplicationBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "Matrix multiplication a la Strassen, DOUBLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        double[][] A = randn(size, size).toArray2();
        double[][] B = randn(size, size).toArray2();
        double[][] C = new double[size][size];

        Timer t = new Timer();
        t.start();
        while (!t.ranFor(seconds)) {
            C = StrassenMatrixMultiplication.multiply(A, B);
            counter++;
            ops += 2L * size * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}