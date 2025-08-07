package com.mx.microsicmas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TX_SIC_RESOURCE_REQUEST")
@Getter
@Setter
@NoArgsConstructor
public class ResourceRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence_TX_SIC_RESOURCE_REQUEST")
    @SequenceGenerator(name = "id_Sequence_TX_SIC_RESOURCE_REQUEST", sequenceName = "TX_SIC_RESOURCE_REQUEST_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private long id;
    @Column(name = "REQUEST")
    private String request;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "COST")
    private double cost;
    @Column(name = "STATUS_ID")
    private String status;
    @OneToMany(mappedBy = "resourceRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Finding> findings = new ArrayList<>();


}
