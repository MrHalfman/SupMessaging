/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.persistence;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adrienxp3
 */
@Entity
@Table(name = "user_friendship")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserFriendship.findAll", query = "SELECT u FROM UserFriendship u"),
    @NamedQuery(name = "UserFriendship.findById", query = "SELECT u FROM UserFriendship u WHERE u.id = :id")})
public class UserFriendship implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_users2", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users idUsers2;
    @JoinColumn(name = "id_users1", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users idUsers1;

    public UserFriendship() {
    }

    public UserFriendship(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getIdUsers2() {
        return idUsers2;
    }

    public void setIdUsers2(Users idUsers2) {
        this.idUsers2 = idUsers2;
    }

    public Users getIdUsers1() {
        return idUsers1;
    }

    public void setIdUsers1(Users idUsers1) {
        this.idUsers1 = idUsers1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFriendship)) {
            return false;
        }
        UserFriendship other = (UserFriendship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supmessaging.persistence.UserFriendship[ id=" + id + " ]";
    }
    
}
