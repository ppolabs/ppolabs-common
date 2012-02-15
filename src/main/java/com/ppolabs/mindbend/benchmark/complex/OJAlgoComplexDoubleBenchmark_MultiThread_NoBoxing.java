package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.util.Constants;
import org.ojalgo.OjAlgoUtils;
import org.ojalgo.machine.VirtualMachine;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.random.Normal;
import org.ojalgo.random.RandomNumber;

public class OJAlgoComplexDoubleBenchmark_MultiThread_NoBoxing implements Benchmark {

    private final String name;
    private final VirtualMachine virtualMachine;

    public OJAlgoComplexDoubleBenchmark_MultiThread_NoBoxing() {
        this.name = "ojAlgo complex (element-wise) matrix multiplication (NoBoxing!), DOUBLE precision, multi-thread";
        this.virtualMachine = Constants.MACHINE_I7_860_MULTI_THREADED;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        OjAlgoUtils.ENVIRONMENT = this.virtualMachine;
        RandomNumber normal = new Normal(1, 0.1);

        int counter = 0;
        long ops = 0;

        PrimitiveMatrix realA = (PrimitiveMatrix) PrimitiveMatrix.FACTORY.makeRandom(size, size, normal);
        PrimitiveMatrix realB = (PrimitiveMatrix) PrimitiveMatrix.FACTORY.makeRandom(size, size, normal);
        PrimitiveMatrix imagA = (PrimitiveMatrix) PrimitiveMatrix.FACTORY.makeRandom(size, size, normal);
        PrimitiveMatrix imagB = (PrimitiveMatrix) PrimitiveMatrix.FACTORY.makeRandom(size, size, normal);

        PrimitiveDenseStore realC = null;
        PrimitiveDenseStore imagC = null;


        final Timer t = new Timer();
        t.start();

        while (!t.ranFor(seconds)) {

            realC = PrimitiveDenseStore.FACTORY.copy((PrimitiveMatrix) realA.multiplyElements(realB).subtract(imagA.multiplyElements(imagB)));
            imagC = PrimitiveDenseStore.FACTORY.copy((PrimitiveMatrix) realA.multiplyElements(imagB).subtract(imagA.multiplyElements(realB)));

            counter++;
            ops += 4L * size * size * size - 2L * size * size;

        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
