package NeuralNetwork;

import java.util.Random;
import NeuralNetwork.Algebra.Matrix;

/**
 * An implementation of a basic Neural Network
 * that takes two inputs and outputs a result.
 * It can be used in different scenarios such as
 * guessing the result of a test based on the
 * amount of sleep time and study time.
 * @author Bruno De Luca
 */

public class NeuralNetwork {

    /**
     * The number of inputs, hidden neurons and the number of outputs
     */

    private int inputLayerSize;
    private int outputLayerSize;
    private int hiddenLayerSize;

    /**
     * The weighs which will change the values of out input to get the final result
     */

    private float[][] w1;
    private float[][] w2;

    /**
     * Matrices being transformed and used for different methods
     */

    private float[][] z3;
    private float[][] z1;
    private float[][] a2;

    /**
     * Constructor. It sets the fields and generates random values for the wight matrices
     * @param inputLayerSize the number of inputs
     * @param outputLayerSize number of outputs
     * @param hiddenLayerSize number of hidden neurones
     */

    public NeuralNetwork(int inputLayerSize, int outputLayerSize, int hiddenLayerSize){
        this.inputLayerSize = inputLayerSize;
        this.outputLayerSize = outputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.w1 = generateRandomParameters(inputLayerSize, hiddenLayerSize);
        this.w2 = generateRandomParameters(hiddenLayerSize, outputLayerSize);
    }

    /**
     * Returns a matrix with random weights. it is intended to be instanced once
     * @param row number of rows of the matrix
     * @param col number of columns of the matrix
     * @return a matrix with random weights
     */

    private float[][] generateRandomParameters(int row, int col){
        float[][] w = new float[row][col];
        for(int i = 0; i < w.length; i++){
            for(int j = 0; j < w[i].length; j++){
                w[i][j] = (new Random()).nextFloat();
            }
        }
        return w;
    }

    /**
     * it returns the matrix with the outputs. For each input, it is multiplied by its weight.
     * Then it passes though a sigmoid function to normalize the values and finally
     * it is multiplied by another weight parameter and normalized again.
     * @param X the matrix with the inputs
     * @return the matrix with the final results
     */

    public float[][] forward(float[][] X){
        z1 = Matrix.multiply(X, this.w1);
        a2 = sigmoidOperation(z1);
        this.z3 = Matrix.multiply(a2, this.w2);
        return sigmoidOperation(z3);
    }

    /**
     * Returns a matrix transformed by the sigmoid function
     * @param z the matrix to be transformed
     * @return a matrix with its values being transformed by the sigmoid function
     */

    private float[][] sigmoidOperation(float[][] z){
        for(int i = 0; i < z.length; i++){
            for(int j = 0; j < z[i].length; j++){
                z[i][j] = sigmoid(z[i][j]);
            }
        }
        return z;
    }

    /**
     * Returns a matrix transformed by the derivative of the sigmoid function
     * @param z the matrix to be transformed
     * @return a matrix with its values being transformed by the derivative of the sigmoid function
     */

    private float[][] sigmoidPrimeOperation(float[][] z){
        for(int i = 0; i < z.length; i++){
            for(int j = 0; j < z[i].length; j++){
                z[i][j] = sigmoidPrime(z[i][j]);
            }
        }
        return z;
    }

    /**
     * The sigmoid function
     * @param z the initial value
     * @return the transformation of the initial value
     */

    private float sigmoid(float z){
        return (float) (1 / (1 + Math.exp(-z)));
    }

    /**
     * The derivative of the sigmoid function
     * @param z the initial value
     * @return the transformation of the inital value
     */

    private float sigmoidPrime(float z) { return (float) (Math.exp(-z)/Math.pow(1 + Math.exp(-z),2));}

    /**
     * Returns a matrix with the costs given the initial values and the final result
     * @param X matrix with the initial values
     * @param y matrix with the original results
     * @return matrix with the values of each cost
     */

    public float[][] costFunction(float[][] X, float[][] y){
        float[][] yHat = forward(X);
        return Matrix.scalarMultiply(Matrix.multiply(Matrix.sum(y,Matrix.scalarMultiply(yHat, -1)),Matrix.sum(y,Matrix.scalarMultiply(yHat, -1))), 0.5f);
    }

    /**
     * Returns a matrix with the costs  respect to W1 and W2 given the initial values and the final result
     * @param X matrix with the initial values
     * @param y matrix with the original results
     * @return matrix with the values of each cost
     */

    public float[][][] costFunctionPrime(float[][] X, float[][] y){
        float[][] yHat = forward(X);

        float[][] delta3 = Matrix.scalarMultiply(Matrix.multiply(Matrix.sum(y, Matrix.scalarMultiply(yHat, -1)), sigmoidPrimeOperation(z3)), -1);
        float[][] d3 = Matrix.multiply(Matrix.transpose(a2), delta3);

        float[][] delta2 = Matrix.multiply(delta3, Matrix.multiply(Matrix.transpose(w2), sigmoidPrimeOperation(z1)));
        float[][] d2 = Matrix.multiply(Matrix.transpose(X), delta2);

        float[][][] c = {d2, d3};
        return c;
    }

}