package ru.practicum.ewm.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utils.EventState;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 120)
    private String title;

    @Column(length = 2000)
    private String annotation;

    @Column(length = 7000)
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    private long views;
}
