package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;

import static org.jblas.DoubleMatrix.randn;

public class JavaComplexDoubleArrayBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "Java complex (element-wise) matrix multiplication, DOUBLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        double[] realA = randn(size, size).data;
        double[] realB = randn(size, size).data;
        double[] realC = new double[size * size];

        double[] imagA = randn(size, size).data;
        double[] imagB = randn(size, size).data;
        double[] imagC = new double[size * size];

        ComplexArray complexA = new ComplexArray(realA, imagA);
        ComplexArray complexB = new ComplexArray(realB, imagB);
        ComplexArray complexC = new ComplexArray(realC, imagC);


        Timer t = new Timer();
        t.start();
        while (!t.ranFor(seconds)) {
            muli_cplx(size, complexA, complexB, complexC);
            counter++;
            ops += (2L * (4L * size * size * size) - 2L * size * size);
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);


    }

    // internal implementation of multiplication: with complex wrapper class
    private void mmuli_cplx(int n, ComplexArray A, ComplexArray B, ComplexArray C) {

        for (int j = 0; j < n; j++) {
            int jn = j * n;
            for (int k = 0; k < n; k++) {
                int kn = k * n;
                double b_kjn_real = B.real[k + jn];
                double b_kjn_imag = B.imag[k + jn];
                for (int i = 0; i < n; i++) {

                    double a_real = A.real[i + kn];
                    double a_imag = A.imag[i + kn];
                    double ac = a_real * b_kjn_real;
                    double bd = a_imag * b_kjn_imag;
                    // JIT should inline these even without any optimization
                    C.real[i + jn] = (ac - bd);
                    C.imag[i + jn] = (a_real + a_imag) * (b_kjn_real + b_kjn_imag) - ac - bd;
//                    C.imag[i + jn] = (a_real * b_kjn_imag + a_imag * b_kjn_real);
//                    C.imag[i + jn] = (a_real + a_imag) * (b_kjn_real + b_kjn_imag) - a_real * b_kjn_real - a_imag * b_kjn_imag;
                }
            }
        }
    }

    private void muli_cplx(final int n, final ComplexArray A, final ComplexArray B, final ComplexArray C) {
        for (int i = 0; i < n * n; i++) {
            C.real[i] = A.real[i] * B.real[i] - A.imag[i] * B.imag[i];
            C.imag[i] = (A.real[i] * B.imag[i]) + (A.imag[i] * B.real[i]);
        }
    }

    private void muli_cplx_3M(final int n, final ComplexArray A, final ComplexArray B, final ComplexArray C) {
        // (a + ib)(c + id) = (ac - bd) + i[(a+b)(c+d) - ac - bd] : 3M rule doesn't play nice with JIT
        for (int i = 0; i < n * n; i++) {
            double ac = A.real[i] * B.real[i];
            double bd = A.imag[i] * B.imag[i];
            C.real[i] = ac - bd;
            C.imag[i] = (A.real[i] + A.imag[i]) * (B.real[i] + B.imag[i]) - ac - bd;
        }
    }

    private ComplexArray muli_cplx_test(final int n, final ComplexArray A, final ComplexArray B, final ComplexArray C) {
        // (a + ib)(c + id) = (ac - bd) + i[(a+b)(c+d) - ac - bd]
        for (int i = 0; i < n * n; i++) {
            C.real[i] = A.real[i] * B.real[i] - A.imag[i] * B.imag[i];
            C.imag[i] = (A.real[i] * B.imag[i]) + (A.imag[i] * B.real[i]);
        }
        return C;
    }

    private class ComplexArray {

        double[] real;
        double[] imag;

        public ComplexArray(double[] real, double[] imag) {
            this.real = real;
            this.imag = imag;
        }

        public ComplexArray(int n, int m) {
            this.real = new double[n * m];
            this.imag = new double[n * m];
        }

    }
}