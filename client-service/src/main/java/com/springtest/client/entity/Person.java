package com.springtest.client.entity;

import com.springtest.client.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person", schema = "client-service")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification", unique = true, nullable = false)
    private String identification;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

}
