package com.example.exam.controller;

import com.example.exam.service.OtpService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
public String sendOtp(@RequestParam("username") String email,
                      HttpSession session) {

    otpService.generateAndSendOtp(email);

    session.setAttribute("email", email);

    return "verify-otp";
}
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam int otp,
                            HttpSession session) {

        String email = (String) session.getAttribute("email");

        boolean valid = otpService.verifyOtp(email, otp);

        if(valid) {
            return "redirect:/student/dashboard";
        }

        return "verify-otp";
    }
}