package com.ppolabs.mindbend.experimental;

import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.random.Normal;
import org.ojalgo.random.RandomNumber;

public class OjAlgoSchool {

    RandomNumber normal = new Normal(1, 0.1);

    final PhysicalStore A = PrimitiveDenseStore.FACTORY.makeZero(10, 10);
    final PhysicalStore B = PrimitiveDenseStore.FACTORY.makeZero(10, 10);
    final PhysicalStore C = PrimitiveDenseStore.FACTORY.makeZero(10, 10);

    // how to work with complex data in ojAlgo:
    final PhysicalStore complexA = ComplexDenseStore.FACTORY.makeRandom(5, 5, normal);
    final PhysicalStore complexB = ComplexDenseStore.FACTORY.makeRandom(5, 5, normal);
    final PhysicalStore complexC = ComplexDenseStore.FACTORY.makeZero(5, 5);


    OjAlgoSchool() {

        C.fillByMultiplying(A,B);
        complexC.fillByMultiplying(complexA, complexB);

    }


}
