package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAT_SIC_RULES")
@Getter
@Setter
@NoArgsConstructor
public class Rule extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_CAT_SIC_RULES")
    @SequenceGenerator(name = "id_Sequence_CAT_SIC_RULES", sequenceName = "CAT_SIC_RULES_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "rule", fetch = FetchType.LAZY)
    private List<Planning> plannings = new ArrayList<>();

}
