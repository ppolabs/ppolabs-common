package com.ppolabs.mindbend.benchmark.complex;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.util.Constants;
import org.ojalgo.OjAlgoUtils;
import org.ojalgo.machine.VirtualMachine;
import org.ojalgo.matrix.ComplexMatrix;
import org.ojalgo.matrix.MatrixUtils;
import org.ojalgo.matrix.store.ComplexDenseStore;

public class OJAlgoComplexDoubleBenchmark_MultiThread implements Benchmark {

    private final String name;
    private final VirtualMachine virtualMachine;

    public OJAlgoComplexDoubleBenchmark_MultiThread() {
        this.name = "ojAlgo complex (element-wise) matrix multiplication, DOUBLE precision, multi-threaded";
        this.virtualMachine = Constants.MACHINE_I7_860_MULTI_THREADED;
        // this.virtualMachine = Hardware.makeSimple().virtualise(); // constructs and checks whether hardware
                                                                     // matches any of PREDEFINED configs in Hardware()
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        // setup virtual machine
        OjAlgoUtils.ENVIRONMENT = this.virtualMachine;
//        RandomNumber normal = new Normal(1, 0.1);

        int counter = 0;
        long ops = 0;

        final ComplexMatrix A = (ComplexMatrix) ComplexMatrix.FACTORY.copy(MatrixUtils.makeRandomComplexStore(size, size));
        final ComplexMatrix B = (ComplexMatrix) ComplexMatrix.FACTORY.copy(MatrixUtils.makeRandomComplexStore(size, size)); 
        ComplexDenseStore C = null;

        Timer t = new Timer();
        t.start();

        while (!t.ranFor(seconds)) {
            C = ComplexDenseStore.FACTORY.copy((ComplexMatrix) A.multiplyElements(B));
            counter++;
            ops += 4L * size * size * size - 2L * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
