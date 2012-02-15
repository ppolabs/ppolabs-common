package com.ppolabs.mindbend.util;

public class ArrayFloatMathUtils {

    public static float[][] mmuli_ijk_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int i = 0; i < aRows; i++)
            for (int j = 0; j < bCols; j++)
                for (int k = 0; k < aCols; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static float[][] mmuli_ikj_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int i = 0; i < aRows; i++)
            for (int k = 0; k < aCols; k++)
                for (int j = 0; j < bCols; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static float[][] mmuli_jik_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int j = 0; j < bCols; j++)
            for (int i = 0; i < aRows; i++)
                for (int k = 0; k < aCols; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static float[][] mmuli_GAXPY_2D(final float[][] AA, final float[][] BB) {
        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int j = 0; j < bCols; j++)
            for (int k = 0; k < aCols; k++)
                for (int i = 0; i < aRows; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static float[][] mmuli_kij_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int k = 0; k < aCols; k++)
            for (int i = 0; i < aRows; i++)
                for (int j = 0; j < bCols; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static float[][] mmuli_kji_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int k = 0; k < aCols; k++)
            for (int j = 0; j < bCols; j++)
                for (int i = 0; i < aRows; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];

        return CC;
    }
    public static float[][] mmuli_ikj_PURE_ROW_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            float[] arowi = AA[i];
            float[] crowi = CC[i];
            for (int k = 0; k < aCols; k++) {
                float[] browk = BB[k];
                double aik = arowi[k];
                for (int j = 0; j < bCols; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
        return CC;
    }

    public static float[][] mmuli_JAMA_2D(final float[][] AA, final float[][] BB) {

        final int aRows = AA.length;
        final int aCols = AA[0].length;
        final int bRows = BB.length;
        final int bCols = BB[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("A:Cols: " + aCols + " did not match B:Rows " + bRows + ".");
        }

        float[][] CC = new float[aRows][bCols];

        float[] bcolj = new float[bCols];

        for (int j = 0; j < bCols; j++) {

            for (int k = 0; k < aCols; k++) {
                bcolj[k] = BB[k][j];
            }

            for (int i = 0; i < aRows; i++) {
                float[] arowi = AA[i];
                float sum = 0f;
                for (int k = 0; k < aCols; k++) {
                    sum += arowi[k] * bcolj[k];
                }
                CC[i][j] = sum;
            }
        }

        return CC;
    }
    public static void mmuli_ijk_1D(int n, float[] A, float[] B, float[] C) {

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
    public static void mmuli_ijk_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_ikj_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int i = 0; i < n; i++)
            for (int k = 0; k < n; k++)
                for (int j = 0; j < n; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_jik_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int j = 0; j < n; j++)
            for (int i = 0; i < n; i++)
                for (int k = 0; k < n; k++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_GAXPY_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int j = 0; j < n; j++)
            for (int k = 0; k < n; k++)
                for (int i = 0; i < n; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_kij_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_kji_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int k = 0; k < n; k++)
            for (int j = 0; j < n; j++)
                for (int i = 0; i < n; i++)
                    CC[i][j] += AA[i][k] * BB[k][j];
    }
    public static void mmuli_JAMA_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {

        float[] bcolj = new float[n];

        for (int j = 0; j < n; j++) {

            for (int k = 0; k < n; k++) {
                bcolj[k] = BB[k][j];
            }

            for (int i = 0; i < n; i++) {
                float[] arowi = AA[i];
                float sum = 0f;
                for (int k = 0; k < n; k++) {
                    sum += arowi[k] * bcolj[k];
                }
                CC[i][j] = sum;
            }
        }
    }
    public static void mmuli_ikj_PURE_ROW_2D(int n, final float[][] AA, final float[][] BB, float[][] CC) {
        for (int i = 0; i < n; i++) {
            float[] arowi = AA[i];
            float[] crowi = CC[i];
            for (int k = 0; k < n; k++) {
                float[] browk = BB[k];
                double aik = arowi[k];
                for (int j = 0; j < n; j++) {
                    crowi[j] += aik * browk[j];
                }
            }
        }
    }

    public static void transpose_inplace(float[] m, int w, int h) {

        // source: http://rosettacode.org/wiki/Matrix_transposition
        int start, next, i;
        float tmp;

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
