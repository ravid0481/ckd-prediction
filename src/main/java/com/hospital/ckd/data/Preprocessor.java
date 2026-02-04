package com.hospital.ckd.data;

import com.hospital.ckd.model.PatientData;

public class Preprocessor {
    
    // Normal ranges for medical parameters (for normalization)
    private static final double[] MIN_VALUES = {0, 50, 50, 0.5, 3.1, 0.2, 0, 4.5, 2.5, 9};
    private static final double[] MAX_VALUES = {90, 200, 400, 15, 17.8, 5.8, 5, 150, 47, 54};

    /**
     * Normalize patient data for neural network input
     * This scales all values to [0, 1] range for better neural network performance
     * 
     * @param patientData Raw patient data
     * @return Normalized data array ready for neural network
     */
    public static double[] normalize(PatientData patientData) {
        double[] rawData = patientData.toArray();
        double[] normalized = new double[rawData.length];

        for (int i = 0; i < rawData.length; i++) {
            // Min-max normalization: (value - min) / (max - min)
            normalized[i] = (rawData[i] - MIN_VALUES[i]) / (MAX_VALUES[i] - MIN_VALUES[i]);
            
            // Clamp values to [0, 1] range in case of outliers
            normalized[i] = Math.max(0.0, Math.min(1.0, normalized[i]));
        }

        return normalized;
    }

    /**
     * Validate that all patient data values are within reasonable medical ranges
     * 
     * @param patientData Patient data to validate
     * @return Validation error message, or null if valid
     */
    public static String validateData(PatientData patientData) {
        double[] data = patientData.toArray();
        String[] fieldNames = {
            "Age", "Blood Pressure", "Blood Glucose Random", "Serum Creatinine",
            "Hemoglobin", "Albumin", "Sugar", "Sodium", "Potassium", "Packed Cell Volume"
        };

        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                return fieldNames[i] + " cannot be negative";
            }
            if (data[i] < MIN_VALUES[i] * 0.5 || data[i] > MAX_VALUES[i] * 2) {
                return fieldNames[i] + " value seems outside normal range. Please verify.";
            }
        }

        // Additional specific validations
        if (patientData.getAge() > 120) {
            return "Age seems unrealistic. Please verify.";
        }
        if (patientData.getBloodPressure() > 250) {
            return "Blood pressure reading seems extremely high. Please verify.";
        }

        return null; // All validations passed
    }

    /**
     * Get parameter information for UI tooltips
     */
    public static String getParameterInfo(String parameterName) {
        switch (parameterName.toLowerCase()) {
            case "age":
                return "Patient age in years (typical range: 0-120)";
            case "bloodpressure":
                return "Systolic blood pressure in mmHg (normal: 90-120)";
            case "bloodglucose":
                return "Random blood glucose in mg/dL (normal: 70-140)";
            case "serumcreatinine":
                return "Serum creatinine in mg/dL (normal: 0.6-1.3)";
            case "hemoglobin":
                return "Hemoglobin level in g/dL (normal: 12-16)";
            case "albumin":
                return "Albumin level in g/dL (normal: 3.5-5.0)";
            case "sugar":
                return "Sugar level (0=normal, 1-5=increasing levels)";
            case "sodium":
                return "Sodium level in mEq/L (normal: 135-145)";
            case "potassium":
                return "Potassium level in mEq/L (normal: 3.5-5.0)";
            case "packedcellvolume":
                return "Packed cell volume/Hematocrit % (normal: 36-46)";
            default:
                return "Medical parameter value";
        }
    }
}
