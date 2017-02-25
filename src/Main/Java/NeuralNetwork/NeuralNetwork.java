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
        float[][] z1 = Matrix.multiply(X, this.w1);
        float[][] a2 = sigmoidOperation(z1);
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

    private float sigmoid(float z){
        return (float) (1 / (1 + Math.exp(-z)));
    }

    private float sigmoidPrime(float z) { return (float) (Math.exp(-z)/Math.pow(1 + Math.exp(-z),2));}

    public float costFunction(float[][] X, float[][] y) {
        float[][] yHat = forward(X);
        float x = 0;
        for(int i = 0; i < yHat[0].length; i++){
            x += 0.5*Math.pow(y[0][i] - yHat[0][i],2);
        }
        return x;
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