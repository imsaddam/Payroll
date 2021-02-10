package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Employee;
import com.example.model.SocialContribution;

@Repository
public interface SocialContributionRepository extends MongoRepository<SocialContribution, String> {

}
