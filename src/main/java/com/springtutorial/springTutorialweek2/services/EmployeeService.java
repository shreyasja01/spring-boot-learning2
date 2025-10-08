package com.springtutorial.springTutorialweek2.services;

import com.springtutorial.springTutorialweek2.Exceptions.ResourceNotFoundException;
import com.springtutorial.springTutorialweek2.dto.EmployeeDTO;
import com.springtutorial.springTutorialweek2.entities.EmployeeEntity;
import com.springtutorial.springTutorialweek2.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository  employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> getEmployeeById(Long id){

        Optional<EmployeeEntity> employeeEntity =  employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class)) ;

   }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities =  employeeRepository.findAll();
       return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {

        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);

        EmployeeEntity savedEmployeeEntity =   employeeRepository.save(toSaveEntity);

       return  modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);


    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {

        isExistsByEmployeeId(employeeId);
        EmployeeEntity saveEmployeeEntity;
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        if(employeeRepository.findById(employeeId).isPresent()){
            employeeEntity.setId(employeeId);
        }
        saveEmployeeEntity = employeeRepository.save(employeeEntity);
        return  modelMapper.map(saveEmployeeEntity,EmployeeDTO.class);

    }

    public void isExistsByEmployeeId(Long employeeId){
        boolean exists =  employeeRepository.existsById(employeeId);
        if(!exists)  throw new ResourceNotFoundException("Employee not found with id " + employeeId);

    }
    public boolean deleteEmployeeById(Long employeeId) {

        isExistsByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return  true;
    }

    public EmployeeDTO updatePartialEmployee(Long employeeId, Map<String, Object> updates) {

        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

        return  modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);

    }
}
