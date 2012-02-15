package com.ppolabs.mindbend.util;

public class ArrayDoubleMathUtils {

    public static double[][] mmuli_ijk_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++)
            for (int j = 0; j < bCols; j++)
                for (int k = 0; k < aCols; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static double[][] mmuli_ikj_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++)
            for (int k = 0; k < aCols; k++)
                for (int j = 0; j < bCols; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static double[][] mmuli_jik_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int j = 0; j < bCols; j++)
            for (int i = 0; i < aRows; i++)
                for (int k = 0; k < aCols; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static double[][] mmuli_GAXPY_2D(final double[][] AA, final double[][] BB) {
        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int j = 0; j < bCols; j++)
            for (int k = 0; k < aCols; k++)
                for (int i = 0; i < aRows; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static double[][] mmuli_kij_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int k = 0; k < aCols; k++)
            for (int i = 0; i < aRows; i++)
                for (int j = 0; j < bCols; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static double[][] mmuli_kji_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int k = 0; k < aCols; k++)
            for (int j = 0; j < bCols; j++)
                for (int i = 0; i < aRows; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static double[][] mmuli_ikj_PURE_ROW_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            double[] arowi = AA[i];
            double[] crowi = CC[i];
            for (int k = 0; k < aCols; k++) {
                double[] browk = BB[k];
                double aik = arowi[k];
                for (int j = 0; j < bCols; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
        return CC;
    }

    public static double[][] mmuli_JAMA_2D(final double[][] AA, final double[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        double[][] CC = new double[aRows][bCols];

        double[] bcolj = new double[bCols];

        for (int j = 0; j < bCols; j++) {

            for (int k = 0; k < aCols; k++) {
                bcolj[k] = BB[k][j];
            }

            for (int i = 0; i < aRows; i++) {
                double[] arowi = AA[i];
                double sum = 0.0;
                for (int k = 0; k < aCols; k++) {
                    sum += arowi[k] * bcolj[k];
                }
                CC[i][j] = sum;
            }
        }

        return CC;
    }
    public static void mmuli_ijk_1D(int n, double[] A, double[] B, double[] C) {

        for (int j = 0; j < n; j++) {
            int jn = j * n;
            for (int k = 0; k < n; k++) {
                int kn = k * n;
                double bkjn = B[k + jn];
                for (int i = 0; i < n; i++) {
                    C[i + jn] += A[i + kn] * bkjn;
                }
            }
        }
    }
    public static void mmuli_ijk_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_ikj_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int i = 0; i < n; i++)
            for (int k = 0; k < n; k++)
                for (int j = 0; j < n; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_jik_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int j = 0; j < n; j++)
            for (int i = 0; i < n; i++)
                for (int k = 0; k < n; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_GAXPY_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int j = 0; j < n; j++)
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_kij_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_kji_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int k = 0; k < n; k++)
            for (int j = 0; j < n; j++)
                for (int i = 0; i < n; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_JAMA_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {

        double[] bcolj = new double[n];

        for (int j = 0; j < n; j++) {

            for (int k = 0; k < n; k++) {
                bcolj[k] = BB[k][j];
            }

            for (int i = 0; i < n; i++) {
                double[] arowi = AA[i];
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += arowi[k] * bcolj[k];
                }
                CC[i][j] = sum;
            }
        }
    }
    public static void mmuli_ikj_PURE_ROW_2D(int n, final double[][] AA, final double[][] BB, double[][] CC) {
        for (int i = 0; i < n; i++) {
            double[] arowi = AA[i];
            double[] crowi = CC[i];
            for (int k = 0; k < n; k++) {
                double[] browk = BB[k];
                double aik = arowi[k];
                for (int j = 0; j < n; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
    }

    public static void transpose_inplace(double[] m, int w, int h) {

        // source: http://rosettacode.org/wiki/Matrix_transposition
        int start, next, i;
        double tmp;

        for (start = 0; start <= w * h - 1; start++) {
            next = start;
            i = 0;
            do {
                i++;
                next = (next % h) * w + next / h;
            } while (next > start);
            if (next < start || i == 1) continue;

            tmp = m[next = start];
            do {
                i = (next % h) * w + next / h;
                m[next] = (i == start) ? tmp : m[i];
                next = i;
            } while (next > start);
        }

    }
    
}
