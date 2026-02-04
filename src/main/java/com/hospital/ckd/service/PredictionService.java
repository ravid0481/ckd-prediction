package com.hospital.ckd.service;

import com.hospital.ckd.data.Preprocessor;
import com.hospital.ckd.ml.NeuralNetwork;
import com.hospital.ckd.model.PatientData;
import com.hospital.ckd.model.PredictionResult;

public class PredictionService {
    private final NeuralNetwork neuralNetwork;
    private static PredictionService instance;

    /**
     * Private constructor for singleton pattern
     */
    private PredictionService() {
        this.neuralNetwork = new NeuralNetwork();
        System.out.println("CKD Prediction Service initialized");
        System.out.println(neuralNetwork.getNetworkSummary());
    }

    /**
     * Get singleton instance of PredictionService
     */
    public static synchronized PredictionService getInstance() {
        if (instance == null) {
            instance = new PredictionService();
        }
        return instance;
    }

    /**
     * Predict CKD risk for a patient
     * 
     * @param patientData Patient medical data
     * @return PredictionResult containing risk percentage, category, and precautions
     * @throws IllegalArgumentException if patient data is invalid
     */
    public PredictionResult predictCKDRisk(PatientData patientData) {
        // Validate input data
        String validationError = Preprocessor.validateData(patientData);
        if (validationError != null) {
            throw new IllegalArgumentException("Data validation failed: " + validationError);
        }

        try {
            // Preprocess data (normalize for neural network)
            double[] normalizedInputs = Preprocessor.normalize(patientData);

            // Get prediction from neural network
            double riskProbability = neuralNetwork.predict(normalizedInputs);

            // Create and return result
            PredictionResult result = new PredictionResult(riskProbability);
            
            // Log prediction for debugging
            logPrediction(patientData, result);
            
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Prediction failed: " + e.getMessage(), e);
        }
    }

    /**
     * Log prediction details for debugging and monitoring
     */
    private void logPrediction(PatientData patientData, PredictionResult result) {
        System.out.println("=== CKD Risk Prediction ===");
        System.out.printf("Patient Age: %.1f years%n", patientData.getAge());
        System.out.printf("Risk Probability: %.3f%n", result.getRiskProbability());
        System.out.printf("Risk Percentage: %d%%%n", result.getRiskPercentage());
        System.out.printf("Risk Category: %s%n", result.getRiskCategory());
        System.out.println("============================");
    }

    /**
     * Get service health status
     */
    public boolean isServiceHealthy() {
        try {
            // Test with sample data
            PatientData testData = new PatientData(45, 120, 100, 1.0, 14.0, 4.0, 0, 140, 4.0, 40);
            double result = neuralNetwork.predict(Preprocessor.normalize(testData));
            return result >= 0.0 && result <= 1.0;
        } catch (Exception e) {
            System.err.println("Service health check failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get neural network information
     */
    public String getModelInfo() {
        return neuralNetwork.getNetworkSummary();
    }
}
