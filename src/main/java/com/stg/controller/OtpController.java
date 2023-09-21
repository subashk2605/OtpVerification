package com.stg.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	        return otpService.generateOtp(otpLength,userId);
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
	
}
