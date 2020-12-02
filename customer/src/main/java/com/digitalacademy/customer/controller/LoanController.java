package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.api.LoanApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {
    private LoanApi loanApi;

    @Autowired
    public LoanController(LoanApi loanApi){
        this.loanApi = loanApi;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanInfo(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(loanApi.getLoanInfo(id));
    }

}
