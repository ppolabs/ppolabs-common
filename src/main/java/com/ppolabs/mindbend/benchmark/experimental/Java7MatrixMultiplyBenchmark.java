package com.ppolabs.mindbend.benchmark.experimental;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.experimental.strassen.Java7MatrixMultiply;

import java.util.concurrent.ExecutionException;

import static org.jblas.FloatMatrix.randn;

/**
 * User: pmar@ppolabs.com
 * Date: 2/15/12
 * Time: 10:55 AM
 */
public class Java7MatrixMultiplyBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "Matrix multiplication in Java7, method a la Strassen, SINGLE precision, Fork/Join";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) throws ExecutionException, InterruptedException {

        int counter = 0;
        long ops = 0;

        float[][] A = randn(size, size).toArray2();
        float[][] B = randn(size, size).toArray2();
        float[][] C = new float[size][size];

        Timer t = new Timer();
        t.start();
        while (!t.ranFor(seconds)) {

            Java7MatrixMultiply strassenMMul = new Java7MatrixMultiply();

            strassenMMul.setSize(size);
            strassenMMul.setA(A);
            strassenMMul.setB(B);
            strassenMMul.setC(C);
            strassenMMul.execute();

            counter++;
            ops += 2L * size * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
