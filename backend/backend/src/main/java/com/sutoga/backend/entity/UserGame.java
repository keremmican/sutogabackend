package com.sutoga.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name="userGame")
@Getter
@Setter
public class UserGame {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_ID")
    private Game game;

    private int playTime;

}