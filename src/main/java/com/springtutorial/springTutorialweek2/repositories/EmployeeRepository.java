package com.springtutorial.springTutorialweek2.repositories;

import com.springtutorial.springTutorialweek2.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{

}
