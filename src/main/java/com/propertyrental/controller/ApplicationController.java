package com.propertyrental.controller;

import com.propertyrental.model.RentalApplication;
import com.propertyrental.repository.RentalApplicationRepository;
import com.propertyrental.repository.PropertyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final RentalApplicationRepository applications;
    private final PropertyRepository properties;
    private final AuthController auth;
    public ApplicationController(RentalApplicationRepository a, PropertyRepository p,AuthController auth){applications=a;properties=p;this.auth=auth;}
    @GetMapping public List<RentalApplication> all(@RequestHeader("Authorization") String token){auth.requireRole(token,"landlord","admin");return applications.findAll();}
    @PostMapping public RentalApplication apply(@RequestHeader("Authorization") String token,@Valid @RequestBody RentalApplication app){
        auth.requireRole(token,"tenant");
        if(!properties.existsById(app.getPropertyId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Property not found");
        app.setId(null); app.setStatus("PENDING"); return applications.save(app);
    }
    @PatchMapping("/{id}/status") public RentalApplication status(@RequestHeader("Authorization") String token,@PathVariable Long id,@RequestParam String status){
        auth.requireRole(token,"landlord","admin");
        if(!Set.of("PENDING","CHECKING","APPROVED","REJECTED","CANCELLED").contains(status.toUpperCase()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsupported status");
        RentalApplication a=applications.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Application not found"));
        a.setStatus(status.toUpperCase()); return applications.save(a);
    }
}

