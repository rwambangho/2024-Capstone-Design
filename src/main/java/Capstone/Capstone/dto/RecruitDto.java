package Capstone.Capstone.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RecruitDto {
    private String title;
    private String contents;
    private String nickname;
    private int distance;
    private Long id;
    private int star=0;
    private double avgStar=0.0;
    private List<String> keywords;
    private String destination;
    private String departure;
    private LocalDate departureDate;
    private LocalDateTime createdAt;
    private String message;
    private int participant;
    private int maxParticipant;
    private List<String> users;
    private List<String> bookingUsers;
    private boolean isDriverPost;
    private double  departureX;
    private double  departureY;
    private double arrivalX;
    private double arrivalY;
}

