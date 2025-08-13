package com.mx.microsicmas.domain;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TX_SIC_FINDING_ACTION")
@Getter
@Setter
@NoArgsConstructor
public class FindingAction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_finding_action")
    @SequenceGenerator(name = "seq_finding_action", sequenceName = "TX_SIC_FINDING_ACTION_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINDING_ID", nullable = false) 
    private Finding finding;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ESTATUS_ID")
    private String statusId;
    @Column(name = "TARGET_DATE")
    private Date targetDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "RESPONSABLE")
    private String responsable;
    @Column(name = "OBSERVATION")
    private String observation;

}
