package com.hospital.ckd.ml;

public class ActivationFunction {
    
    /**
     * Sigmoid activation function
     * Formula: 1 / (1 + e^(-x))
     */
    public static double sigmoid(double x) {
        // Prevent overflow for very large negative values
        if (x < -500) return 0.0;
        if (x > 500) return 1.0;
        return 1.0 / (1.0 + Math.exp(-x));
    }

    /**
     * ReLU activation function
     * Formula: max(0, x)
     */
    public static double relu(double x) {
        return Math.max(0, x);
    }

    /**
     * Tanh activation function
     * Formula: (e^x - e^(-x)) / (e^x + e^(-x))
     */
    public static double tanh(double x) {
        return Math.tanh(x);
    }
}
