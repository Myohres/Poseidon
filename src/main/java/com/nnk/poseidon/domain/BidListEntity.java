package com.nnk.poseidon.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Time;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class BidListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bidListId;
    private String account;
    private String type;
    private double bidQuantity;
    private double askQuantity;
    private double bid;
    private double ask;
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
