package eaismart.webapps.sysbanka.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eaismart.webapps.sysbanka.dto.DomainDto;
import eaismart.webapps.sysbanka.entity.Domain;
import eaismart.webapps.sysbanka.repository.IDomainRepository;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Service(value = "sysbankaDomainService")
public class DomainService { 
	
	@Autowired
	private IDomainRepository domainRepository; 
	
	public DomainDto create(DomainDto domainDto) {
		Domain domain = new Domain(); 
		new ModelMapper().map(domainDto, domain);
		domain = domainRepository.save(domain); 
		new ModelMapper().map(domain, domainDto);
		return domainDto; 
	}
	
	public DomainDto update(DomainDto domainDto) {
		return create(domainDto);
	}
	
	public DomainDto get(Long id) {
		Optional<Domain> opt = domainRepository.findById(id); 
		Domain domain = opt.isPresent() ? opt.get() : null; 
		DomainDto domainDto = new DomainDto(); 
		new ModelMapper().map(domain, domainDto); 
		return domainDto;
	}
	
	public List<DomainDto> getAll() { 
		List<Domain> domains = domainRepository.findAll(); 
		List<DomainDto> domainDtos = new ArrayList<DomainDto>(); 
		domains.forEach(obj->{
			DomainDto domainDto = new DomainDto(); 
			new ModelMapper().map(obj, domainDto); 
			domainDtos.add(domainDto);
		});
		return domainDtos; 
	}
	
	public void delete(Long id) {
		domainRepository.deleteById(id);
	}
}
