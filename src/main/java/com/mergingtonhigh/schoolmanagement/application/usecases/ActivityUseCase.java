package com.mergingtonhigh.schoolmanagement.application.usecases;

import com.mergingtonhigh.schoolmanagement.application.dtos.ActivityDTO;
import com.mergingtonhigh.schoolmanagement.domain.entities.Activity;
import com.mergingtonhigh.schoolmanagement.domain.repositories.ActivityRepository;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.DifficultyLevel;
import com.mergingtonhigh.schoolmanagement.presentation.mappers.ActivityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Use case for managing activities.
 */
@Service
public class ActivityUseCase {
    
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    
    public ActivityUseCase(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }
    
    /**
     * Get all activities with optional filtering.
     */
    public Map<String, ActivityDTO> getActivities(String day, String startTime, String endTime, String difficultyLevel) {
        List<Activity> activities;
        
        if (day != null || startTime != null || endTime != null) {
            LocalTime start = startTime != null ? LocalTime.parse(startTime) : null;
            LocalTime end = endTime != null ? LocalTime.parse(endTime) : null;
            
            if (day != null && start != null && end != null) {
                activities = activityRepository.findByDayAndTimeRange(day, start, end);
            } else if (day != null) {
                activities = activityRepository.findByDay(day);
            } else if (start != null && end != null) {
                activities = activityRepository.findByTimeRange(start, end);
            } else {
                activities = activityRepository.findAll();
            }
        } else {
            activities = activityRepository.findAll();
        }
        
        // Apply difficulty level filtering if specified
        if (difficultyLevel != null && !difficultyLevel.isEmpty()) {
            activities = applyDifficultyFilter(activities, difficultyLevel);
        }
        
        return activities.stream()
                .collect(Collectors.toMap(
                    Activity::getName,
                    activityMapper::toDTO
                ));
    }

    /**
     * Overloaded method for backward compatibility.
     */
    public Map<String, ActivityDTO> getActivities(String day, String startTime, String endTime) {
        return getActivities(day, startTime, endTime, null);
    }

    /**
     * Apply difficulty level filtering to activities.
     */
    private List<Activity> applyDifficultyFilter(List<Activity> activities, String difficultyLevel) {
        if ("all".equalsIgnoreCase(difficultyLevel)) {
            // "all" means show only activities without specific difficulty level
            return activities.stream()
                    .filter(activity -> activity.getDifficultyLevel() == null)
                    .collect(Collectors.toList());
        }
        
        try {
            DifficultyLevel targetLevel = DifficultyLevel.valueOf(difficultyLevel.toUpperCase());
            return activities.stream()
                    .filter(activity -> targetLevel.equals(activity.getDifficultyLevel()))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            // Invalid difficulty level, return all activities
            return activities;
        }
    }
    
    /**
     * Get available days for activities.
     */
    public List<String> getAvailableDays() {
        return activityRepository.findAllUniqueDays()
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }
}