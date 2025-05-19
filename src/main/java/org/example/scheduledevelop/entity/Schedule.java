package org.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;
}
