package eaismart.webapps.sysbanka.dto;

import eaismart.webapps.sysbanka.util.constants.ClientType;
import eaismart.webapps.sysbanka.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class AccountDto {
	
	private Long id;
	private String upload; 
	private ClientType clientType;
	private long accountNumber;
	private long oldAccountNumber;
	private String nib;
	private String description;
	private String agency;
	private String currency;
	private Status status;
	
	private EntidadeDto entidadeDto;
	
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	
	public ClientType getClientType() {
		return clientType;
	}
	
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public EntidadeDto getEntidadeDto() {
		return entidadeDto;
	}
	public void setEntidadeDto(EntidadeDto entidadeDto) {
		this.entidadeDto = entidadeDto;
	}
	
}
