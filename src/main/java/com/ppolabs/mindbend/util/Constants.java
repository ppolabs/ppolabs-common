package com.ppolabs.mindbend.util;

import org.ojalgo.machine.BasicMachine;
import org.ojalgo.machine.VirtualMachine;

public class Constants {

    public static final String X86_64 = "x86_64";
    public static final String X86 = "x86";

    public static final int INT_0000 = 0;
    public static final int INT_0001 = 1;
    public static final int INT_0002 = 2;
    public static final int INT_0003 = 3;
    public static final int INT_0004 = 4;
    public static final int INT_0007 = 7;
    public static final int INT_0008 = 8;
    public static final int INT_0016 = 16;
    public static final int INT_0031 = 31;
    public static final int INT_0032 = 32;
    public static final int INT_1024 = 1024;
    public static final long LONG_0000 = 0L;
    public static final long LONG_0001 = 1L;
    public static final long LONG_0002 = 2L;
    public static final long LONG_0003 = 3L;
    public static final long LONG_0004 = 4L;
    public static final long LONG_0005 = 5L;
    public static final long LONG_0006 = 6L;
    public static final long LONG_0007 = 7L;
    public static final long LONG_0008 = 8L;
    public static final long LONG_0012 = 12L;
    public static final long LONG_0016 = 16L;
    public static final long LONG_0032 = 32L;
    public static final long LONG_0128 = 128L;
    public static final long LONG_0256 = 256L;
    public static final long LONG_0512 = 512L;
    public static final long LONG_1024 = 1024L;

    // Settings for multi and single threaded processing on Intel Core i7
    // --------------------------------------------------------------------
    //      $ cat /proc/cpuinfo : Intel(R) Core(TM) i7 CPU 860 @ 2.80GHz
    //      $ cat /proc/meminfo : MemTotal:        8179708 kB

    private static final long I7_860_MEMORY = LONG_0008 * LONG_1024 * LONG_1024 * LONG_1024;
    private static final long I7_860_L1 = LONG_0032 * LONG_1024;
    private static final long I7_860_L2 = LONG_0256 * LONG_1024;
    private static final long I7_860_L3 = LONG_0008 * LONG_1024 * LONG_1024;
    private static final int I7_860_CORES = INT_0008;
    private static final int I7_860_UNITS = INT_0002;

    // VirtualMachine() works only with forked version of ojAlgo (constructor modifier from private -> public)
    public static final VirtualMachine MACHINE_I7_860_MULTI_THREADED = new VirtualMachine(X86_64,
            new BasicMachine[]{
                    new BasicMachine(I7_860_MEMORY, I7_860_CORES),
                    new BasicMachine(I7_860_L3, I7_860_CORES),
                    new BasicMachine(I7_860_L2, Constants.I7_860_UNITS),
                    new BasicMachine(I7_860_L1, Constants.I7_860_UNITS)
            });

    public static final VirtualMachine MACHINE_I7_860_SINGLE_THREADED = new VirtualMachine(X86_64,
            new BasicMachine[]{
                    new BasicMachine(I7_860_MEMORY, INT_0001),
                    new BasicMachine(I7_860_L3, INT_0001),
                    new BasicMachine(I7_860_L2, INT_0001),
                    new BasicMachine(I7_860_L1, INT_0001)
            });


    // Settings for multi-threaded processing on Quad-Core AMD Opteron 6128
    // --------------------------------------------------------------------
    //      $ cat /proc/cpuinfo : AMD Opteron(tm) Processor 6128, 32 processors
    //      $ cat /proc/meminfo : MemTotal:        132292604 kB

    private static final long AMD_Opteron_6128_32cores_MEMORY = LONG_0128 * LONG_1024 * LONG_1024 * LONG_1024;
    private static final long AMD_Opteron_6128_32cores_L1 = LONG_0128 * LONG_1024;
    private static final long AMD_Opteron_6128_32cores_L2 = LONG_0008 * LONG_1024 * LONG_0512;
    private static final long AMD_Opteron_6128_32cores_L3 = LONG_0012 * LONG_1024 * LONG_1024;
    private static final int AMD_Opteron_6128_32cores_CORES = INT_0032;
    private static final int AMD_Opteron_6128_32cores_UNITS = INT_0004;

    // VirtualMachine() works only with forked version of ojAlgo (constructor modifier from private -> public)
    public static final VirtualMachine MACHINE_AMD_Opteron_6128_32cores = new VirtualMachine(X86_64,
            new BasicMachine[]{
                    new BasicMachine(AMD_Opteron_6128_32cores_MEMORY, AMD_Opteron_6128_32cores_CORES),
                    new BasicMachine(AMD_Opteron_6128_32cores_L3, AMD_Opteron_6128_32cores_CORES),
                    new BasicMachine(AMD_Opteron_6128_32cores_L2, AMD_Opteron_6128_32cores_UNITS),
                    new BasicMachine(AMD_Opteron_6128_32cores_L1, AMD_Opteron_6128_32cores_UNITS)
            });


}
