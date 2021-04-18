package com.latihan.posify.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordResetToken() {
    }

    public PasswordResetToken(Long id, String token, User user) {
        this.id = id;
        this.token = token;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
