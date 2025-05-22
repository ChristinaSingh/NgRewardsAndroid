package main.com.ngrewards.Models;

public class ChartData {
    private float value;
    private String label;

    public ChartData(float value, String label) {
        this.value = value;
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
