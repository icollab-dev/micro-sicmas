package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CAT_SIC_ADITORY_CLASSIFICATION")
@Getter
@Setter
@NoArgsConstructor
public class ClassificationAudit extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_CAT_SIC_ADITORY_CLASSIFICATION")
    @SequenceGenerator(name = "id_Sequence_CAT_SIC_ADITORY_CLASSIFICATION", sequenceName = "CAT_SIC_ADITORY_CLASSIFICATION_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "AUDITORY")
    private Boolean auditory;
    @Column(name = "FIND")
    private Boolean find;

}
