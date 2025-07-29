package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAT_STAFF")
@Getter
@Setter
@NoArgsConstructor
public class Staff extends BaseEntity {
    @Id
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FIRSTLASTNAME")
    private String firstlastname;
    @Column(name = "SECONDLASTNAME")
    private String secondlastname;
    @Column(name = "COMPANY")
    private String company;
}
