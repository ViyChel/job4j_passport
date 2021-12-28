package ru.job4j.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "series", nullable = false, unique = true)
    private String series;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

}