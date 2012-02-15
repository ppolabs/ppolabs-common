package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.RandomMatrices;

import java.util.Random;

public class EJMLComplexDoubleBenchmark implements Benchmark {

    @Override
    public String getName() {
        return "EJML complex (element-wise) matrix multiplication, DOUBLE precision";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        Random rand = new Random();
        DenseMatrix64F realA = RandomMatrices.createRandom(size, size, rand);
        DenseMatrix64F imagA = RandomMatrices.createRandom(size, size, rand);
        DenseMatrix64F realB = RandomMatrices.createRandom(size, size, rand);
        DenseMatrix64F imagB = RandomMatrices.createRandom(size, size, rand);

        ComplexEJML complexA = new ComplexEJML(realA, imagA);
        ComplexEJML complexB = new ComplexEJML(realB, imagB);
        ComplexEJML complexC = new ComplexEJML(size, size);

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

    private void muli_cplx(final int n, final ComplexEJML A, final ComplexEJML B, final ComplexEJML C) {
        for (int i = 0; i < n * n; i++) {
            // ------
            // C.real.data[i] = A.real.data[i] * B.real.data[i] - A.imag.data[i] * B.imag.data[i];
            // C.imag.data[i] = (A.real.data[i] * B.imag.data[i]) + (A.imag.data[i] * B.real.data[i]);
            // ------
            // getters setters are inlined better by JIT
            C.real.set(i, A.real.get(i) * B.real.get(i) - A.imag.get(i) * B.imag.get(i));
            C.imag.set(i, A.real.get(i) * B.imag.get(i) + A.imag.get(i) * B.real.get(i));
        }
    }

    private class ComplexEJML {

        DenseMatrix64F real;
        DenseMatrix64F imag;

        public ComplexEJML(double[][] real, double[][] imag) {
            this.real = new DenseMatrix64F(real);
            this.imag = new DenseMatrix64F(imag);
        }

        public ComplexEJML(DenseMatrix64F real, DenseMatrix64F imag) {
            this.real = real;
            this.imag = imag;
        }

        public ComplexEJML(int n, int m) {
            this.real = new DenseMatrix64F(n, m);
            this.imag = new DenseMatrix64F(n, m);
        }

    }
}