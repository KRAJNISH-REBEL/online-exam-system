package com.example.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, Integer> otpStorage = new HashMap<>();

    public void generateAndSendOtp(String email) {

        int otp = 100000 + new Random().nextInt(900000);

        otpStorage.put(email, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Online Exam System OTP");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);

        System.out.println("OTP Sent: " + otp);
    }

    public boolean verifyOtp(String email, int otp) {

        Integer storedOtp = otpStorage.get(email);

        return storedOtp != null && storedOtp == otp;
    }
}