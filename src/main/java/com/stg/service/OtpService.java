package com.stg.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.stg.entity.Otp;
import com.stg.repository.OtpRepository;

@Service
public class OtpService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private OtpRepository otpRepository;

	public String generateOtp(int otpLength, String userId) {
		String otp = generateRandomOtp();
		Otp otpEntity = new Otp();
		otpEntity.setOtpValue(otp);
		otpEntity.setUserId(userId);
		otpEntity.setCreationTime(LocalDateTime.now().toString());
		otpRepository.save(otpEntity);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(userId);
		message.setSubject("Your Otp");
		message.setText("Use the following OTP to Reset your Password. OTP is valid for 2 minutes " + otp);

		javaMailSender.send(message);
		return otp;
	}

	// Verify the OTP
	public boolean verifyOtp(String userId, String otpValue) {

		Otp otpEntity = otpRepository.findByUserId(userId);
		if (otpEntity != null) {
			// Check if the OTP matches and is still valid (e.g., within a certain time
			// limit)
			String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
			LocalDateTime creationTime = null;
			try {
				// Create a DateTimeFormatter using the pattern
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

				// Parse the input string into a LocalDateTime
				creationTime = LocalDateTime.parse(otpEntity.getCreationTime(), formatter);

				// Print the result
				System.out.println("Parsed LocalDateTime: " + creationTime);
			} catch (DateTimeParseException e) {
				// Handle parsing exceptions
				System.err.println("Error parsing the input string: " + e.getMessage());
			}

			if (otpEntity.getOtpValue().equals(otpValue) && isValidOtp(creationTime)) {
				return true;
			}
			if (!isValidOtp(creationTime)) {
				otpRepository.delete(otpEntity);

			}
		}
		return false;
	}

	// Generate a random OTP
	private String generateRandomOtp() {
		int min = 100000; // Minimum 6-digit number
		int max = 999999; // Maximum 6-digit number

		Random random = new Random();
		int randomOtp = random.nextInt((max - min) + 1) + min;

		return String.valueOf(randomOtp);
	}

	// Check if the OTP is still valid (e.g., within 5 minutes)
	private boolean isValidOtp(LocalDateTime creationTime) {
		LocalDateTime now = LocalDateTime.now();
		return now.isBefore(creationTime.plusSeconds(50));
	}

}