package com.propertyrental.controller;

import com.propertyrental.repository.PropertyRepository;
import com.propertyrental.repository.RentalApplicationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {
    private final PropertyRepository properties;
    private final RentalApplicationRepository applications;
    public DashboardController(PropertyRepository p,RentalApplicationRepository a){properties=p;applications=a;}
    @GetMapping("/stats") public Map<String,Long> stats(){
        return Map.of("totalProperties",properties.count(),"availableProperties",properties.countByAvailableTrue(),
          "totalApplications",applications.count(),"pendingApplications",applications.countByStatus("PENDING"));
    }
}
