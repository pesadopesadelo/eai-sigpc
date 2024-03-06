package eaismart.webapps.sysbanka.entity;

import java.io.Serializable;

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

import eaismart.entity.BaseEntity;
import eaismart.webapps.sysbanka.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
@Entity
@Table(name="tbl_account", schema = "public")
public class Account extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false) 
	private Long id;

	@Column(nullable = false, name = "account_number")
	private long accountNumber;
	
	@Column(nullable = false, name = "old_account_number")
	private long oldAccountNumber;
	
	@Column(length = 100)
	private String nib;
	
	private String description;
	
	@Column(nullable = false)
	private String agency;
	
	private String currency;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entidade_fk")
	private Entidade entidade; 
	
	@Enumerated(EnumType.STRING)
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getOldAccountNumber() {
		return oldAccountNumber;
	}

	public void setOldAccountNumber(long oldAccountNumber) {
		this.oldAccountNumber = oldAccountNumber;
	}

	public String getNib() {
		return nib;
	}

	public void setNib(String nib) {
		this.nib = nib;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
