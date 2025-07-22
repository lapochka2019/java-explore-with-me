package ru.practicum.ewm.location.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "latitude")
    private float latitude;
    @Column(nullable = false, name = "longitude")
    private float longitude;
}
