package dev.shrp.tasks.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String content;

    @Column(nullable = false)
    public String user_id;

    @Column(nullable = false)
    public boolean completed = false;

    @Column(nullable = true)
    public Date completedAt;

    @Column(nullable = false)
    public Date createdAt = new Date();

    @Column(nullable = true)
    public Date updatedAt;
}
