package com.example.exam.controller;

import com.example.exam.service.OtpService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginSuccessController {

    @Autowired
    private OtpService otpService;

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication,
                               HttpSession session) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority()
                                .equals("ROLE_ADMIN"));

        // =========================
        // ADMIN LOGIN
        // =========================

        if (isAdmin) {

            return "redirect:/admin/dashboard";
        }

        // =========================
        // STUDENT LOGIN + OTP
        // =========================

        String email = authentication.getName();

        otpService.generateAndSendOtp(email);

        session.setAttribute("email", email);

        return "verify-otp";
    }
}