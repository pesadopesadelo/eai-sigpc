/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.webapps.sigpc.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import eaismart.webapps.sigpc.util.constants.Currency; 
import eaismart.webapps.sigpc.util.constants.Status;
import eaismart.webapps.sigpc.util.constants.TransitionType;

/**
 *
 * @author elton
 */
public class MovementDto {

    private String upload;
    private TransitionType type; 

    private Long id;
    private String recipient;
    private String description;
    private String levelDetail;
    private String numDoc;
    private String numMov;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal valor;
    private Date date;
    private Status status;
    private ClassifierDto classifierDto;
    private CompanyDto companyDto;
    private String month;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String codePayments;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDatePayments;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDatePayments;

    private String codeTransferences;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDateTransferences;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDateTransferences;
    
    private List<DomainDto> currencies; 
    private List<DomainDto> months; 
    private Map<Long, String> companies;
    private Long companyId; 
    
    public MovementDto() {
    	currencies = new ArrayList<DomainDto>(); 
    	months = new ArrayList<DomainDto>();
    	companies = new LinkedHashMap<Long, String>(); 
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public TransitionType getType() {
        return type;
    }

    public void setType(TransitionType type) {
        this.type = type;
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

    public String getLevelDetail() {
        return levelDetail;
    }

    public void setLevelDetail(String levelDetail) {
        this.levelDetail = levelDetail;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClassifierDto getClassifierDto() {
        return classifierDto;
    }

    public void setClassifierDto(ClassifierDto classifierDto) {
        this.classifierDto = classifierDto;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MovementDto [upload=" + upload + ", type=" + type + ", id=" + id + ", recipient=" + recipient
                + ", description=" + description + ", levelDetail=" + levelDetail + ", numDoc=" + numDoc + ", numMov="
                + numMov + ", currency=" + currency + ", balance=" + balance + ", date=" + date + ", status=" + status
                + ", classifierDto=" + classifierDto + ", companyDto=" + companyDto + ", beginDate=" + beginDate
                + ", endDate=" + endDate  + "]";
    }

    public String getCodePayments() {
        return codePayments;
    }

    public void setCodePayments(String codePayments) {
        this.codePayments = codePayments;
    }

    public Date getBeginDatePayments() {
        return beginDatePayments;
    }

    public void setBeginDatePayments(Date beginDatePayments) {
        this.beginDatePayments = beginDatePayments;
    }

    public Date getEndDatePayments() {
        return endDatePayments;
    }

    public void setEndDatePayments(Date endDatePayments) {
        this.endDatePayments = endDatePayments;
    }

    public String getCodeTransferences() {
        return codeTransferences;
    }

    public void setCodeTransferences(String codeTransferences) {
        this.codeTransferences = codeTransferences;
    }

    public Date getBeginDateTransferences() {
        return beginDateTransferences;
    }

    public void setBeginDateTransferences(Date beginDateTransferences) {
        this.beginDateTransferences = beginDateTransferences;
    }

    public Date getEndDateTransferences() {
        return endDateTransferences;
    }

    public void setEndDateTransferences(Date endDateTransferences) {
        this.endDateTransferences = endDateTransferences;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

	public List<DomainDto> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<DomainDto> currencies) {
		this.currencies = currencies;
	}

	public List<DomainDto> getMonths() {
		return months;
	}

	public void setMonths(List<DomainDto> months) {
		this.months = months;
	}

	public Map<Long, String> getCompanies() {
		return companies;
	}

	public void setCompanies(Map<Long, String> companies) {
		this.companies = companies;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	} 
	
	
}
