package com.springtutorial.springTutorialweek2.controllers;

import com.springtutorial.springTutorialweek2.Exceptions.ResourceNotFoundException;
import com.springtutorial.springTutorialweek2.dto.EmployeeDTO;
import com.springtutorial.springTutorialweek2.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    //private final EmployeeRepository employeeRepository ;
    private final EmployeeService employeeService;
    public EmployeeController( EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long id){
       Optional <EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
       return  employeeDTO
               .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
               .orElseThrow(() -> new ResourceNotFoundException("Employee was not found of Id" + id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees (@RequestParam(required = false,name="inputAge") Integer age,
                                              @RequestParam(required = false) String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){

        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO>  updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,@PathVariable Long employeeId){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) ResponseEntity.ok(true);
        return  ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates
            , @PathVariable Long employeeId ){

        EmployeeDTO employeeDTO =   employeeService.updatePartialEmployee(employeeId,updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();

        return  ResponseEntity.ok(employeeDTO);

    }
}
