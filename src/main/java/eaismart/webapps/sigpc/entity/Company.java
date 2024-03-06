package eaismart.webapps.sigpc.entity;

import eaismart.entity.BaseEntity;
import eaismart.webapps.sigpc.util.constants.Status;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author eai-smart
 */

@Entity
@Table(name = "tbl_company", schema = "public")
public class Company extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(length = 100)
    @Size(max = 100, message = "Name must contain a maximum of 100 characters")
    private String name;

    @Column(length = 50)
    @Size(max = 50, message = "Account must contain a maximum of 50 characters")
    private String account;

    @Column(length = 20)
    @Size(max = 20, message = "Nif must contain a maximum of 20 characters")
    private int nif;

    @Column(length = 100)
    @Size(max = 100, message = "Address must contain a maximum of 100 characters")
    private String address;

    @Column(length = 30)
    @Size(max = 30, message = "Country must contain a maximum of 30 characters")
    private String country;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<Movement> movement;
    
    @Column(length = 30)
    private String phone;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 255)
    private String website;

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Movement> getMovement() {
        return movement;
    }

    public void setMovement(List<Movement> movement) {
        this.movement = movement;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
