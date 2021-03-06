package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Employee;
import com.example.model.PayRecords;
import com.example.model.SocialContribution;
import com.example.model.TaxRange;

@Repository
public interface PayRecordsRepository extends MongoRepository<PayRecords, String> {

}
