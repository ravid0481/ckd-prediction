package com.hospital.ckd.model;

public class PredictionResult {
    private final double riskProbability;
    private final int riskPercentage;
    private final String riskCategory;
    private final String precautions;

    public PredictionResult(double riskProbability) {
        this.riskProbability = riskProbability;
        this.riskPercentage = (int) Math.round(riskProbability * 100);
        this.riskCategory = determineRiskCategory(riskPercentage);
        this.precautions = generatePrecautions(riskPercentage);
    }

    private String determineRiskCategory(int percentage) {
        if (percentage <= 10) return "Very Low Risk";
        else if (percentage <= 20) return "Low Risk";
        else if (percentage <= 40) return "Moderate Risk";
        else if (percentage <= 60) return "High Risk";
        else if (percentage <= 80) return "Very High Risk";
        else return "Critical Risk";
    }

    private String generatePrecautions(int percentage) {
        if (percentage <= 10) {
            return "• Maintain regular health check-ups\n" +
                   "• Follow a balanced diet\n" +
                   "• Stay hydrated\n" +
                   "• Exercise regularly\n" +
                   "• Avoid smoking and excessive alcohol";
        } else if (percentage <= 20) {
            return "• Monitor blood pressure regularly\n" +
                   "• Reduce salt intake\n" +
                   "• Maintain healthy weight\n" +
                   "• Regular moderate exercise\n" +
                   "• Annual kidney function tests";
        } else if (percentage <= 40) {
            return "• Strict dietary control - low protein, low sodium\n" +
                   "• Monitor blood glucose levels\n" +
                   "• Regular blood pressure monitoring\n" +
                   "• Avoid nephrotoxic medications\n" +
                   "• Semi-annual kidney function tests";
        } else if (percentage <= 60) {
            return "• Immediate medical consultation required\n" +
                   "• Regular monitoring by healthcare provider\n" +
                   "• Strict medication compliance\n" +
                   "• Specialized kidney diet\n" +
                   "• Quarterly medical follow-ups";
        } else if (percentage <= 80) {
            return "• Urgent nephrologist consultation required\n" +
                   "• Comprehensive kidney evaluation needed\n" +
                   "• Strict medical supervision\n" +
                   "• Potential dialysis preparation\n" +
                   "• Monthly medical monitoring";
        } else {
            return "• IMMEDIATE MEDICAL ATTENTION REQUIRED\n" +
                   "• Emergency nephrology consultation\n" +
                   "• Hospitalization may be necessary\n" +
                   "• Urgent kidney function assessment\n" +
                   "• Immediate treatment intervention needed";
        }
    }

    // Getters
    public double getRiskProbability() { return riskProbability; }
    public int getRiskPercentage() { return riskPercentage; }
    public String getRiskCategory() { return riskCategory; }
    public String getPrecautions() { return precautions; }
}
