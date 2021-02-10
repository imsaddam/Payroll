package com.example.EmployeeController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.model.SocialContribution;
import com.example.repository.EmployeeRepository;
import com.example.repository.SocialContributionRepository;

@RestController
class SocialContributionController {

  @Autowired
  private final SocialContributionRepository repository;

  SocialContributionController(SocialContributionRepository repository) {
    this.repository = repository;
  }



  @GetMapping("/api/payroll/socialcontribution")
  List<SocialContribution> all() {
	
   return repository.findAll();
  }
  // end::get-aggregate-root[]

  @PostMapping("/api/payroll/SocialContribution")
  SocialContribution newSocialContribution(@RequestBody SocialContribution socialContribution) {
	socialContribution.setId(UUID.randomUUID().toString());
    return repository.save(socialContribution);
  }

  // Single item
  
  @GetMapping("/api/payroll/socialcontribution/{id}")
  SocialContribution one(@PathVariable String id) throws Throwable {
    
    return repository.findById(id)
      .orElseThrow(() -> new Exception());
  }

  @PutMapping("/api/payroll/socialcontribution/{id}")
  SocialContribution replaceSocialContribution(@RequestBody SocialContribution socialContribution, @PathVariable String id) {
    
    return repository.findById(id)
      .map(sct -> {
    	  sct.setPercentage(socialContribution.getPercentage());
    	  sct.setPeriod(socialContribution.getPeriod());
        return repository.save(sct);
      })
      .orElseGet(() -> {
    	  socialContribution.setId(id);
        return repository.save(socialContribution);
      });
  }

  @DeleteMapping("/api/payroll/socialcontribution/{id}")
  void deleteSocialContribution(@PathVariable String id) {
    repository.deleteById(id);
  }
}