package com.hospital.ckd.ml;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private final List<Layer> layers;
    private final int inputSize;
    private final int outputSize;

    /**
     * Create a neural network for CKD risk prediction
     * Architecture: 10 inputs -> 8 hidden (sigmoid) -> 1 output (sigmoid)
     */
    public NeuralNetwork() {
        this.inputSize = 10; // 10 medical parameters
        this.outputSize = 1; // CKD risk probability
        this.layers = new ArrayList<>();
        
        // Build the network architecture
        buildNetwork();
    }

    /**
     * Build the neural network architecture
     * This creates a simple feedforward network suitable for binary classification
     */
    private void buildNetwork() {
        // Hidden layer: 10 inputs -> 8 neurons with sigmoid activation
        Layer hiddenLayer = new Layer(10, 8, "sigmoid");
        layers.add(hiddenLayer);

        // Output layer: 8 inputs -> 1 output with sigmoid activation
        Layer outputLayer = new Layer(8, 1, "sigmoid");
        layers.add(outputLayer);

        System.out.println("Neural Network initialized:");
        System.out.println("- Input layer: 10 neurons");
        System.out.println("- Hidden layer: 8 neurons (sigmoid activation)");
        System.out.println("- Output layer: 1 neuron (sigmoid activation)");
    }

    /**
     * Predict CKD risk for given patient data
     * @param inputs Patient medical parameters (preprocessed)
     * @return CKD risk probability (0.0 to 1.0)
     */
    public double predict(double[] inputs) {
        if (inputs.length != inputSize) {
            throw new IllegalArgumentException("Input size must be " + inputSize + " but got " + inputs.length);
        }

        // Forward propagation through all layers
        double[] currentOutput = inputs;
        
        for (Layer layer : layers) {
            currentOutput = layer.forward(currentOutput);
        }

        // Return the single output value (CKD risk probability)
        return currentOutput[0];
    }

    /**
     * Get network summary information
     */
    public String getNetworkSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Neural Network Architecture:\n");
        summary.append("Input size: ").append(inputSize).append("\n");
        
        for (int i = 0; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            summary.append("Layer ").append(i + 1).append(": ")
                   .append(layer.getInputSize()).append(" -> ")
                   .append(layer.getOutputSize()).append(" (")
                   .append(layer.getActivationType()).append(")\n");
        }
        
        summary.append("Output size: ").append(outputSize);
        return summary.toString();
    }

    // Getters
    public int getInputSize() { return inputSize; }
    public int getOutputSize() { return outputSize; }
    public List<Layer> getLayers() { return layers; }
}
