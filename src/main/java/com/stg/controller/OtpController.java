package com.stg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stg.service.OtpService;

import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@RestController
@RequestMapping("/otp")
public class OtpController {

	  @Autowired
	    private OtpService otpService;

	    // Generate and return an OTP for a specific user
	    @PostMapping("/generate")
	    public String generateOtp(@RequestParam String userId) {
	        int otpLength = 6; // Adjust the OTP length as needed
	        	otpService.generateOtp(otpLength,userId) ;
	        	return "Your OTP is Send to your Registered Mail Sucessfully";
	         

	         
	    }

	    // Verify an OTP for a specific user
	    @PostMapping("/verify")
	    public String verifyOtp(@RequestParam String userId, @RequestParam String otpValue) {
	        if (otpService.verifyOtp(userId, otpValue)) {
	            return "OTP is valid.";
	        } else {
	            return "OTP is invalid or has expired.";
	        }
	    }
	    
	    @DeleteMapping("/delete")
	    public void deleteOtpByUserId() {
	    otpService.deleteOtpByUserId("ksubashchandrabose904@gmail.com");
	    }
	  
	
}
