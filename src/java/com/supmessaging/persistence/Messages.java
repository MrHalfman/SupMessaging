/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.persistence;

import com.supmessaging.tools.ComplexRequest;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adrienxp3
 */
@Entity
@Table(name = "messages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m"),
    @NamedQuery(name = "Messages.findById", query = "SELECT m FROM Messages m WHERE m.id = :id"),
    @NamedQuery(name = "Messages.findByDateMessage", query = "SELECT m FROM Messages m WHERE m.dateMessage = :dateMessage"),
    @NamedQuery(name = "Messages.findByIdUserAuthor", query = "SELECT m FROM Messages m WHERE m.idUserAuthor = :idUserAuthor"),
    @NamedQuery(name = "Messages.findByIdUserReceiver", query = "SELECT m FROM Messages m WHERE m.idUserReceiver = :idUserReceiver"),
    @NamedQuery(name = "Messages.findByMail", query = "SELECT m FROM Messages m WHERE m.mail = :mail"),
    @NamedQuery(name = "Messages.findByReadMessage", query = "SELECT m FROM Messages m WHERE m.readMessage = :readMessage")})
public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "dateMessage")
    private String dateMessage;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "corpus")
    private String corpus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user_author")
    private int idUserAuthor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user_receiver")
    private int idUserReceiver;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mail")
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "readMessage")
    private int readMessage;

    public Messages() {
    }

    public Messages(Integer id) {
        this.id = id;
    }

    public Messages(Integer id, String dateMessage, String corpus, int idUserAuthor, int idUserReceiver, String mail, int readMessage) {
        this.id = id;
        this.dateMessage = dateMessage;
        this.corpus = corpus;
        this.idUserAuthor = idUserAuthor;
        this.idUserReceiver = idUserReceiver;
        this.mail = mail;
        this.readMessage = readMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getCorpus() {
        return corpus;
    }

    public void setCorpus(String corpus) {
        this.corpus = corpus;
    }

    public int getIdUserAuthor() {
        return idUserAuthor;
    }

    public void setIdUserAuthor(int idUserAuthor) {
        this.idUserAuthor = idUserAuthor;
    }

    public int getIdUserReceiver() {
        return idUserReceiver;
    }

    public void setIdUserReceiver(int idUserReceiver) {
        this.idUserReceiver = idUserReceiver;
    }
    
    public String getAuthorName() {
        ComplexRequest request = new ComplexRequest();
        Users username = request.getUserById(idUserAuthor);
       
        return username.getPseudo();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getReadMessage() {
        return readMessage;
    }

    public void setReadMessage(int readMessage) {
        this.readMessage = readMessage;
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
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.supmessaging.persistence.Messages[ id=" + id + " ]";
    }
    
}
