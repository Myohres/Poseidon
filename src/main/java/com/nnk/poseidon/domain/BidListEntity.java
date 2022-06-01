package com.nnk.poseidon.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Time;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class BidListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;
    @NotEmpty(message = "Account is mandatory")
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotEmpty(message = "Type is mandatory")
    @NotBlank(message = "Type is mandatory")
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
