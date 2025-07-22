package com.mx.microsicmas.domain;

import lombok.*;

import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ACTIVE")
    private boolean active;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "USER_CREATED")
    private String userCreated;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_CREATED")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Basic(optional = true)
    @Size(min = 1, max = 45)
    @Column(name = "USER_UPDATED")
    private String userUpdated;

    @Basic(optional = true)
    @Column(name = "DATE_UPDATED")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateUpdated;

    public void setDataCreated(final String user) {
        this.userCreated = user;
        //this.dateCreated = new Timestamp(new Date().getTime());
        this.dateCreated = new Date();
        this.userUpdated = user;
        this.dateUpdated = this.dateCreated;
    }

    public void setDataUpdate(final String user) {
        this.userUpdated = user;
        this.dateUpdated = new Date();
    }

    @PrePersist
    void onPrePersist() {
        setDataCreated(getUsuarioHeaders());
    }

    @PreUpdate
    void onPreUpdate() {
        setDataUpdate(getUsuarioHeaders());
    }

    private static String getUsuarioHeaders() {
        String user = "";
        try {
            user = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getParameter("user");
        } catch (IllegalStateException e) {
            user = "system";
        }
        return user;
    }

}
