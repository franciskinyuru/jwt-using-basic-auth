package dev.sdp.jwt.demo.controller;

import dev.sdp.jwt.demo.model.RestResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> home(Principal principal){
        return ResponseEntity.ok(new RestResponse( "Hello my name is "+principal.getName()));
    }
}
