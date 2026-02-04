package com.hospital.ckd.ml;

public class Layer {
    private final int inputSize;
    private final int outputSize;
    private final double[][] weights;
    private final double[] biases;
    private final String activationType;

    /**
     * Create a neural network layer
     * @param inputSize Number of input neurons
     * @param outputSize Number of output neurons
     * @param activationType Type of activation function ("sigmoid", "relu", "tanh")
     */
    public Layer(int inputSize, int outputSize, String activationType) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.activationType = activationType;
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        
        // Initialize weights and biases with pre-trained values
        initializeWeights();
    }

    /**
     * Initialize weights with pre-trained values for CKD prediction
     * These values are simulated pre-trained weights for demonstration
     */
    private void initializeWeights() {
        // Hidden layer weights (10 inputs -> 8 hidden neurons)
        if (inputSize == 10 && outputSize == 8) {
            // Simulated pre-trained weights for hidden layer
            double[][] hiddenWeights = {
                {0.15, -0.23, 0.45, -0.12, 0.67, -0.34, 0.28, 0.19, -0.41, 0.33},
                {-0.18, 0.42, -0.31, 0.56, -0.29, 0.71, -0.15, 0.38, 0.24, -0.47},
                {0.39, -0.14, 0.52, -0.36, 0.18, -0.43, 0.61, -0.27, 0.35, 0.19},
                {-0.25, 0.48, -0.17, 0.34, -0.59, 0.22, -0.46, 0.53, -0.31, 0.16},
                {0.41, -0.29, 0.63, -0.18, 0.37, -0.51, 0.14, 0.46, -0.32, 0.58},
                {-0.33, 0.56, -0.24, 0.49, -0.16, 0.38, -0.62, 0.27, 0.44, -0.35},
                {0.28, -0.41, 0.35, -0.53, 0.47, -0.19, 0.32, -0.48, 0.26, 0.51},
                {-0.47, 0.31, -0.58, 0.24, 0.42, -0.37, 0.19, 0.55, -0.43, 0.29}
            };
            double[] hiddenBiases = {-0.1, 0.2, -0.15, 0.3, -0.05, 0.25, -0.2, 0.1};
            
            System.arraycopy(hiddenBiases, 0, this.biases, 0, hiddenBiases.length);
            for (int i = 0; i < outputSize; i++) {
                System.arraycopy(hiddenWeights[i], 0, this.weights[i], 0, inputSize);
            }
        }
        // Output layer weights (8 hidden -> 1 output)
        else if (inputSize == 8 && outputSize == 1) {
            double[][] outputWeights = {
                {0.73, -0.41, 0.58, -0.32, 0.67, -0.29, 0.44, 0.51}
            };
            double[] outputBiases = {-0.15};
            
            System.arraycopy(outputBiases, 0, this.biases, 0, outputBiases.length);
            System.arraycopy(outputWeights[0], 0, this.weights[0], 0, inputSize);
        }
    }

    /**
     * Forward propagation through this layer
     * @param inputs Input values
     * @return Output values after applying weights, biases, and activation
     */
    public double[] forward(double[] inputs) {
        if (inputs.length != inputSize) {
            throw new IllegalArgumentException("Input size mismatch. Expected: " + inputSize + ", Got: " + inputs.length);
        }

        double[] outputs = new double[outputSize];

        // Calculate weighted sum + bias for each output neuron
        for (int i = 0; i < outputSize; i++) {
            double sum = biases[i];
            for (int j = 0; j < inputSize; j++) {
                sum += weights[i][j] * inputs[j];
            }
            
            // Apply activation function
            outputs[i] = applyActivation(sum);
        }

        return outputs;
    }

    /**
     * Apply the specified activation function
     */
    private double applyActivation(double value) {
        switch (activationType.toLowerCase()) {
            case "sigmoid":
                return ActivationFunction.sigmoid(value);
            case "relu":
                return ActivationFunction.relu(value);
            case "tanh":
                return ActivationFunction.tanh(value);
            default:
                return value; // Linear activation
        }
    }

    // Getters
    public int getInputSize() { return inputSize; }
    public int getOutputSize() { return outputSize; }
    public String getActivationType() { return activationType; }
}
