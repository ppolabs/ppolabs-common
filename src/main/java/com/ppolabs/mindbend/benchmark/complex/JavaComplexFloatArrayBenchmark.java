package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;

import static org.jblas.FloatMatrix.randn;

public class JavaComplexFloatArrayBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "Java complex (element-wise) matrix multiplication, SINGLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        float[] realA = randn(size, size).data;
        float[] realB = randn(size, size).data;
        float[] realC = new float[size * size];

        float[] imagA = randn(size, size).data;
        float[] imagB = randn(size, size).data;
        float[] imagC = new float[size * size];

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
    private void mmuli_cplx(final int n, final ComplexArray A, final ComplexArray B, final ComplexArray C) {

        for (int j = 0; j < n; j++) {
            int jn = j * n;
            for (int k = 0; k < n; k++) {
                int kn = k * n;
                float b_kjn_real = B.real[k + jn];
                float b_kjn_imag = B.imag[k + jn];
                for (int i = 0; i < n; i++) {
                    
                    float a_real = A.real[i + kn];
                    float a_imag = A.imag[i + kn];
                    float ac = a_real * b_kjn_real;
                    float bd = a_imag * b_kjn_imag;
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
            float ac = A.real[i] * B.real[i];
            float bd = A.imag[i] * B.imag[i];
            C.real[i] = ac - bd;
            C.imag[i] = (A.real[i] + A.imag[i]) * (B.real[i] + B.imag[i]) - ac - bd;
        }
    }

    private ComplexArray muli_cplx_test(int n, final ComplexArray A, final ComplexArray B, final ComplexArray C) {
        // (a + ib)(c + id) = (ac - bd) + i[(a+b)(c+d) - ac - bd] : 3M rule doesn't play nice with JIT
        for (int i = 0; i < n * n; i++) {
            C.real[i] = A.real[i] * B.real[i] - A.imag[i] * B.imag[i];
            C.imag[i] = (A.real[i] * B.imag[i]) + (A.imag[i] * B.real[i]);
        }

        return C;
    }

    private class ComplexArray {

        float[] real;
        float[] imag;

        public ComplexArray(float[] real, float[] imag) {
            this.real = real;
            this.imag = imag;
        }

        public ComplexArray(int n, int m) {
            this.real = new float[n * m];
            this.imag = new float[n * m];
        }

    }
}