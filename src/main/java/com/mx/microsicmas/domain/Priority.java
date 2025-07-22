package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CAT_SIC_PRIORITY")
@Getter
@Setter
@NoArgsConstructor
public class Priority extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_CAT_SIC_PRIORITY")
    @SequenceGenerator(name = "id_Sequence_CAT_SIC_PRIORITY", sequenceName = "CAT_SIC_PRIORITY_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
}
