/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.webapps.sigpc.service;

import eaismart.webapps.sigpc.dto.BalanceSheetDto;
import eaismart.webapps.sigpc.dto.DomainDto;
import eaismart.webapps.sigpc.entity.Classifier;
import eaismart.webapps.sigpc.entity.Company;
import eaismart.webapps.sigpc.entity.Movement;
import eaismart.webapps.sigpc.repository.IClassifierRepository;
import eaismart.webapps.sigpc.repository.ICompanyRepository;
import eaismart.webapps.sigpc.repository.IDomainRepository;
import eaismart.webapps.sigpc.repository.IMovementRepository;
import eaismart.webapps.sigpc.util.constants.Month;
import eaismart.webapps.sigpc.util.constants.Status;
import eaismart.webapps.sigpc.util.constants.TransitionType;
import eaismart.webapps.sigpc.util.constants.Year;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author elton
 */
@Service
public class BalanceSheetService {

    @Autowired
    private IMovementRepository movementRepository;

    @Autowired
    private IClassifierRepository classifierRepository;
    
    @Autowired
    private ICompanyRepository companyRepository;

    @Autowired
    private IDomainRepository domainRepository;

    public List<BalanceSheetDto> getAllBalanceSheetByMonthly(BalanceSheetDto balanceSheetDto) {

        Month month = Month.getMonthByValue(balanceSheetDto.getMonth());
        List<BalanceSheetDto> balanceSheetDtoList = new ArrayList<BalanceSheetDto>();
        List<Classifier> classifierList = this.classifierRepository.findByStatus(Status.ATIVO);
        List<Movement> movementList = this.movementRepository.findByMonth(month);
        BalanceSheetDto _balanceSheetDto = null;

        if (!movementList.isEmpty()) {
            for (Classifier classifier : classifierList) {
                _balanceSheetDto = new BalanceSheetDto();
                BigDecimal somaFinalBudget = BigDecimal.ZERO;
                BigDecimal somaInitialBudget = BigDecimal.ZERO;

                for (Movement movement : movementList) {
                    if (classifier.getId().compareTo(movement.getClassifier().getId()) == 0
                            && movement.getCompany().getId().compareTo(balanceSheetDto.getCompany()) == 0
                            && movement.getDate().toString().contains(balanceSheetDto.getYear())
                            && movement.getMonth().equals(month)) {
                        if (movement.getType().equals(TransitionType.CREDIT)) {
                            somaFinalBudget = somaFinalBudget.add(movement.getValor());
                        } else if (movement.getType().equals(TransitionType.DEBIT)) {
                            somaInitialBudget = somaInitialBudget.add(movement.getValor());
                        }
                    }
                }
                _balanceSheetDto.setEcoClassifier(classifier.getCode());
                _balanceSheetDto.setDescription(classifier.getName());
                _balanceSheetDto.setFinalBudget(somaFinalBudget);
                _balanceSheetDto.setInitialBudget(somaInitialBudget);
                balanceSheetDtoList.add(_balanceSheetDto);
            }
        }
        return balanceSheetDtoList;
    }

    public List<BalanceSheetDto> getAllTotalBalanceSheetValueByMonthy(BalanceSheetDto balanceSheetDto) throws Exception {

        Month month = null;
        month = Month.getMonthByValue(balanceSheetDto.getMonth());
        List<BalanceSheetDto> listBalanceSheetDto = getAllBalanceSheetByMonthly(balanceSheetDto);
        List<Movement> movementList = null;
        List<BalanceSheetDto> list = new ArrayList<>();

        BigDecimal somaTotalExp = BigDecimal.ZERO;
        BigDecimal somaTotalRev = BigDecimal.ZERO;
        BalanceSheetDto _balanceSheetDto = new BalanceSheetDto();

        for (BalanceSheetDto balance : listBalanceSheetDto) {
            somaTotalExp = somaTotalExp.add(balance.getFinalBudget());
            somaTotalRev = somaTotalRev.add(balance.getInitialBudget());
        }

        _balanceSheetDto.setTotalExpenses(somaTotalExp);
        _balanceSheetDto.setTotalRevenue(somaTotalRev);

        movementList = this.movementRepository.findByMonth(month);
            for (Movement movement : movementList) {
                if (movementList.size() > 0 && movement.getDate().toString().contains(balanceSheetDto.getYear().toString())
                        && movement.getCompany().getId().compareTo(balanceSheetDto.getCompany()) == 0
                        && movement.getMonth().equals(month)) {
                    _balanceSheetDto.setFinalBalance(movementList.get(movementList.size() - 1).getBalance());
                }
            }

            if (month != null) {
                month = Month.returnMonth(month);
                movementList = this.movementRepository.findByMonth(month);
                if (movementList.isEmpty()) {
                    _balanceSheetDto.setInitialBalance(BigDecimal.ZERO);
                } else {
                    for (Movement movement : movementList) {
                        if (movement.getMonth().equals(Month.DECEMBER)) {
                            if (movement.getCompany().getId().compareTo(balanceSheetDto.getCompany()) == 0
                                    && movement.getDate().toString().contains(balanceSheetDto.getYear())) {
                                _balanceSheetDto.setInitialBalance(movementList.get(movementList.size() - 1).getBalance());
                            } else {
                                _balanceSheetDto.setInitialBalance(BigDecimal.ZERO);
                            }
                        } else if (movement.getCompany().getId().compareTo(balanceSheetDto.getCompany()) == 0
                                && movement.getDate().toString().contains(balanceSheetDto.getYear())
                                && movement.getMonth().equals(month)) {
                            _balanceSheetDto.setInitialBalance(movementList.get(movementList.size() - 1).getBalance());
                        } else {
                            _balanceSheetDto.setInitialBalance(BigDecimal.ZERO);
                        }
                    }
                }
            }
        
        _balanceSheetDto.setTotalEntry(_balanceSheetDto.getInitialBalance().add(somaTotalRev));
        _balanceSheetDto.setTotalOutput(_balanceSheetDto.getInitialBalance().add(somaTotalRev));
        list.add(_balanceSheetDto);
        return list;
    }

    public Long valueOf(Long value) {

        value = value - 1;

        return value;
    }

    public void loadComanyNames(BalanceSheetDto balanceSheetDto) throws Exception {
        Map<Long, String> map = this.companyRepository.findAll().stream().collect(Collectors.toMap(Company::getId, Company::getName));
        if (map != null && !map.isEmpty()) {
            balanceSheetDto.getCompanies().putAll(map);
        }
    }

    public void loadYearsFromDomain(BalanceSheetDto balanceSheetDto) {
        this.domainRepository.findByDominioAndEstado(Year.class.getSimpleName().toUpperCase(), Status.ATIVO).forEach(domain -> {
            DomainDto domainDto = new DomainDto();
            new ModelMapper().map(domain, domainDto);
            balanceSheetDto.getYears().add(domainDto);
        });
        balanceSheetDto.getYears().sort(Comparator.comparing(DomainDto::getId));
    }
}
