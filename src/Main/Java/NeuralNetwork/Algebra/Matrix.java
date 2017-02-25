package NeuralNetwork.Algebra;

/**
 * Created by brunodeluca on 2/25/17.
 */
public class Matrix {

    public static float[][] multiply(float[][] a, float[][] b){
        int aRow = a.length;
        int aColumn = a[0].length;
        int bRow = b.length;
        int bColumn = b[0].length;

        if(aColumn != bRow) throw new IllegalArgumentException("A:Rows: " + aColumn + " did not match B:Columns " + bRow + ".");

        float[][] c = new float[aRow][bColumn];

        for(int i = 0; i < aRow; i++){
            for(int j = 0; j < bColumn; j++){
                for(int k = 0; k < aColumn; k++){
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public static float[][] sum(float[][] a, float[][] b){
        int aRow = a.length;
        int aColumn = a[0].length;
        int bRow = b.length;
        int bColumn = b[0].length;

        if((aRow != bRow) || (aColumn != bColumn)) throw new IllegalArgumentException("Both matrix should have the same size");

        float[][] c = new float[aRow][aColumn];

        for(int i = 0; i < aRow; i++){
            for(int j = 0; j < aColumn; j++){
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

    public static float[][] scalarMuliply(float[][] a, float b){
        int aRow = a.length;
        int aColumn = a[0].length;

        float[][] c = new float[aRow][aColumn];

        for(int i = 0; i < aRow; i++){
            for(int j = 0; j < aColumn; j++){
                c[i][j] = a[i][j]*b;
            }
        }

        return c;
    }

    public static float[][] transpose(float[][] a){
        int aRow = a.length;
        int aColumn = a[0].length;

        float[][] c = new float[aColumn][aRow];

        for(int i = 0; i < aRow; i++){
            for(int j = 0; j < aColumn; j++){
                c[i][j] = a[j][i];
            }
        }
        return c;
    }

}
