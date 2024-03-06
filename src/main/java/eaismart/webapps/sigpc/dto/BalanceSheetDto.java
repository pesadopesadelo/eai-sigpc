package eaismart.webapps.sigpc.dto;

import eaismart.webapps.sigpc.util.constants.BalanceSheetType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Iekiny Marcel Feb 16, 2021
 */

public class BalanceSheetDto {

    private Long id;

    private String ecoClassifier;
    private String description;
    private Long company;
    private Map<Long, String> companies;
    private List<DomainDto> years;
    private BigDecimal initialBudget;
    private BigDecimal finalBudget;
    private BigDecimal moneyTransfer;
    private BigDecimal quarterValue;
    private BigDecimal totalExpenses;
    private BigDecimal totalRevenue;
    private BigDecimal totalEntry;
    private BigDecimal totalOutput;
    private BigDecimal FinalBalance;
    private BigDecimal InitialBalance;
    private BalanceSheetType type;
    private String month;
    private String year;

    public BalanceSheetDto() {
        companies = new LinkedHashMap<>();
        years = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(BigDecimal moneyTransfer) {
        this.moneyTransfer = moneyTransfer;
    }

    public BalanceSheetType getType() {
        return type;
    }

    public void setType(BalanceSheetType type) {
        this.type = type;
    }

    public String getEcoClassifier() {
        return ecoClassifier;
    }

    public void setEcoClassifier(String ecoClassifier) {
        this.ecoClassifier = ecoClassifier;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getQuarterValue() {
        return quarterValue;
    }

    public void setQuarterValue(BigDecimal quarterValue) {
        this.quarterValue = quarterValue;
    }

    public BigDecimal getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(BigDecimal initialBudget) {
        this.initialBudget = initialBudget;
    }

    public BigDecimal getFinalBudget() {
        return finalBudget;
    }

    public void setFinalBudget(BigDecimal finalBudget) {
        this.finalBudget = finalBudget;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalEntry() {
        return totalEntry;
    }

    public void setTotalEntry(BigDecimal totalEntry) {
        this.totalEntry = totalEntry;
    }

    public BigDecimal getTotalOutput() {
        return totalOutput;
    }

    public void setTotalOutput(BigDecimal totalOutput) {
        this.totalOutput = totalOutput;
    }

    public BigDecimal getFinalBalance() {
        return FinalBalance;
    }

    public void setFinalBalance(BigDecimal FinalBalance) {
        this.FinalBalance = FinalBalance;
    }

    public BigDecimal getInitialBalance() {
        return InitialBalance;
    }

    public void setInitialBalance(BigDecimal InitialBalance) {
        this.InitialBalance = InitialBalance;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public List<DomainDto> getYears() {
        return years;
    }

    public void setYears(List<DomainDto> years) {
        this.years = years;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public Map<Long, String> getCompanies() {
        return companies;
    }

    public void setCompanies(Map<Long, String> companies) {
        this.companies = companies;
    }

}
