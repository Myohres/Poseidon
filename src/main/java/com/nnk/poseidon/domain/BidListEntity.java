package com.nnk.poseidon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Calendar;


@Entity
@Getter
@Setter
@Table(name = "bidlist")
public class BidListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;
    @NotBlank(message = "Account is mandatory")
    private String account;
    @NotEmpty
    @NotBlank(message = "Type is mandatory")
    private String type;
    @NotNull
    @PositiveOrZero
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Date bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Date creationDate;
    private String revisionName;
    private Date revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public BidListEntity() {
    }

    public BidListEntity(String account, String type, Double bidQuantity) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date Date = new Date(cal.getTime().getTime());

        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
        this.askQuantity = 0d;
        this.bid = 0d;
        this.ask = 0d;
        this.benchmark = "defaultValue";
        this.bidListDate = Date;
        this.commentary = "defaultValue";
        this.security = "defaultValue";
        this.status = "defaultValue";
        this.trader = "defaultValue";
        this.book = "defaultValue";
        this.creationName = "defaultValue";
        this.creationDate = Date;
        this.revisionName = "defaultValue";
        this.revisionDate = Date;
        this.dealName = "defaultValue";
        this.dealType = "defaultValue";
        this.sourceListId = "defaultValue";
        this.side = "defaultValue";
    }

}
