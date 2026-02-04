package com.hospital.ckd.model;

public class PatientData {
    private double age;
    private double bloodPressure;
    private double bloodGlucoseRandom;
    private double serumCreatinine;
    private double hemoglobin;
    private double albumin;
    private double sugar;
    private double sodium;
    private double potassium;
    private double packedCellVolume;

    // Constructor
    public PatientData(double age, double bloodPressure, double bloodGlucoseRandom,
                      double serumCreatinine, double hemoglobin, double albumin,
                      double sugar, double sodium, double potassium, double packedCellVolume) {
        this.age = age;
        this.bloodPressure = bloodPressure;
        this.bloodGlucoseRandom = bloodGlucoseRandom;
        this.serumCreatinine = serumCreatinine;
        this.hemoglobin = hemoglobin;
        this.albumin = albumin;
        this.sugar = sugar;
        this.sodium = sodium;
        this.potassium = potassium;
        this.packedCellVolume = packedCellVolume;
    }

    // Convert to array for neural network input
    public double[] toArray() {
        return new double[]{
            age, bloodPressure, bloodGlucoseRandom, serumCreatinine,
            hemoglobin, albumin, sugar, sodium, potassium, packedCellVolume
        };
    }

    // Getters and setters
    public double getAge() { return age; }
    public void setAge(double age) { this.age = age; }
    
    public double getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(double bloodPressure) { this.bloodPressure = bloodPressure; }
    
    public double getBloodGlucoseRandom() { return bloodGlucoseRandom; }
    public void setBloodGlucoseRandom(double bloodGlucoseRandom) { this.bloodGlucoseRandom = bloodGlucoseRandom; }
    
    public double getSerumCreatinine() { return serumCreatinine; }
    public void setSerumCreatinine(double serumCreatinine) { this.serumCreatinine = serumCreatinine; }
    
    public double getHemoglobin() { return hemoglobin; }
    public void setHemoglobin(double hemoglobin) { this.hemoglobin = hemoglobin; }
    
    public double getAlbumin() { return albumin; }
    public void setAlbumin(double albumin) { this.albumin = albumin; }
    
    public double getSugar() { return sugar; }
    public void setSugar(double sugar) { this.sugar = sugar; }
    
    public double getSodium() { return sodium; }
    public void setSodium(double sodium) { this.sodium = sodium; }
    
    public double getPotassium() { return potassium; }
    public void setPotassium(double potassium) { this.potassium = potassium; }
    
    public double getPackedCellVolume() { return packedCellVolume; }
    public void setPackedCellVolume(double packedCellVolume) { this.packedCellVolume = packedCellVolume; }
}
