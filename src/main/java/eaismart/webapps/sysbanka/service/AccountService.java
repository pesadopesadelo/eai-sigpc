package eaismart.webapps.sysbanka.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eaismart.webapps.sysbanka.dto.AccountDto;
import eaismart.webapps.sysbanka.entity.Account;
import eaismart.webapps.sysbanka.repository.IAccountRepository;

/**
 * @author Iekiny Marcel
 * Feb 16, 2021
 */
@Service
public class AccountService {
	
	@Autowired
	private IAccountRepository accountRepository; 
	
	public AccountDto create(AccountDto accountDto) {
		Account account = new Account(); 
		new ModelMapper().map(accountDto, account);
		account = accountRepository.save(account); 
		new ModelMapper().map(account, accountDto);
		return accountDto; 
	}
	
	public void populate(List<AccountDto> accountDtos) {
		for(AccountDto accountDto : accountDtos) {
			accountDto = this.create(accountDto); 
			// ... 
		}
	}
	
}
