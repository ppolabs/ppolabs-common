package com.ppolabs.mindbend.benchmark.wrappers;

public class Data32F {

    public float[] data;
    public int numRows;
    public int numCols;

    public float[] getData() {
        return data;
    }

    public float get( int index ) {
        return data[index];
    }


    public Data32F(int numRows, int numCols, boolean rowMajor, float... data) {
        final int length = numRows * numCols;
        this.data = new float[length];

        this.numRows = numRows;
        this.numCols = numCols;

        set(numRows, numCols, rowMajor, data);
    }

    public Data32F(float data[][]) {
        this.numRows = data.length;
        this.numCols = data[0].length;

        this.data = new float[numRows * numCols];

        int pos = 0;
        for (int i = 0; i < numRows; i++) {
            float[] row = data[i];

            if (row.length != numCols) {
                throw new IllegalArgumentException("All rows must have the same length");
            }

            System.arraycopy(row, 0, this.data, pos, numCols);

            pos += numCols;
        }
    }

    public Data32F(int numRows, int numCols) {
        data = new float[numRows * numCols];

        this.numRows = numRows;
        this.numCols = numCols;
    }

    public Data32F(Data32F orig) {
        this(orig.numRows, orig.numCols);
        System.arraycopy(orig.data, 0, this.data, 0, orig.getNumElements());
    }

    public Data32F(int length) {
        data = new float[length];
    }

    public Data32F() {
    }

    public static Data32F wrap(int numRows, int numCols, float[] data) {
        Data32F s = new Data32F();
        s.data = data;
        s.numRows = numRows;
        s.numCols = numCols;

        return s;
    }

    public void reshape(int numRows, int numCols, boolean saveValues) {
        if (data.length < numRows * numCols) {
            float[] d = new float[numRows * numCols];

            if (saveValues) {
                System.arraycopy(data, 0, d, 0, getNumElements());
            }

            this.data = d;
        }

        this.numRows = numRows;
        this.numCols = numCols;
    }

    public void set(int row, int col, float value) {
        if (col < 0 || col >= numCols || row < 0 || row >= numRows) {
            throw new IllegalArgumentException("Specified element is out of bounds: (" + row + " , " + col + ")");
        }

        data[row * numCols + col] = value;
    }

    
    public void unsafe_set(int row, int col, float value) {
        data[row * numCols + col] = value;
    }

    public void add(int row, int col, float value) {
        if (col < 0 || col >= numCols || row < 0 || row >= numRows) {
            throw new IllegalArgumentException("Specified element is out of bounds");
        }

        data[row * numCols + col] += value;
    }

    public float get(int row, int col) {
        if (col < 0 || col >= numCols || row < 0 || row >= numRows) {
            throw new IllegalArgumentException("Specified element is out of bounds: " + row + " " + col);
        }

        return data[row * numCols + col];
    }

    
    public float unsafe_get(int row, int col) {
        return data[row * numCols + col];
    }

    
    public int getIndex(int row, int col) {
        return row * numCols + col;
    }

    public boolean isInBounds(int row, int col) {
        return (col >= 0 && col < numCols && row >= 0 && row < numRows);
    }

    public int getNumElements() {
        return numRows * numCols;
    }

    public void setReshape(Data32F b) {
        int dataLength = b.getNumElements();

        if (data.length < dataLength) {
            data = new float[dataLength];
        }

        this.numRows = b.numRows;
        this.numCols = b.numCols;

        System.arraycopy(b.data, 0, this.data, 0, dataLength);
    }

    public void set(int numRows, int numCols, boolean rowMajor, float... data) {
        int length = numRows * numCols;

        if (numRows != this.numRows || numCols != this.numCols)
            throw new IllegalArgumentException("Unexpected matrix shape.");
        if (length > this.data.length)
            throw new IllegalArgumentException("The length of this matrix's data array is too small.");

        if (rowMajor) {
            System.arraycopy(data, 0, this.data, 0, length);
        } else {
            int index = 0;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    this.data[index++] = data[j * numRows + i];
                }
            }
        }
    }

    public void zero() {
        final int size = this.getNumElements();
        final float value = 0;
        
        for( int i = 0; i < size; i++ ) {
            this.set(i, value);
        }
        
    }

    public float set( int index , float val ) {
        return this.data[index] = val;
    }


    @SuppressWarnings({"unchecked"})
    public Data32F copy() {
        return new Data32F(this);
    }


}
                 