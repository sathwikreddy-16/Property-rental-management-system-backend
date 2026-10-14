package com.propertyrental.controller;

import com.propertyrental.repository.PropertyRepository;
import com.propertyrental.repository.RentalApplicationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final PropertyRepository properties;
    private final RentalApplicationRepository applications;
    private final AuthController auth;
    public DashboardController(PropertyRepository p,RentalApplicationRepository a,AuthController auth){properties=p;applications=a;this.auth=auth;}
    @GetMapping("/stats") public Map<String,Long> stats(@RequestHeader("Authorization") String token){
        auth.requireRole(token,"landlord","admin");
        return Map.of("totalProperties",properties.count(),"availableProperties",properties.countByAvailableTrue(),
          "totalApplications",applications.count(),"pendingApplications",applications.countByStatus("PENDING"));
    }
}

