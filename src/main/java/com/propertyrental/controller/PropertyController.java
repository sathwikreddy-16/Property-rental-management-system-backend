package com.propertyrental.controller;

import com.propertyrental.model.Property;
import com.propertyrental.repository.PropertyRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "${FRONTEND_URL:http://localhost:5173}")
public class PropertyController {
    private final PropertyRepository repository;
    public PropertyController(PropertyRepository repository){this.repository=repository;}

    @GetMapping public List<Property> all(@RequestParam(required=false) String search,
      @RequestParam(required=false) String city, @RequestParam(required=false) String type,
      @RequestParam(required=false) BigDecimal maxRent) {
        return repository.search(blankToNull(search), blankToNull(city), blankToNull(type), maxRent);
    }
    @GetMapping("/{id}") public Property one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Property not found"));
    }
    @PostMapping public Property create(@Valid @RequestBody Property property){ property.setId(null); return repository.save(property); }
    @PutMapping("/{id}") public Property update(@PathVariable Long id,@Valid @RequestBody Property input){
        Property p=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Property not found"));
        p.setTitle(input.getTitle()); p.setLocation(input.getLocation()); p.setCity(input.getCity());
        p.setPropertyType(input.getPropertyType()); p.setMonthlyRent(input.getMonthlyRent());
        p.setBedrooms(input.getBedrooms()); p.setBathrooms(input.getBathrooms()); p.setDescription(input.getDescription());
        p.setImageUrl(input.getImageUrl()); p.setAvailable(input.isAvailable()); p.setOwnerName(input.getOwnerName());
        return repository.save(p);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){
        if(!repository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Property not found");
        repository.deleteById(id);
    }
    private String blankToNull(String s){return s==null||s.isBlank()?null:s.trim();}
}
