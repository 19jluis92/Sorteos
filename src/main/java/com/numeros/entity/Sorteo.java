/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jluis
 */
@Entity
@Table(name = "sorteo")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Sorteo.findAll", query = "SELECT s FROM Sorteo s"),
        @NamedQuery(name = "Sorteo.findByIdsorteo", query = "SELECT s FROM Sorteo s WHERE s.idsorteo = :idsorteo"),
        @NamedQuery(name = "Sorteo.findByName", query = "SELECT s FROM Sorteo s WHERE s.name = :name") })
public class Sorteo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsorteo")
    private Integer idsorteo;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sorteoId")
    private Collection<Numeros> numerosCollection;

    public Sorteo() {
    }

    public Sorteo(Integer idsorteo) {
        this.idsorteo = idsorteo;
    }

    public Integer getIdsorteo() {
        return idsorteo;
    }

    public void setIdsorteo(Integer idsorteo) {
        this.idsorteo = idsorteo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Numeros> getNumerosCollection() {
        return numerosCollection;
    }

    public void setNumerosCollection(Collection<Numeros> numerosCollection) {
        this.numerosCollection = numerosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsorteo != null ? idsorteo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sorteo)) {
            return false;
        }
        Sorteo other = (Sorteo) object;
        if ((this.idsorteo == null && other.idsorteo != null)
                || (this.idsorteo != null && !this.idsorteo.equals(other.idsorteo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.numeros.entity.Sorteo[ idsorteo=" + idsorteo + " ]";
    }

}
