package eaismart.webapps.sysbanka.entity;

import java.io.Serializable;
import java.util.Date;
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

import eaismart.entity.BaseEntity;
import eaismart.webapps.sysbanka.util.constants.ClientType;
import eaismart.webapps.sysbanka.util.constants.DocType;
import eaismart.webapps.sysbanka.util.constants.Gender;
import eaismart.webapps.sysbanka.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
@Entity
@Table(name="tbl_entity", schema = "public")
public class Entidade extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false) 
	private Long id;

	private String address;
	
	private String name;
	
	private String description; 
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "nr_doc")
	private String nrDoc;
	
	@Column(name = "doc_type")
	@Enumerated(EnumType.STRING)
	private DocType docType;
	
	private String phone;
	
	private String mobile;
	
	private String email; 
	
	@Enumerated(EnumType.STRING)
	private ClientType type;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "member_nr")
	private long memberNr;
	
	@Column(name = "birth_date")
	private Date birthDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "entidade")
	private List<Account> accounts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getNrDoc() {
		return nrDoc;
	}

	public void setNrDoc(String nrDoc) {
		this.nrDoc = nrDoc;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public long getMemberNr() {
		return memberNr;
	}

	public void setMemberNr(long memberNr) {
		this.memberNr = memberNr;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
}
