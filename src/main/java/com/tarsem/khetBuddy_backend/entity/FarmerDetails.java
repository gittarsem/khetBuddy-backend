package com.tarsem.khetBuddy_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_profile")
public class FarmerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String phoneNo;
    @Column(columnDefinition = "LONGTEXT")
    private String profileImage;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
