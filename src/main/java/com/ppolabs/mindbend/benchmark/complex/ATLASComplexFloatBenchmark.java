package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import org.jblas.ComplexFloatMatrix;

import static org.jblas.FloatMatrix.randn;

public class ATLASComplexFloatBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "ATLAS complex (element-wise) matrix multiplication, SINGLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        ComplexFloatMatrix A = new ComplexFloatMatrix(randn(size, size), randn(size, size));
        ComplexFloatMatrix B = new ComplexFloatMatrix(randn(size, size), randn(size, size));
        ComplexFloatMatrix C = new ComplexFloatMatrix(size, size);

        Timer t = new Timer();
        t.start();

        while (!t.ranFor(seconds)) {
            A.muli(B, C); // A.*B -> C
            counter++;
            ops += (2L * (4L * size * size * size) - 2L * size * size);
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
