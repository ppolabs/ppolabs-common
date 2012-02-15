package com.ppolabs.mindbend.benchmark.matrix;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.RandomMatrices;

import java.util.Random;

public class EJMLDoubleBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "EJML matrix multiplication, DOUBLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        Random rand = new Random();

        DenseMatrix64F A = RandomMatrices.createRandom(size, size, rand);
        DenseMatrix64F B = RandomMatrices.createRandom(size, size, rand);
        DenseMatrix64F C = new DenseMatrix64F(size, size);

        Timer t = new Timer();
        t.start();
        while (!t.ranFor(seconds)) {
            CommonOps.mult(A, B, C);
            counter++;
            ops += 2L * size * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
