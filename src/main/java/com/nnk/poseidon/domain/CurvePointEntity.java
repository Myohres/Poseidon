package com.nnk.poseidon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "curvepoint")
public class CurvePointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull(message = "CurvePointId is mandatory")
    private Integer curveId;
    private Timestamp asOfDate;
    @NotNull(message = "Term is mandatory")
    private Double term;
    @NotNull(message = "Value is mandatory")
    private Double value;
    private Timestamp creationDate;
}
