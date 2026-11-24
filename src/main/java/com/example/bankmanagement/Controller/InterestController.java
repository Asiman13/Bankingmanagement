package com.example.bankmanagement.Controller;

import com.example.bankmanagement.Service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interest")
@RequiredArgsConstructor
public class InterestController {
    private final InterestService interestService;
    @PostMapping("/calculate/{id}")
    public ResponseEntity<String > interets(@PathVariable Long id){
        interestService.calculateinterest(id);
        return ResponseEntity.ok("Interest calculated");
    }


}
