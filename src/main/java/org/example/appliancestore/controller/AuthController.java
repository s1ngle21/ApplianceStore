package org.example.appliancestore.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.AuthRequest;
import org.example.appliancestore.dto.RegistrationClient;
import org.example.appliancestore.dto.TokenResponse;
import org.example.appliancestore.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/login")
    public String login(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authService.isAuthenticated(authentication)) {
            redirectAttributes.addFlashAttribute("message", "You are already logged in! \nPlease logout first");
            return "redirect:/home";
        }
        AuthRequest authRequest = new AuthRequest();
        model.addAttribute("authRequest", authRequest);
        return "auth/login-form";
    }


    @Loggable
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("authRequest") AuthRequest registrationRequest, BindingResult bindingResult,
                        HttpServletResponse response, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authService.isAuthenticated(authentication)) {
            redirectAttributes.addFlashAttribute("message", "You are already logged in! \nPlease logout first");
            return "redirect:/home";
        }
        if (bindingResult.hasErrors()) {
            return "auth/login-form";
        }
        TokenResponse tokenResponse = authService.login(registrationRequest);
        Cookie cookie = new Cookie("authToken", tokenResponse.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
        return "redirect:/home";
    }


    @GetMapping("/register")
    public String register(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authService.isAuthenticated(authentication)) {
            redirectAttributes.addFlashAttribute("message", "You are already logged in!");
            return "redirect:/home";
        }
        RegistrationClient registrationClient = new RegistrationClient();
        model.addAttribute("registrationRequest", registrationClient);
        return "auth/register-form";
    }


    @Loggable
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationRequest") RegistrationClient registrationClient, BindingResult result,
                           Authentication authentication, Model model) {
        if (authService.isAuthenticated(authentication)) {
            return "redirect:/home";
        }
        if (result.hasErrors()) {
            model.addAttribute("registrationRequest", registrationClient);
            return "auth/register-form";
        }
        authService.register(registrationClient);
        return "redirect:/login";
    }




}
