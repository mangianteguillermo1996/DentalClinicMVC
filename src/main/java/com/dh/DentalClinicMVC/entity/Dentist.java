package com.dh.DentalClinicMVC.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="dentists")
public class Dentist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "dentist_id")
    private Long id;

    @Column(name = "registration")
    private Integer registration;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Appointment> appointment = new HashSet<Appointment>();

    public Dentist() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRegistration() {
        return registration;
    }

    public void setRegistration(Integer registration) {
        this.registration = registration;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Appointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(Set<Appointment> appointment) {
        this.appointment = appointment;
    }
}
