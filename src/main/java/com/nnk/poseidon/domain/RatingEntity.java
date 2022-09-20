package com.nnk.poseidon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "rating")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "MoodysRating mandatory")
    private String moodysRating;
    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;
    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;
    @NotNull(message = "Order is mandatory")
    private Integer orderNumber;
}
