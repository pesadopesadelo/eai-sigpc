package eaismart.webapps.sigpc.dto;
 

import eaismart.webapps.sigpc.util.constants.Status;

/**
 * @author Iekiny Marcel
 * Feb 16, 2021
 */
public class CompanyDto {
	
	    private Long id;
	    private String name;
	    private String account;
	    private int nif;
	    private String address;
	    private String country;
	    private Status status;
	    
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
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}

}
