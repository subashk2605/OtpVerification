package com.stg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stg.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    // Custom method to find an OTP by user ID
    Otp findByUserId(String userId);
    
     void deleteByUserId(String userId);
}
