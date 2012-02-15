package com.ppolabs.mindbend.benchmark.order;

import com.ppolabs.mindbend.benchmark.ArrayBenchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.util.ArrayDoubleMathUtils;

import static org.jblas.DoubleMatrix.randn;

public class Array2DBenchmark implements ArrayBenchmark {

    private static final String name = "2D Arrays multiplication test";
    private final static String[] orderMethods = {"IJK_2D", "IKJ_2D", "JIK_2D", "GAXPY_2D", "KIJ_2D", "KJI_2D", "JAMA_2D", "IKJ_PURE_ROW_2D"};

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
        double[][] A = randn(size, size).toArray2();
        double[][] B = randn(size, size).toArray2();
        double[][] C = new double[size][size];

        Timer t;
        for (String orderMethod : orderMethods) {
            switch (orderMethod.toUpperCase()) {
                case "IJK_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_ijk_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "IKJ_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_ikj_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "JIK_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_jik_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "GAXPY_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_GAXPY_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "KIJ_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_kij_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "KJI_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_kji_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "JAMA_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_JAMA_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                case "IKJ_PURE_ROW_2D":
                    t = new Timer();
                    t.start();
                    while (!t.ranFor(seconds)) {
                        ArrayDoubleMathUtils.mmuli_ikj_PURE_ROW_2D(size, A, B, C);
                        counter++;
                        ops += 2L * size * size * size;
                    }
                    t.stop();
                    return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
                default:
                    throw new IllegalArgumentException("Unknown ordering method parsed");
            }
        }
        return null;
    }
}