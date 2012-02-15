package com.ppolabs.mindbend;

import com.ppolabs.mindbend.benchmark.ArrayBenchmark;
import com.ppolabs.mindbend.benchmark.Benchmark;
import com.ppolabs.mindbend.benchmark.BenchmarkResult;
import com.ppolabs.mindbend.benchmark.complex.*;
import com.ppolabs.mindbend.benchmark.experimental.Java7MatrixMultiplyBenchmark;
import com.ppolabs.mindbend.benchmark.matrix.*;
import com.ppolabs.mindbend.benchmark.order.Array1DBenchmark;
import com.ppolabs.mindbend.benchmark.order.Array2DBenchmark;
import com.ppolabs.mindbend.util.Logger;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;

public class App {

    private static final int[] multiplicationSizes = {32, 64, 128, 256, 512, 1024, 2048};
    private static final double seconds = 3.0;
    private static final PrintStream out = System.out;

    private static boolean skipLoops = false;
    private static boolean skipJava = false;
    private static boolean unrecognizedOptions = false;
    private static boolean skipJblasSanity = false;
    private static boolean skipMatrix = false;
    private static boolean skipComplex = false;
    private static boolean doExperimental = false;

    static Benchmark[] multiplicationBenchmarks = {
            new JavaFloatArrayBenchmark(),
            new JavaDoubleArrayBenchmark(),
            new EJMLDoubleBenchmark(),
            new ATLASDoubleBenchmark(),
            new ATLASFloatBenchmark(),
            new OJAlgoDoubleBenchmark_MultiThread(),
            new OJAlgoDoubleBenchmark_SingleThread()
    };

    static Benchmark[] experimentalBenchmarks = {
//            new OJAlgoDoubleBenchmark_AmdOpteron(),
//            new OJAlgoDoubleBenchmark_MultiThread(),
//            new StrassenMatrixMultiplicationBenchmark(),
//            new StrassenMatrixMultiplicationBenchmark_ForkJoin(),
            new Java7MatrixMultiplyBenchmark(),
    };

    static ArrayBenchmark[] arrayBenchmarks = {
            new Array1DBenchmark(),
            new Array2DBenchmark()};

    static Benchmark[] complexMultiplicationBenchmarks = {
            new JavaComplexDoubleArrayBenchmark(),
            new JavaComplexFloatArrayBenchmark(),
            new EJMLComplexDoubleBenchmark(),
            new ATLASComplexDoubleBenchmark(),
            new ATLASComplexFloatBenchmark(),
            new ATLASComplexFloatBenchmark_NoBoxing(),
            new OJAlgoComplexDoubleBenchmark_MultiThread(),
            new OJAlgoComplexDoubleBenchmark_MultiThread_NoBoxing(),
    };

    public static void printHelp() {
        System.out.printf("Usage: benchmark [opts]%n"
                + "%n"
                + "with options:%n"
                + "%n"
                + "  --jblas-arch-flavor=value     overriding arch flavor (e.g. --arch-flavor=sse2)%n"
                + "  --skip-jblas-sanity-checks    don't run jblas sanity checks%n"
                + "  --skip-matrix                 don't run matrix benchmarks%n"
                + "  --skip-java                   don't run java benchmarks%n"
                + "  --skip-loops                  don't run java array order benchmarks%n"
                + "  --skip-loops                  don't run complex operations benchmarks%n"
                + "  --do-experimental             run experimental benchmarks%n"
                + "  --help                        show this help%n"
                + "  --debug                       set config levels to debug%n"
                + "%n"
                + "Recommended VM options (on Linux64): -server -XX:+UseNUMA -XX:+UseCondCardMark -Xms1024M -Xmx2048M -Xss1M -XX:MaxPermSize=128m -XX:+UseParallelGC%n");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (String arg : args) {
            if (arg.startsWith("--")) {
                int i = arg.indexOf('=');
                String value = null;
                if (i != -1) {
                    value = arg.substring(i + 1);
                    arg = arg.substring(0, i);
                }

/*
                // JDK 6 compatible
                if (arg.equals("--jblas-arch-flavor")) {
                    Logger.getLogger().info("Setting jblas-arch flavor to " + value);
                    org.jblas.util.ArchFlavor.overrideArchFlavor(value);
                } else if (arg.equals("--skip-java")) {
                    skipJava = true;
                } else if (arg.equals("--skip-loops")) {
                    skipLoops = true;
                } else if (arg.equals("--skip-jblas-sanity-checks")) {
                    skipJblasSanity = true;
                } else if (arg.equals("--help")) {
                    printHelp();
                    return;
                } else if (arg.equals("--debug")) {
                    Logger.getLogger().setLevel(Logger.DEBUG);
                } else {
                    Logger.getLogger().warning("Unrecognized option \"" + arg + "\"");
                    unrecognizedOptions = true;
                }
*/
                // JDK 7 version
                switch (arg.toLowerCase()) {
                    case "--jblas-arch-flavor":
                        Logger.getLogger().info("Setting jblas-arch flavor to " + value);
                        org.jblas.util.ArchFlavor.overrideArchFlavor(value);
                        break;
                    case "--skip-java":
                        skipJava = true;
                        break;
                    case "--skip-loops":
                        skipLoops = true;
                        break;
                    case "--skip-matrix":
                        skipMatrix = true;
                        break;
                    case "--skip-complex":
                        skipComplex = true;
                        break;
                    case "--skip-jblas-sanity-checks":
                        skipJblasSanity = true;
                        break;
                    case "--do-experimental":
                        doExperimental = true;
                        break;
                    case "--help":
                        printHelp();
                        return;
                    case "--debug":
                        Logger.getLogger().setLevel(Logger.DEBUG);
                        break;
                    default:
                        Logger.getLogger().warning("Unrecognized option \"" + arg + "\"");
                        unrecognizedOptions = true;
                        break;
                }
            }
        }

        if (unrecognizedOptions) {
            return;
        }

        if (!skipLoops) {
            loopBenchmark();
        }

        if (!skipJblasSanity) {
            jblasSanityChecks(args);
        }

        if (!skipMatrix) {
            matrixBenchmark();
        }

        if (!skipComplex) {
            complexMatrixBenchmark();
        }

        if (doExperimental) {
            experimentalBenchmark();
        }

    }

    private static void jblasSanityChecks(String[] args) {
        out.println("Running sanity checks for jblas.");
        out.println();
        org.jblas.util.SanityChecks.main(args);
        out.println();
    }

    private static void matrixBenchmark() throws ExecutionException, InterruptedException {

        out.println("Simple benchmark for matrix operations");
        out.println();

        out.println("Each benchmark will take about " + ((int) (seconds)) + " seconds...");

        for (Benchmark b : multiplicationBenchmarks) {
            if (skipJava) {
                if (b.getName().contains("Java")) {
                    continue;
                }
            }

            out.println();
            out.println("Running benchmark \"" + b.getName() + "\".");
            for (int n : multiplicationSizes) {
                out.printf("n = %-5d: ", n);
                out.flush();

                BenchmarkResult result = b.run(n, seconds);

                result.printResult();
            }
        }
    }

    private static void loopBenchmark() {

        out.println("Simple benchmark for comparing loop ordering in Java 1D Arrays Multiplication");
        out.println();
        out.println("Each benchmark will take about " + ((int) (seconds)) + " seconds...");

        for (ArrayBenchmark b : arrayBenchmarks) {
            for (String method : b.getMethods()) {
                out.println();
                out.println("Running benchmark \"" + b.getName() + ", method: " + method + "\".");

                for (int n : multiplicationSizes) {
                    out.printf("n = %-5d: ", n);
                    out.flush();
                    BenchmarkResult result = b.run(n, seconds, method);
                    result.printResult();
                }
            }
        }
    }

    private static void complexMatrixBenchmark() throws ExecutionException, InterruptedException {

        out.println("Simple benchmark for complex-matrix multiplication");
        out.println();

        out.println("Each benchmark will take about " + ((int) (seconds)) + " seconds...");

        for (Benchmark b : complexMultiplicationBenchmarks) {
            if (skipJava) {
                if (b.getName().contains("Java")) {
                    continue;
                }
            }

            out.println();
            out.println("Running benchmark \"" + b.getName() + "\".");
            for (int n : multiplicationSizes) {
                out.printf("n = %-5d: ", n);
                out.flush();

                BenchmarkResult result = b.run(n, seconds);

                result.printResult();
            }
        }

    }

    private static void experimentalBenchmark() throws ExecutionException, InterruptedException {

        out.println("Simple complex matrix multiplication");
        out.println();

        out.println("Each benchmark will take about " + ((int) (seconds)) + " seconds...");

        for (Benchmark b : experimentalBenchmarks) {
            if (skipJava) {
                if (b.getName().contains("Java")) {
                    continue;
                }
            }

            out.println();
            out.println("Running benchmark \"" + b.getName() + "\".");
            for (int n : multiplicationSizes) {
                out.printf("n = %-5d: ", n);
                out.flush();

                BenchmarkResult result = b.run(n, seconds);

                result.printResult();
            }
        }

    }
}