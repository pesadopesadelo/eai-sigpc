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
@Table(name = "tbl_classifier", schema = "public")
public class Classifier extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(length = 50)
    @Size(max = 50, message = "Name must contain a maximum of 50 characters")
    private String name;

    @Column(length = 50)
    @Size(max = 50, message = "Code must contain a maximum of 50 characters")
    private String code;

    @Column(length = 100)
    @Size(max = 100, message = "Description must contain a maximum of 100 characters")
    private String description;

    @Column(length = 10)
    @Size(max = 10, message = "Type must contain a maximum of 10 characters")
    private String type;

    @Column(name = "self_id")
    private long selfId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classifier")
    private List<Movement> movement;

    public Classifier() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSelfId() {
        return selfId;
    }

    public void setSelfId(long selfId) {
        this.selfId = selfId;
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
