package eaismart.webapps.sysbanka.dto;

import java.util.Date;

import eaismart.webapps.sysbanka.util.constants.ClientType;
import eaismart.webapps.sysbanka.util.constants.DocType;
import eaismart.webapps.sysbanka.util.constants.Gender;
import eaismart.webapps.sysbanka.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Feb 16, 2021
 */
public class EntidadeDto { 
	
	private Long id;
	private String address;
	private String name;
	private String description; 
	private Status status;
	private String nrDoc;
	private DocType docType;
	private String phone;
	private String mobile;
	private String email; 
	private ClientType type;
	private Gender gender;
	private long memberNr;
	private Date birthDate;
	
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
	
}
