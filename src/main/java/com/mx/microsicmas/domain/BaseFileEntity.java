package com.mx.microsicmas.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@MappedSuperclass
public class BaseFileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
        this.dateCreated = new Timestamp(new Date().getTime());
    }

    public void setDataUpdate(final String user) {
        this.userUpdated = user;
        this.dateUpdated = new Date();
    }

    @PrePersist
    void  onPrePersist() {
        setDataCreated(getUsuarioHeaders());
    }

    @PreUpdate void onPreUpdate() {
        setDataUpdate(getUsuarioHeaders());
    }

    private String getUsuarioHeaders(){
        String usuario = "system";
        try{
            String tokenAWS =  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if(tokenAWS != null && tokenAWS.indexOf("|") != -1){
                String [] token = tokenAWS.split("|");
                usuario = token[1];
            }
        } catch(Exception e) {
            usuario = "system";
        }

        return usuario;
    }
}
