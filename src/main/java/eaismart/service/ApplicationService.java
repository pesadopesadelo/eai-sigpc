package eaismart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eaismart.dto.ApplicationDto;
import eaismart.entity.Application;
import eaismart.repository.IApplicationRepository;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Service
public class ApplicationService { 
	
	@Autowired
	private IApplicationRepository applicationRepository; 
	
	public ApplicationDto create(ApplicationDto applicationDto) {
		Application application = new Application(); 
		new ModelMapper().map(applicationDto, application);
		application = applicationRepository.save(application); 
		new ModelMapper().map(application, applicationDto);
		return applicationDto; 
	}
	
	public ApplicationDto update(ApplicationDto applicationDto) {
		return create(applicationDto);
	}
	
	public ApplicationDto get(Long id) {
		Optional<Application> opt = applicationRepository.findById(id); 
		Application application = opt.isPresent() ? opt.get() : null; 
		ApplicationDto applicationDto = new ApplicationDto(); 
		new ModelMapper().map(application, applicationDto); 
		return applicationDto;
	}
	
	public List<ApplicationDto> getAll() { 
		List<Application> applications = applicationRepository.findAll(); 
		List<ApplicationDto> applicationDtos = new ArrayList<ApplicationDto>(); 
		applications.forEach(obj->{
			ApplicationDto applicationDto = new ApplicationDto(); 
			new ModelMapper().map(obj, applicationDto); 
			applicationDtos.add(applicationDto);
		});
		return applicationDtos; 
	}
	
	public void delete(Long id) {
		applicationRepository.deleteById(id);
	}
}
