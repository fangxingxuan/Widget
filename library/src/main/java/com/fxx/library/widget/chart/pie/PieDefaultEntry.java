package com.fxx.library.widget.chart.pie;

/**
 * IPieEntry的默认实现
 * Created by wsl on 17/5/25.
 */

public class PieDefaultEntry implements IPieEntry{

    private float value;
    private final String label;

    public final int color;

    public PieDefaultEntry(float value, String description, int color) {
        this.value = value;
        this.label = description;
        this.color = color;
    }



    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public float getValue() {
        return this.value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }



    @Override
    public String toString() {
        return "PieDefaultEntry{" +
                "value=" + value +
                ", label=" + label +
                ", color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PieDefaultEntry that = (PieDefaultEntry) o;

        if (Float.compare(that.value, value) != 0) return false;
        if (color != that.color) return false;
        return label != null ? label.equals(that.label) : that.label == null;

    }

    @Override
    public int hashCode() {
        int result = (value != +0.0f ? Float.floatToIntBits(value) : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + color;
        return result;
    }
}
