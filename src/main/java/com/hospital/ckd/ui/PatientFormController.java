package com.hospital.ckd.ui;

import com.hospital.ckd.model.PatientData;
import com.hospital.ckd.model.PredictionResult;
import com.hospital.ckd.service.PredictionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientFormController implements Initializable {

    // Input fields
    @FXML private TextField ageField;
    @FXML private TextField bloodPressureField;
    @FXML private TextField bloodGlucoseField;
    @FXML private TextField serumCreatinineField;
    @FXML private TextField hemoglobinField;
    @FXML private TextField albuminField;
    @FXML private TextField sugarField;
    @FXML private TextField sodiumField;
    @FXML private TextField potassiumField;
    @FXML private TextField packedCellVolumeField;

    // Action button
    @FXML private Button analyzeButton;

    // Result display
    @FXML private Label riskPercentageLabel;
    @FXML private Label riskCategoryLabel;
    @FXML private TextArea precautionsArea;
    @FXML private Label statusLabel;

    private PredictionService predictionService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the prediction service
        predictionService = PredictionService.getInstance();

        // Set up input field validation
        setupInputValidation();

        // Clear results initially
        clearResults();

        // Set button action
        analyzeButton.setOnAction(e -> analyzeCKDRisk());

        // Check service health
        if (!predictionService.isServiceHealthy()) {
            showError("Prediction service is not available");
        } else {
            statusLabel.setText("System ready");
            statusLabel.setTextFill(Color.GREEN);
        }
    }

    /**
     * Set up input validation for all numeric fields
     */
    private void setupInputValidation() {
        TextField[] fields = {
            ageField, bloodPressureField, bloodGlucoseField, serumCreatinineField,
            hemoglobinField, albuminField, sugarField, sodiumField, potassiumField, packedCellVolumeField
        };

        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                // Allow only numeric input (including decimals)
                if (!newValue.matches("\\d*\\.?\\d*")) {
                    field.setText(oldValue);
                }
            });
        }
    }

    /**
     * Handle the analyze button click
     */
    @FXML
    private void analyzeCKDRisk() {
        try {
            // Clear previous results
            clearResults();
            statusLabel.setText("Analyzing...");
            statusLabel.setTextFill(Color.BLUE);

            // Validate and collect input data
            PatientData patientData = collectPatientData();

            // Perform prediction
            PredictionResult result = predictionService.predictCKDRisk(patientData);

            // Display results
            displayResults(result);

            statusLabel.setText("Analysis completed");
            statusLabel.setTextFill(Color.GREEN);

        } catch (IllegalArgumentException e) {
            showError("Input Error: " + e.getMessage());
        } catch (Exception e) {
            showError("Analysis failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Collect and validate patient data from input fields
     */
    private PatientData collectPatientData() {
        // Check for empty fields
        if (isAnyFieldEmpty()) {
            throw new IllegalArgumentException("Please fill in all fields");
        }

        try {
            double age = Double.parseDouble(ageField.getText());
            double bloodPressure = Double.parseDouble(bloodPressureField.getText());
            double bloodGlucose = Double.parseDouble(bloodGlucoseField.getText());
            double serumCreatinine = Double.parseDouble(serumCreatinineField.getText());
            double hemoglobin = Double.parseDouble(hemoglobinField.getText());
            double albumin = Double.parseDouble(albuminField.getText());
            double sugar = Double.parseDouble(sugarField.getText());
            double sodium = Double.parseDouble(sodiumField.getText());
            double potassium = Double.parseDouble(potassiumField.getText());
            double packedCellVolume = Double.parseDouble(packedCellVolumeField.getText());

            return new PatientData(age, bloodPressure, bloodGlucose, serumCreatinine,
                                 hemoglobin, albumin, sugar, sodium, potassium, packedCellVolume);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter valid numeric values");
        }
    }

    /**
     * Check if any input field is empty
     */
    private boolean isAnyFieldEmpty() {
        TextField[] fields = {
            ageField, bloodPressureField, bloodGlucoseField, serumCreatinineField,
            hemoglobinField, albuminField, sugarField, sodiumField, potassiumField, packedCellVolumeField
        };

        for (TextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Display prediction results in the UI
     */
    private void displayResults(PredictionResult result) {
        riskPercentageLabel.setText(result.getRiskPercentage() + "%");
        riskCategoryLabel.setText(result.getRiskCategory());
        precautionsArea.setText(result.getPrecautions());

        // Color code the risk category
        Color categoryColor;
        int riskPercentage = result.getRiskPercentage();
        if (riskPercentage <= 20) {
            categoryColor = Color.GREEN;
        } else if (riskPercentage <= 40) {
            categoryColor = Color.ORANGE;
        } else if (riskPercentage <= 80) {
            categoryColor = Color.RED;
        } else {
            categoryColor = Color.DARKRED;
        }
        
        riskCategoryLabel.setTextFill(categoryColor);
        riskPercentageLabel.setTextFill(categoryColor);
    }

    /**
     * Clear all result displays
     */
    private void clearResults() {
        riskPercentageLabel.setText("--");
        riskCategoryLabel.setText("--");
        precautionsArea.clear();
        riskPercentageLabel.setTextFill(Color.BLACK);
        riskCategoryLabel.setTextFill(Color.BLACK);
    }

    /**
     * Show error message
     */
    private void showError(String message) {
        statusLabel.setText("Error: " + message);
        statusLabel.setTextFill(Color.RED);
        clearResults();
    }

    /**
     * Clear all input fields
     */
    @FXML
    private void clearForm() {
        TextField[] fields = {
            ageField, bloodPressureField, bloodGlucoseField, serumCreatinineField,
            hemoglobinField, albuminField, sugarField, sodiumField, potassiumField, packedCellVolumeField
        };

        for (TextField field : fields) {
            field.clear();
        }
        
        clearResults();
        statusLabel.setText("Form cleared");
        statusLabel.setTextFill(Color.BLUE);
    }
}
