package org.example.scheduledevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "schedule")
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

    public Schedule() {}

    public Schedule(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }


}
