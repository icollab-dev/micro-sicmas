package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TX_SIC_FINDING_STAFF")
@Getter
@Setter
@NoArgsConstructor
public class FindingStaff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_finding_staff")
    @SequenceGenerator(name = "seq_finding_staff", sequenceName = "TX_SIC_FINDING_STAFF_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINDING_ID")
    private Finding finding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAFF_ID")
    private Staff staff;


}
