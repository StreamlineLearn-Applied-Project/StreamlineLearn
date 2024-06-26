package com.StreamlineLearn.UserManagement.model;

import com.StreamlineLearn.UserManagement.enums.Education;
import com.StreamlineLearn.UserManagement.enums.Field;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Education education;

    @Enumerated(value = EnumType.STRING)
    private Field field;
}
