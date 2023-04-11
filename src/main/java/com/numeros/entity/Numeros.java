/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jluis
 */
@Entity
@Table(name = "numeros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Numeros.findAll", query = "SELECT n FROM Numeros n"),
    @NamedQuery(name = "Numeros.findByIdnumeros", query = "SELECT n FROM Numeros n WHERE n.idnumeros = :idnumeros"),
    @NamedQuery(name = "Numeros.findByNumero", query = "SELECT n FROM Numeros n WHERE n.numero = :numero"),
    @NamedQuery(name = "Numeros.findByNumeroSorteo", query = "SELECT n FROM Numeros n WHERE n.numeroSorteo = :numeroSorteo"),
    @NamedQuery(name = "Numeros.findByDate", query = "SELECT n FROM Numeros n WHERE n.date = :date")})
public class Numeros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnumeros")
    private Integer idnumeros;
    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @Column(name = "numeroSorteo")
    private int numeroSorteo;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "sorteoId", referencedColumnName = "idsorteo")
    @ManyToOne(optional = false)
    private Sorteo sorteoId;

    public Numeros() {
    }

    public Numeros(Integer idnumeros) {
        this.idnumeros = idnumeros;
    }

    public Numeros(Integer idnumeros, String numero, int numeroSorteo) {
        this.idnumeros = idnumeros;
        this.numero = numero;
        this.numeroSorteo = numeroSorteo;
    }

    public Integer getIdnumeros() {
        return idnumeros;
    }

    public void setIdnumeros(Integer idnumeros) {
        this.idnumeros = idnumeros;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getNumeroSorteo() {
        return numeroSorteo;
    }

    public void setNumeroSorteo(int numeroSorteo) {
        this.numeroSorteo = numeroSorteo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Sorteo getSorteoId() {
        return sorteoId;
    }

    public void setSorteoId(Sorteo sorteoId) {
        this.sorteoId = sorteoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnumeros != null ? idnumeros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Numeros)) {
            return false;
        }
        Numeros other = (Numeros) object;
        if ((this.idnumeros == null && other.idnumeros != null) || (this.idnumeros != null && !this.idnumeros.equals(other.idnumeros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.numeros.entity.Numeros[ idnumeros=" + idnumeros + " ]";
    }
    
    
    
    public int getpos1(){
    
       
        return Integer.parseInt(this.numero.substring(0, 1));
    }
    
    public Integer getposNumero1(){
    int temp = Integer.parseInt(this.numero.substring(0, 2));
        return Integer.parseInt(this.numero.substring(0, 2));
    }
    
    public Integer getposNumero2(){
    int temp = Integer.parseInt(this.numero.substring(2, 4));
        return Integer.parseInt(this.numero.substring(2, 4));
    }
    
    public Integer getposNumero3(){
    
        return Integer.parseInt(this.numero.substring(4, 6));
    }
    
    public Integer getposNumero4(){
    
        return Integer.parseInt(this.numero.substring(6, 8));
    }
    
    public Integer getposNumero5(){
    
        return Integer.parseInt(this.numero.substring(8, 10));
    }
    
    public Integer getposNumero6(){
    
        return Integer.parseInt(this.numero.substring(10, 12));
    }
}
