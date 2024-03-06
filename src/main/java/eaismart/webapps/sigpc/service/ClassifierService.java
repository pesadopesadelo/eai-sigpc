package eaismart.webapps.sigpc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eaismart.webapps.sigpc.dto.ClassifierDto; 
import eaismart.webapps.sigpc.entity.Classifier;
import eaismart.webapps.sigpc.repository.IClassifierRepository;


/**
 * @author Amandio Santos
 * Feb 23, 2024
 */

@Service("sigpcClassifierService")
public class ClassifierService { 
	
	@Autowired
	private IClassifierRepository classifierRepository; 
	
	public ClassifierDto create(ClassifierDto classifierDto) {
		Classifier classifier = new Classifier(); 
		new ModelMapper().map(classifierDto, classifier);
		classifier = classifierRepository.save(classifier); 
		new ModelMapper().map(classifier, classifierDto);
		return classifierDto; 
	}
	
	public ClassifierDto update(ClassifierDto classifierDto) {
		return create(classifierDto);
	}
	
	public ClassifierDto get(Long id) {
		Optional<Classifier> opt = classifierRepository.findById(id); 
		Classifier classifier = opt.isPresent() ? opt.get() : null; 
		ClassifierDto classifierDto = new ClassifierDto(); 
		new ModelMapper().map(classifier, classifierDto); 
		return classifierDto;
	}
	
	public List<ClassifierDto> getAll() { 
		List<Classifier> classifiers = classifierRepository.findAll(); 
		List<ClassifierDto> classifierDtos = new ArrayList<ClassifierDto>(); 
		classifiers.forEach(obj->{
			ClassifierDto classifierDto = new ClassifierDto(); 
			new ModelMapper().map(obj, classifierDto); 
			classifierDtos.add(classifierDto);
		});
		return classifierDtos; 
	}
	
	public void delete(Long id) {
		classifierRepository.deleteById(id);
	}
}
