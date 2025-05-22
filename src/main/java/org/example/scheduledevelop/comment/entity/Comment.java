package org.example.scheduledevelop.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.scheduledevelop.common.BaseEntity;
import org.example.scheduledevelop.schedule.entity.Schedule;
import org.example.scheduledevelop.user.entity.User;

@Entity
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(){}

    public Comment(String comment, User user, Schedule schedule){
        this.comment = comment;
        this.user = user;
        this.schedule = schedule;
    }

}
