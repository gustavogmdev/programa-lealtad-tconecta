package bimbo.programalealtadtconecta.controllers;


import bimbo.programalealtadtconecta.dto.AuthRequest;
import bimbo.programalealtadtconecta.dto.AuthResponse;
import bimbo.programalealtadtconecta.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
