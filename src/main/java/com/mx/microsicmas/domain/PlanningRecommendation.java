package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TX_SIC_PLANNING_RECOMMENDATION")
@Getter
@Setter
@NoArgsConstructor
public class PlanningRecommendation extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_TX_SIC_PLANNING_RECOMMENDATION")
    @SequenceGenerator(name = "id_Sequence_TX_SIC_PLANNING_RECOMMENDATION", sequenceName = "TX_SIC_PLANNING_RECOMMENDATION_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNING_ID", nullable = false)
    private Planning planning;
    @Column(name = "RECOMMENDATION")
    private String recommendation;
    @Column(name = "NAME")
    private String name;

}
