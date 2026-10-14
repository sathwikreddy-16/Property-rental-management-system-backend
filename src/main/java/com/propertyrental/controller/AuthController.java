package com.propertyrental.controller;

import com.propertyrental.model.Account;
import com.propertyrental.repository.AccountRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AccountRepository accounts;
    private final PasswordEncoder passwords;
    private final Map<String, Account> sessions = new ConcurrentHashMap<>();
    @Value("${app.admin-signup-code:}") private String adminSignupCode;
    public AuthController(AccountRepository accounts, PasswordEncoder passwords){this.accounts=accounts;this.passwords=passwords;}

    public record AuthRequest(@NotBlank String name, @Email @NotBlank String email,
                              @NotBlank @Size(min=8, max=100) String password, @NotBlank String role, String adminCode) {}
    public record LoginRequest(@Email @NotBlank String email, @NotBlank String password, @NotBlank String role) {}
    public record UserView(Long id, String name, String email, String role) {}
    public record AuthResponse(String token, UserView user) {}

    @PostMapping("/signup") public AuthResponse signup(@Valid @RequestBody AuthRequest request){
        String role=normalizeRole(request.role());
        if(accounts.existsByEmailIgnoreCase(request.email().trim())) throw new ResponseStatusException(HttpStatus.CONFLICT,"An account with this email already exists");
        if(role.equals("admin") && (adminSignupCode.isBlank() || !adminSignupCode.equals(request.adminCode())))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Admin registration requires a valid administrator signup code");
        Account account=new Account(); account.setName(request.name().trim()); account.setEmail(request.email().trim().toLowerCase());
        account.setPasswordHash(passwords.encode(request.password())); account.setRole(role);
        return issue(accounts.save(account));
    }
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest request){
        String role=normalizeRole(request.role());
        Account account=accounts.findByEmailIgnoreCase(request.email().trim()).orElse(null);
        if(account==null || !account.getRole().equals(role) || !passwords.matches(request.password(),account.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email, password, or account type is incorrect");
        return issue(account);
    }
    @PostMapping("/logout") @ResponseStatus(HttpStatus.NO_CONTENT) public void logout(@RequestHeader(value="Authorization",required=false) String authorization){
        String token=bearer(authorization); if(token!=null) sessions.remove(token);
    }
    public Account authenticated(String authorization){
        String token=bearer(authorization); Account account=token==null?null:sessions.get(token);
        if(account==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in again");
        return account;
    }
    public Account requireRole(String authorization,String... roles){
        Account account=authenticated(authorization);
        if(!java.util.Arrays.asList(roles).contains(account.getRole())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your account type cannot perform this action");
        return account;
    }
    private AuthResponse issue(Account account){String token=UUID.randomUUID()+"."+UUID.randomUUID();sessions.put(token,account);return new AuthResponse(token,view(account));}
    private UserView view(Account a){return new UserView(a.getId(),a.getName(),a.getEmail(),a.getRole());}
    private String normalizeRole(String role){String value=role==null?"":role.trim().toLowerCase();if(!java.util.Set.of("tenant","landlord","admin").contains(value))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsupported account type");return value;}
    private String bearer(String value){return value!=null&&value.startsWith("Bearer ")?value.substring(7):null;}
}

