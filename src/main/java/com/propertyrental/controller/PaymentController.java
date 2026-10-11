package com.propertyrental.controller;

import com.propertyrental.model.PaymentRecord;
import com.propertyrental.repository.PaymentRecordRepository;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {
    private final PaymentRecordRepository payments;
    public PaymentController(PaymentRecordRepository payments){this.payments=payments;}
    @PostMapping("/demo") public PaymentRecord demo(@RequestBody Map<String,String> body){
        // Demo-only record. This does not process a real payment or store card details.
        PaymentRecord p=new PaymentRecord();
        p.setPropertyId(Long.valueOf(body.get("propertyId")));
        p.setPayerName(body.getOrDefault("payerName","Demo Tenant"));
        p.setAmount(new BigDecimal(body.getOrDefault("amount","0")));
        p.setStatus("DEMO_RECORDED");
        return payments.save(p);
    }
}
