package com.mergingtonhigh.schoolmanagement.domain.valueobjects;

/**
 * Enum representing the different difficulty levels for activities.
 * Activities can be targeted at different experience levels.
 */
public enum DifficultyLevel {
    BEGINNER("Iniciante"),
    INTERMEDIATE("Intermediário"), 
    ADVANCED("Avançado");

    private final String label;

    DifficultyLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}