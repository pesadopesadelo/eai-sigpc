package eaismart.webapps.sigpc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eaismart.webapps.sigpc.dto.CompanyDto;
import eaismart.webapps.sigpc.entity.Company;
import eaismart.webapps.sigpc.repository.ICompanyRepository;


/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Service("sigpcCompanyService")
public class CompanyService { 
	
	@Autowired
	private ICompanyRepository companyRepository; 
	
	public CompanyDto create(CompanyDto companyDto) {
		Company company = new Company(); 
		new ModelMapper().map(companyDto, company);
		company = companyRepository.save(company); 
		new ModelMapper().map(company, companyDto);
		return companyDto; 
	}
	
	public CompanyDto update(CompanyDto companyDto) {
		return create(companyDto);
	}
	
	public CompanyDto get(Long id) {
		Optional<Company> opt = companyRepository.findById(id); 
		Company company = opt.isPresent() ? opt.get() : null; 
		CompanyDto companyDto = new CompanyDto(); 
		new ModelMapper().map(company, companyDto); 
		return companyDto;
	}
	
	public List<CompanyDto> getAll() { 
		List<Company> companies = companyRepository.findAll(); 
		List<CompanyDto> companyDtos = new ArrayList<CompanyDto>(); 
		companies.forEach(obj->{
			CompanyDto companyDto = new CompanyDto(); 
			new ModelMapper().map(obj, companyDto); 
			companyDtos.add(companyDto);
		});
		return companyDtos; 
	}
	
	public void delete(Long id) {
		companyRepository.deleteById(id);
	}
}
