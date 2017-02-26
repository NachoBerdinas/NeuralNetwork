package NeuralNetwork;

import java.util.Random;
import NeuralNetwork.Algebra.Matrix;

/**
 * Created by brunodeluca on 2/15/17.
 */

public class NeuralNetwork {

    private int inputLayerSize;
    private int outputLayerSize;
    private int hiddenLayerSize;

    private float[][] w1;
    private float[][] w2;

    private float[][] z3;
    private float[][] z1;
    private float[][] a2;

    public NeuralNetwork(int inputLayerSize, int outputLayerSize, int hiddenLayerSize){
        this.inputLayerSize = inputLayerSize;
        this.outputLayerSize = outputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.w1 = generateRandomParameters(inputLayerSize, hiddenLayerSize);
        this.w2 = generateRandomParameters(hiddenLayerSize, outputLayerSize);
    }

    private float[][] generateRandomParameters(int row, int col){
        float[][] w = new float[row][col];
        for(int i = 0; i < w.length; i++){
            for(int j = 0; j < w[i].length; j++){
                w[i][j] = (new Random()).nextFloat();
            }
        }
        return w;
    }

    public float[][] forward(float[][] X){
        z1 = Matrix.multiply(X, this.w1);
        a2 = sigmoidOperation(z1);
        this.z3 = Matrix.multiply(a2, this.w2);
        return sigmoidOperation(z3);
    }

    private float[][] sigmoidOperation(float[][] z){
        for(int i = 0; i < z.length; i++){
            for(int j = 0; j < z[i].length; j++){
                z[i][j] = sigmoid(z[i][j]);
            }
        }
        return z;
    }

    private float[][] sigmoidPrimeOperation(float[][] z){
        for(int i = 0; i < z.length; i++){
            for(int j = 0; j < z[i].length; j++){
                z[i][j] = sigmoidPrime(z[i][j]);
            }
        }
        return z;
    }

    private float sigmoid(float z){
        return (float) (1 / (1 + Math.exp(-z)));
    }

    private float sigmoidPrime(float z) { return (float) (Math.exp(-z)/Math.pow(1 + Math.exp(-z),2));}

    public float[][] costFunction(float[][] X, float[][] y){
        float[][] yHat = forward(X);
        return Matrix.scalarMultiply(Matrix.multiply(Matrix.sum(y,Matrix.scalarMultiply(yHat, -1)),Matrix.sum(y,Matrix.scalarMultiply(yHat, -1))), 0.5f);
    }

    public float[][][] costFunctionPrime(float[][] X, float[][] y){
        float[][] yHat = forward(X);

        float[][] delta3 = Matrix.scalarMultiply(Matrix.multiply(Matrix.sum(y, Matrix.scalarMultiply(yHat, -1)), sigmoidPrimeOperation(z3)), -1);
        float[][] d3 = Matrix.multiply(Matrix.transpose(a2), delta3);

        float[][] delta2 = Matrix.multiply(delta3, Matrix.multiply(Matrix.transpose(w2), sigmoidPrimeOperation(z1)));
        float[][] d2 = Matrix.multiply(Matrix.transpose(X), delta2);

        float[][][] c = {d2, d3};
        return c;
    }

    public void printMatrix(float[][] z){
        for(int i = 0; i < z.length; i++){
            for(int j = 0; j < z[i].length; j++){
                System.out.print(z[i][j] + " ");
            }
            System.out.println();
        }
    }

}