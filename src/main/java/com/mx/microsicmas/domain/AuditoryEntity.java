package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAT_SIC_AUDITORY_ENTITY")
@Getter
@Setter
@NoArgsConstructor
public class AuditoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_CAT_SIC_ADITORY_ENTITY")
    @SequenceGenerator(name = "id_Sequence_CAT_SIC_ADITORY_ENTITY", sequenceName = "CAT_SIC_ADITORY_ENTITY_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "auditoryEntity", fetch = FetchType.LAZY)
    private List<Planning> plannings = new ArrayList<>();
}
