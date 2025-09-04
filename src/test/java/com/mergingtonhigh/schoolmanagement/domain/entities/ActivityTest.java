package com.mergingtonhigh.schoolmanagement.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ActivityType;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.DifficultyLevel;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.Email;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ScheduleDetails;

/**
 * Unit tests for Activity domain entity.
 */
class ActivityTest {

    @Test
    void shouldCreateActivityWithValidData() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday", "Wednesday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        // Act
        Activity activity = new Activity(
                "Clube de Xadrez",
                "Aprenda estratégias de xadrez",
                "Seg/Qua 15:30-17:00",
                schedule,
                12,
                ActivityType.ACADEMIC);

        // Assert
        assertEquals("Clube de Xadrez", activity.getName());
        assertEquals("Aprenda estratégias de xadrez", activity.getDescription());
        assertEquals(12, activity.getMaxParticipants());
        assertEquals(0, activity.getCurrentParticipantCount());
        assertTrue(activity.canAddParticipant());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Activity(null, "Descrição", "Horário", schedule, 12, ActivityType.ACADEMIC));
    }

    @Test
    void shouldAddParticipantSuccessfully() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");

        // Act
        activity.addParticipant(studentEmail);

        // Assert
        assertEquals(1, activity.getCurrentParticipantCount());
        assertTrue(activity.isParticipantRegistered(studentEmail));
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateParticipant() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");
        activity.addParticipant(studentEmail);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> activity.addParticipant(studentEmail));
    }

    @Test
    void shouldRemoveParticipantSuccessfully() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");
        activity.addParticipant(studentEmail);

        // Act
        activity.removeParticipant(studentEmail);

        // Assert
        assertEquals(0, activity.getCurrentParticipantCount());
        assertFalse(activity.isParticipantRegistered(studentEmail));
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentParticipant() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> activity.removeParticipant(studentEmail));
    }

    @Test
    void shouldCreateMangaManiacsActivityWithCorrectProperties() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Tuesday"),
                LocalTime.of(19, 0),
                LocalTime.of(20, 0));

        // Act
        Activity mangaManiacs = new Activity(
                "Manga Maniacs",
                "Explore as histórias fantásticas dos personagens mais interessantes dos Mangás japoneses (romances gráficos)",
                "Terças-feiras, 19:00 - 20:00",
                schedule,
                15,
                ActivityType.ARTS);

        // Assert
        assertEquals("Manga Maniacs", mangaManiacs.getName());
        assertEquals("Explore as histórias fantásticas dos personagens mais interessantes dos Mangás japoneses (romances gráficos)", mangaManiacs.getDescription());
        assertEquals(15, mangaManiacs.getMaxParticipants());
        assertEquals(ActivityType.ARTS, mangaManiacs.getType());
        assertEquals(0, mangaManiacs.getCurrentParticipantCount());
        assertTrue(mangaManiacs.canAddParticipant());
        
        // Verify schedule details
        assertEquals(List.of("Tuesday"), mangaManiacs.getScheduleDetails().days());
        assertEquals(LocalTime.of(19, 0), mangaManiacs.getScheduleDetails().startTime());
        assertEquals(LocalTime.of(20, 0), mangaManiacs.getScheduleDetails().endTime());
    }

    @Test
    void shouldCreateActivityWithDifficultyLevel() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday", "Wednesday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        // Act
        Activity activity = new Activity(
                "Advanced Chess Club",
                "Advanced chess strategies and tournament preparation",
                "Seg/Qua 15:30-17:00",
                schedule,
                8,
                ActivityType.ACADEMIC,
                DifficultyLevel.ADVANCED);

        // Assert
        assertEquals("Advanced Chess Club", activity.getName());
        assertEquals(DifficultyLevel.ADVANCED, activity.getDifficultyLevel());
        assertEquals(ActivityType.ACADEMIC, activity.getType());
    }

    @Test
    void shouldCreateActivityWithoutDifficultyLevel() {
        // Arrange & Act
        Activity activity = createTestActivity();

        // Assert
        assertNull(activity.getDifficultyLevel()); // Should be null for all-levels activities
    }

    @Test
    void shouldSetAndGetDifficultyLevel() {
        // Arrange
        Activity activity = createTestActivity();

        // Act
        activity.setDifficultyLevel(DifficultyLevel.INTERMEDIATE);

        // Assert
        assertEquals(DifficultyLevel.INTERMEDIATE, activity.getDifficultyLevel());
    }

    private Activity createTestActivity() {
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        return new Activity(
                "Atividade de Teste",
                "Descrição de teste",
                "Seg 15:30-17:00",
                schedule,
                12,
                ActivityType.ACADEMIC);
    }
}