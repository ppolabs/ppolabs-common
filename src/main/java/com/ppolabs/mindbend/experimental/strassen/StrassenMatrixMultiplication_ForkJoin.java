package com.ppolabs.mindbend.experimental.strassen;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Uses static imports of the {@link StrassenMatrixMultiplication} class and
 * extends it with the Java7MatrixMultiply_STUB/join framework introduced with java7.
 *
 * @author pmar
 */
public class StrassenMatrixMultiplication_ForkJoin {

    /**
     * Multiplies two quadratic (length must be a power of 2) matrices.
     *
     * @param a
     * @param b
     * @return
     */
    public static double[][] multiply(double[][] a, double[][] b) throws InterruptedException, ExecutionException {
        StrassenMatrixMultiplication.checkInput(a, b);
        // setup a pool
        ForkJoinPool pool = new ForkJoinPool();
        // start the initial computing instance
        StrassenComputer computer = new StrassenComputer(a, b);
        // submit it to the pool
        pool.submit(computer);
        // wait for the result
        return computer.get();
    }

    public static final class StrassenComputer extends RecursiveTask<double[][]> {

        private static final long serialVersionUID = -3691620760085314284L;
        private final double[][] inputA;
        private final double[][] inputB;

        public StrassenComputer(double[][] inputA, double[][] inputB) {
            super();
            this.inputA = inputA;
            this.inputB = inputB;
        }

        @Override
        protected double[][] compute() {
            int n = inputA.length;
            if (n == 1) {
                double[][] toReturn = new double[1][1];
                toReturn[0][0] = inputA[0][0] * inputB[0][0];
                return toReturn;
            }

            int nHalf = n / 2;
            double[][] a = StrassenMatrixMultiplication.copy(nHalf, inputA, 0, 0);
            double[][] b = StrassenMatrixMultiplication.copy(nHalf, inputA, 0, nHalf);
            double[][] c = StrassenMatrixMultiplication.copy(nHalf, inputA, nHalf, 0);
            double[][] d = StrassenMatrixMultiplication.copy(nHalf, inputA, nHalf, nHalf);
            double[][] e = StrassenMatrixMultiplication.copy(nHalf, inputB, 0, 0);
            double[][] f = StrassenMatrixMultiplication.copy(nHalf, inputB, 0, nHalf);
            double[][] g = StrassenMatrixMultiplication.copy(nHalf, inputB, nHalf, 0);
            double[][] h = StrassenMatrixMultiplication.copy(nHalf, inputB, nHalf, nHalf);

            // create Java7MatrixMultiply_STUB instances
            ForkJoinTask<double[][]> fork1 = new StrassenComputer(a, StrassenMatrixMultiplication.add(f, h, -1)).fork();            // P1 = a(f-h) = af-ah
            ForkJoinTask<double[][]> fork2 = new StrassenComputer(StrassenMatrixMultiplication.add(a, b, 1), h).fork();             // P2 = (a+b)h = ah+bh
            ForkJoinTask<double[][]> fork3 = new StrassenComputer(StrassenMatrixMultiplication.add(c, d, 1), e).fork();             // P3 = (c+d)e = ce+de
            ForkJoinTask<double[][]> fork4 = new StrassenComputer(d, StrassenMatrixMultiplication.add(g, e, -1)).fork();            // P4 = d(g-e) = dg-de
            ForkJoinTask<double[][]> fork5 = new StrassenComputer(StrassenMatrixMultiplication.add(a, d, 1), StrassenMatrixMultiplication.add(e, h, 1)).fork();  // P5 = (a+d)(e+h)=ae+de+ah+dh
            ForkJoinTask<double[][]> fork6 = new StrassenComputer(StrassenMatrixMultiplication.add(b, d, -1), StrassenMatrixMultiplication.add(g, h, 1)).fork(); // P6 = (b-d)(g+h)=bg-dg+bh-dh
            ForkJoinTask<double[][]> fork7 = new StrassenComputer(StrassenMatrixMultiplication.add(a, c, -1), StrassenMatrixMultiplication.add(e, f, 1)).fork(); // P7 = (a-c)(e+f)=ae-ce+af-cf

            // r = P5+P4-P2+P6 = ae+bg
            double[][] r = StrassenMatrixMultiplication.add(StrassenMatrixMultiplication.add(fork5.join(), fork4.join(), 1), StrassenMatrixMultiplication.add(fork2.join(), fork6.join(), -1), -1);

            // s = P1+P2 = af+bh
            double[][] s = StrassenMatrixMultiplication.add(fork1.join(), fork2.join(), 1);

            // t = P3+P4 = ce+dg
            double[][] t = StrassenMatrixMultiplication.add(fork3.join(), fork4.join(), 1);

            // u = P5+P1-P3-P7 = cf+dh
            double[][] u = StrassenMatrixMultiplication.add(StrassenMatrixMultiplication.add(fork5.join(), fork1.join(), 1), StrassenMatrixMultiplication.add(fork3.join(), fork7.join(), 1), -1);

            return StrassenMatrixMultiplication.reconstructMatrix(r, s, t, u);
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        double[][] a = new double[][]{{8, 9}, {5, -1}};
        double[][] b = new double[][]{{-2, 3}, {4, 0}};
        /*
         * Answer: [20.0, 24.0] [-14.0, 15.0]
         */
        StrassenMatrixMultiplication.printMatrix(multiply(a, b));
    }


}
