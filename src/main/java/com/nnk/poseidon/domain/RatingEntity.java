package com.nnk.poseidon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private int orderNumber;
}
