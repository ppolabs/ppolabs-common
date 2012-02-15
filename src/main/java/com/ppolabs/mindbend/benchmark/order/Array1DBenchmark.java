package com.ppolabs.mindbend.benchmark.order;

import com.ppolabs.mindbend.benchmark.ArrayBenchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.util.ArrayDoubleMathUtils;

import static org.jblas.DoubleMatrix.randn;

public class Array1DBenchmark implements ArrayBenchmark {

    private static final String name = "1D Arrays multiplication test";
    private static final String[] orderMethods = {"IJK_1D"};

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getMethods() {
        return orderMethods;
    }

    @Override
    public BenchmarkResult run(int size, double seconds, String method) {

        int counter = 0;
        long ops = 0;
        double[] A = randn(size, size).data;
        double[] B = randn(size, size).data;
        double[] C = new double[size * size];

        Timer t;
        for (String orderMethod : orderMethods) {
            switch (orderMethod.toUpperCase()) {
                case "IJK_1D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_ijk_1D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                default:
                    throw new IllegalArgumentException("Unknown entry for ordering method parsed to run()");
            }
        }
        return null;
    }

}
