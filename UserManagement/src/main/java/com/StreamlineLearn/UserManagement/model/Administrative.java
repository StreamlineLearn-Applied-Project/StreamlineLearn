package com.StreamlineLearn.UserManagement.model;

import jakarta.persistence.*;


@Entity
public class Administrative  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AdministrativePosition position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AdministrativePosition getPosition() {
        return position;
    }

    public void setPosition(AdministrativePosition position) {
        this.position = position;
    }
}
