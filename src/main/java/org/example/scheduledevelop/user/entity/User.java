package org.example.scheduledevelop.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.scheduledevelop.common.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;

    public User(){}

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }


}
