package eaismart.webapps.sigpc.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eaismart.util.file.excel.ExcelFile;
import eaismart.webapps.sigpc.dto.ClassifierDto;
import eaismart.webapps.sigpc.dto.CompanyDto;
import eaismart.webapps.sigpc.dto.DomainDto;
import eaismart.webapps.sigpc.dto.MovementDto;
import eaismart.webapps.sigpc.entity.Classifier;
import eaismart.webapps.sigpc.entity.Company;
import eaismart.webapps.sigpc.entity.Movement;
import eaismart.webapps.sigpc.repository.IClassifierRepository;
import eaismart.webapps.sigpc.repository.ICompanyRepository;
import eaismart.webapps.sigpc.repository.IDomainRepository;
import eaismart.webapps.sigpc.repository.IMovementRepository;
import eaismart.webapps.sigpc.util.constants.Currency; 
import eaismart.webapps.sigpc.util.constants.Month;
import eaismart.webapps.sigpc.util.constants.Status;
import eaismart.webapps.sigpc.util.constants.TransitionType;

/**
 *
 * @author elton
 */
@Service
public class MovementService {

    @Autowired
    private IMovementRepository movementRepository;

    @Autowired
    private IClassifierRepository classifierRepository;
    
    @Autowired
    private IDomainRepository domainRepository; 
    
    @Autowired
    private ICompanyRepository companyRepository; 

    public Movement save(Movement movement) {
        return movementRepository.save(movement);
    }
	
	public List<MovementDto> getAllRecievementMovements(){
		List<MovementDto> recebimentos = new ArrayList<MovementDto>(); 
		List<Movement> movementsRecebimentos = this.movementRepository.findByType(TransitionType.DEBIT); 
		movementsRecebimentos.forEach(movement->{
			MovementDto movementRow = new MovementDto();
			new ModelMapper().map(movement, movementRow);
			movementRow.setClassifierDto(new ClassifierDto());
			new ModelMapper().map(movement.getClassifier(), movementRow.getClassifierDto());
			recebimentos.add(movementRow); 
		});
		return recebimentos;
	}
	
	public List<MovementDto> getAllRecievementMovements(MovementDto filter){
		List<MovementDto> recebimentos = new ArrayList<MovementDto>();  
		List<Movement> movementsRecebimentos = this.movementRepository.findAll(defineFilters(filter.getType(), filter.getNumDoc(), filter.getBeginDate(), filter.getEndDate()));
		movementsRecebimentos.forEach(movement->{
			MovementDto movementRow = new MovementDto();
			new ModelMapper().map(movement, movementRow);
			movementRow.setClassifierDto(new ClassifierDto());
			new ModelMapper().map(movement.getClassifier(), movementRow.getClassifierDto());
			recebimentos.add(movementRow); 
		});
		return recebimentos;
	}
	
	public List<MovementDto> getAllPaymentMovements(){
		List<MovementDto> pagamentos = new ArrayList<MovementDto>(); 
		List<Movement> movementsPagamentos = this.movementRepository.findByType(TransitionType.CREDIT); 
		movementsPagamentos.forEach(pagamento->{
			MovementDto pagamentoRow = new MovementDto();
			new ModelMapper().map(pagamento, pagamentoRow);
			pagamentoRow.setClassifierDto(new ClassifierDto());
			new ModelMapper().map(pagamento.getClassifier(), pagamentoRow.getClassifierDto());
			pagamentos.add(pagamentoRow); 
		});
		return pagamentos; 
	}
	
	public List<MovementDto> getAllPaymentMovements(MovementDto filter){
		List<MovementDto> pagamentos = new ArrayList<MovementDto>(); 
		List<Movement> movementsPagamentos = this.movementRepository.findAll(this.defineFilters(filter.getType(), filter.getCodePayments(), filter.getBeginDatePayments(), filter.getEndDatePayments()));
		movementsPagamentos.forEach(pagamento->{
			MovementDto pagamentoRow = new MovementDto();
			new ModelMapper().map(pagamento, pagamentoRow);
			pagamentoRow.setClassifierDto(new ClassifierDto());
			new ModelMapper().map(pagamento.getClassifier(), pagamentoRow.getClassifierDto());
			pagamentos.add(pagamentoRow); 
		});
		return pagamentos; 
	}
	
	
	public List<MovementDto> getAllTransferenceMovements(){
		List<MovementDto> transferencias = new ArrayList<MovementDto>(); 
		List<Movement> movementsTransferencias = this.movementRepository.findByType(TransitionType.TRANSFER); 
		movementsTransferencias.forEach(transferencia->{
			MovementDto transferenciaRow = new MovementDto();
			new ModelMapper().map(transferencia, transferenciaRow);
			transferenciaRow.setClassifierDto(new ClassifierDto());
			new ModelMapper().map(transferencia.getClassifier(), transferenciaRow.getClassifierDto());
			transferencias.add(transferenciaRow); 
		});
		return transferencias;
	}
	
	public List<MovementDto> getAllTransferenceMovements(MovementDto filter){
		List<MovementDto> transferencias = new ArrayList<MovementDto>(); 
		List<Movement> movementsTransferencias = this.movementRepository.findAll(defineFilters(filter.getType(), filter.getCodeTransferences(), filter.getBeginDateTransferences(), filter.getEndDateTransferences())); 
		movementsTransferencias.forEach(transferencia->{
			MovementDto transferenciaRow = new MovementDto();
			new ModelMapper().map(transferencia, transferenciaRow);
			transferenciaRow.setClassifierDto(new ClassifierDto());
			new ModelMapper().map(transferencia.getClassifier(), transferenciaRow.getClassifierDto());
			transferencias.add(transferenciaRow); 
		});
		return transferencias;
	}
	
	private Specification<Movement> defineFilters(TransitionType type, String numDoc, Date beginDate, Date endDate){
		Specification<Movement> specification = IMovementRepository.hasType(type);
		if(numDoc != null && !numDoc.isEmpty()) 
			specification = specification.and(IMovementRepository.hasCode(numDoc)); 
		if(beginDate != null && endDate != null) 
			specification = specification.and(IMovementRepository.hasDateBetween(beginDate, endDate));
		return specification; 
	}
	
	private List<MovementDto> readExcelAndMapToDto(InputStream inputStream, MovementDto model) {
		ExcelFile excelFile = new ExcelFile(); 

		List<MovementDto> movementDtos = new ArrayList<MovementDto>();  
		 
			
		excelFile.setCurrentSheet(model.getMonth());
		excelFile.loadFrom(inputStream);
		List<ArrayList<Object>> data = excelFile.getData(); 
		//List<MovementDto> movementDtos = new ArrayList<MovementDto>(); 
		int beginRow = 8;
		for(int i = 0; i < data.size(); i++) {
			if(i > beginRow) {
				if(data.get(i).get(0) == null) 
					break;
				try {
					MovementDto movementDto = new MovementDto();
					movementDto.setCurrency(model.getCurrency());
					movementDto.setMonth(model.getMonth());
					movementDto.setNumMov(new BigDecimal(data.get(i).get(0) + "").toBigInteger() + "");
					movementDto.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(data.get(i).get(1) + ""));
					movementDto.setRecipient(data.get(i).get(2) + "");
					movementDto.setDescription(data.get(i).get(3) + "");
					movementDto.setNumDoc(new BigDecimal(data.get(i).get(4) + "").toBigInteger() + "");
					movementDto.setStatus(Status.ATIVO);
					
					String []aux = (data.get(i).get(5) + "").split("-");
					ClassifierDto classifierDto = new ClassifierDto(); 
					classifierDto.setCode(aux[0].trim());
					classifierDto.setName(aux[1].trim());
					classifierDto.setDescription(data.get(i).get(5) + "");
					classifierDto.setStatus(Status.ATIVO);
					movementDto.setClassifierDto(classifierDto);
					
					Object valorDebito = data.get(i).get(6);
					Object valorCredito = data.get(i).get(7);
					
					if(valorDebito != null) {
						movementDto.setType(TransitionType.DEBIT);
						movementDto.setValor(new BigDecimal(valorDebito + "") );
					}
					if(valorCredito != null) {
						movementDto.setType(TransitionType.CREDIT);
						movementDto.setValor(new BigDecimal(valorCredito + "") );
					}
					movementDto.setBalance(new BigDecimal(data.get(i).get(8) + ""));
					
					CompanyDto companyDto = new CompanyDto();
					companyDto.setId(model.getCompanyId());
					movementDto.setCompanyDto(companyDto);
                    
                    movementDtos.add(movementDto);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        
		}
        return movementDtos;
    }
	
	@Transactional
    public void populateMovementsFromExcelToDb(InputStream inputStream, MovementDto model) {
        List<MovementDto> movementDtos = readExcelAndMapToDto(inputStream, model);
        for (MovementDto movementDto : movementDtos) {
            Optional<Classifier> optional = this.classifierRepository.findByCode(movementDto.getClassifierDto().getCode());
            Classifier classifier = null;
            if (optional.isPresent()) {
                classifier = optional.get();
            } else {
                classifier = new Classifier();
                new ModelMapper().map(movementDto.getClassifierDto(), classifier);
                classifier = this.classifierRepository.save(classifier);
            }
            Optional<Company> optionalCompany = companyRepository.findById(model.getCompanyId()); 
            Company company = null; 
            if(optionalCompany.isPresent()) 
            	company = optionalCompany.get(); 
            Movement movement = new Movement();
            new ModelMapper().map(movementDto, movement);
            movement.setMonth(Month.getMonthByValue(movementDto.getMonth()));
            movement.setClassifier(classifier);
            movement.setCompany(company);
            movement = this.movementRepository.save(movement);
        }
    }
    
    public void loadCurrenciesFromDomain(MovementDto movementDto) {
    	this.domainRepository.findByDominioAndEstado(Currency.class.getSimpleName().toUpperCase(), Status.ATIVO).forEach(domain->{
    		DomainDto domainDto = new DomainDto();
    		new ModelMapper().map(domain, domainDto); 
    		movementDto.getCurrencies().add(domainDto); 
    	});
    	movementDto.getCurrencies().sort(Comparator.comparing(DomainDto::getId));
    }
    
    public void loadMonthsFromDomain(MovementDto movementDto) {
    	this.domainRepository.findByDominioAndEstado(Month.class.getSimpleName().toUpperCase(), Status.ATIVO).forEach(domain->{
    		DomainDto domainDto = new DomainDto();
    		new ModelMapper().map(domain, domainDto); 
    		movementDto.getMonths().add(domainDto); 
    	});
    	movementDto.getMonths().sort(Comparator.comparing(DomainDto::getId));
    }
    
    public void loadCompanies(MovementDto movementDto) {
    	Map<Long, String> map = this.companyRepository.findAll().stream().collect(Collectors.toMap(Company::getId, Company::getName));
    	if(map != null && !map.isEmpty())
    		movementDto.getCompanies().putAll(map);
    }
}
