package it.pleaseopen.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class Demo {

    @GetMapping("/")
    public ResponseEntity<?> ping(){
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }
}
