package com.ppolabs.mindbend.experimental;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.RandomMatrices;

import java.util.Random;

/**
 * User: pmar@ppolabs.com
 * Date: 2/13/12
 * Time: 10:51 PM
 */
public class EJMLSchool {

    static DenseMatrix64F A;
    static DenseMatrix64F B;
    static DenseMatrix64F C;

    static void ejmlMultiplication() {

        // initialization of matrices
        A = new DenseMatrix64F(512,512);
        B = new DenseMatrix64F(512,512);
        C = new DenseMatrix64F(512,512);

        RandomMatrices.setRandom(A, 0, 1, new Random());
        RandomMatrices.setRandom(B, 0, 1, new Random());

        CommonOps.mult(A, B, C);
        
    }

    public static void main(String[] args) {

        ejmlMultiplication();
    }


}
