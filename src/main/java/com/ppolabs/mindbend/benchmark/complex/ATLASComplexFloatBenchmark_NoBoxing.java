package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import org.jblas.FloatMatrix;

import static org.jblas.FloatMatrix.randn;
import static org.jblas.FloatMatrix.zeros;

public class ATLASComplexFloatBenchmark_NoBoxing implements Benchmark {

    @Override
    public String getName() {
        return "ATLAS complex (element-wise) matrix multiplication, SINGLE precision [No Boxing]";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        // input matrix: A
        FloatMatrix realA = randn(size, size);
        FloatMatrix imagA = randn(size, size);

        // input matrix: B
        FloatMatrix realB = randn(size, size);
        FloatMatrix imagB = randn(size, size);

        // new matrix: C
        FloatMatrix realC = zeros(size, size);
        FloatMatrix imagC = zeros(size, size);

        Timer t = new Timer();
        t.start();

        while (!t.ranFor(seconds)) {

            // real part
            (realA.mul(realB)).subi(imagA.mul(imagB), realC);

            // imag part
            (realA.mul(imagB)).addi(imagA.mul(realB), imagC);

            counter++;
            ops += 4L * size * size * size - 2L * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
