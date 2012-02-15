package com.ppolabs.mindbend.benchmark.experimental;

import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.Timer;
import com.ppolabs.mindbend.util.Constants;
import org.ojalgo.OjAlgoUtils;
import org.ojalgo.machine.VirtualMachine;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.random.Normal;
import org.ojalgo.random.RandomNumber;

public class OJAlgoDoubleBenchmark_AmdOpteron implements Benchmark {

    private final String name;
    private final VirtualMachine virtualMachine;

    public OJAlgoDoubleBenchmark_AmdOpteron() {
        this.name = "ojAlgo matrix multiplication, DOUBLE precision, Optimized for TU Delft's cluster";
        this.virtualMachine = Constants.MACHINE_AMD_Opteron_6128_32cores;
        // this.virtualMachine = Hardware.makeSimple().virtualise(); // constructs and checks whether hardware
                                                                     // matches any of PREDEFINED configs in Hardware()
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BenchmarkResult run(int size, double seconds) {

        int counter = 0;
        long ops = 0;

        RandomNumber normal = new Normal(1, 0.1);

        PhysicalStore A = PrimitiveDenseStore.FACTORY.makeRandom(size, size, normal);
        PhysicalStore B = PrimitiveDenseStore.FACTORY.makeRandom(size, size, normal);
        PhysicalStore C = PrimitiveDenseStore.FACTORY.makeZero(size, size);

        OjAlgoUtils.ENVIRONMENT = this.virtualMachine;

        Timer t = new Timer();
        t.start();

        while (!t.ranFor(seconds)) {
            C.fillByMultiplying(A, B);
            counter++;
            ops += 2L * size * size * size;
        }
        t.stop();

        return new BenchmarkResult(ops, t.elapsedSeconds(), counter);
    }
}
