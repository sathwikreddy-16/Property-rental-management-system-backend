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
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {
    private final RentalApplicationRepository applications;
    private final PropertyRepository properties;
    public ApplicationController(RentalApplicationRepository a, PropertyRepository p){applications=a;properties=p;}
    @GetMapping public List<RentalApplication> all(){return applications.findAll();}
    @PostMapping public RentalApplication apply(@Valid @RequestBody RentalApplication app){
        if(!properties.existsById(app.getPropertyId())) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Property not found");
        app.setId(null); app.setStatus("PENDING"); return applications.save(app);
    }
    @PatchMapping("/{id}/status") public RentalApplication status(@PathVariable Long id,@RequestParam String status){
        if(!Set.of("PENDING","APPROVED","REJECTED","CANCELLED").contains(status.toUpperCase()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsupported status");
        RentalApplication a=applications.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Application not found"));
        a.setStatus(status.toUpperCase()); return applications.save(a);
    }
}
