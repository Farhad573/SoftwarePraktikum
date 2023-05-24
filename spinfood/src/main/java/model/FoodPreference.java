package model;

public enum FoodPreference {
    none(0),
    meat(0),
    veggie(1),
    vegan(2);

    private int value;

    FoodPreference(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
