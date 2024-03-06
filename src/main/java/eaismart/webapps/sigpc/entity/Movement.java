package eaismart.webapps.sigpc.entity;

import eaismart.entity.BaseEntity;
import eaismart.webapps.sigpc.util.constants.Currency;
import eaismart.webapps.sigpc.util.constants.Month;
import eaismart.webapps.sigpc.util.constants.Status;
import eaismart.webapps.sigpc.util.constants.TransitionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 *
 * @author eai-smart
 */
@Entity
@Table(name = "tbl_movement", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = { "status", "num_mov", "company_fk"}, name = "uk_tbl_movement"))
public class Movement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(length = 100)
    @Size(max = 100, message = "Recipient must contain a maximum of 100 characters")
    private String recipient;

    @Column(length = 100)
    @Size(max = 100, message = "Description must contain a maximum of 100 characters")
    private String description;

    @Column(length = 50, name = "num_doc")
    @Size(max = 50, message = "Num Doc must contain a maximum of 50 characters")
    private String numDoc;

    @Column(length = 50, name = "num_mov")
    @Size(max = 50, message = "Num Mov must contain a maximum of 50 characters")
    private String numMov;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TransitionType type;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal balance;
    private BigDecimal valor;

    private Date date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_fk")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classifier_fk")
    private Classifier classifier;

    public Movement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getNumMov() {
        return numMov;
    }

    public void setNumMov(String numMov) {
        this.numMov = numMov;
    }

    public TransitionType getType() {
        return type;
    }

    public void setType(TransitionType type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
