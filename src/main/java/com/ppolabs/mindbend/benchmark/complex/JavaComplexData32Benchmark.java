package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.wrappers.Data32F;
import com.ppolabs.mindbend.benchmark.Timer;

import java.util.Random;

public class JavaComplexData32Benchmark implements Benchmark {

    @Override
    public String getName() {
        return "Java complex (element-wise) matrix multiplication, SINGLE precision, BOXED in DATA32F";
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        Data32F realA = new Data32F(size, size);
        Data32F imagA = new Data32F(size, size);
        Data32F realB = new Data32F(size, size);
        Data32F imagB = new Data32F(size, size);

        Random rand = new Random();
        setRandom(realA, 0, 1, rand);
        setRandom(imagA, 0, 1, rand);
        setRandom(realB, 0, 1, rand);
        setRandom(imagB, 0, 1, rand);

        ComplexData32F complexA = new ComplexData32F(realA, imagA);
        ComplexData32F complexB = new ComplexData32F(realB, imagB);
        ComplexData32F complexC = new ComplexData32F(size, size);

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

    private void muli_cplx(final int n, final ComplexData32F a, final ComplexData32F b, final ComplexData32F c) {
        for (int i = 0; i < n * n; i++) {
            c.real.set(i, a.real.get(i) * b.real.get(i) - a.imag.get(i) * b.imag.get(i));
            c.imag.set(i, a.real.get(i) * b.imag.get(i) + a.imag.get(i) * b.real.get(i));
        }
    }

    private class ComplexData32F {

        Data32F real;
        Data32F imag;

        public ComplexData32F(Data32F real, Data32F imag) {
            this.real = real;
            this.imag = imag;
        }

        public ComplexData32F(float[][] real, float[][] imag) {
            this.real = new Data32F(real);
            this.imag = new Data32F(imag);
        }

        public ComplexData32F(int n, int m) {
            this.real = new Data32F(n * m);
            this.imag = new Data32F(n * m);
        }

    }

    public static void setRandom(Data32F mat, float min, float max, Random rand) {
        float d[] = mat.getData();
        int size = mat.getNumElements();

        float r = max - min;

        for (int i = 0; i < size; i++) {
            d[i] = r * (float)rand.nextDouble() + min;
        }

    }


}