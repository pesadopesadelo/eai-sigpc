/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.webapps.sigpc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eaismart.webapps.sigpc.entity.Company;
import eaismart.webapps.sigpc.util.constants.Status;
import java.util.List;

/**
 *
 * @author elton
 */
@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long> {

    public List<Company> findByStatus(Status status);
    
}
