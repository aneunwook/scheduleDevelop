package org.example.scheduledevelop.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.scheduledevelop.common.BaseEntity;
import org.example.scheduledevelop.user.entity.User;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    public Schedule() {}

    public Schedule(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

}
