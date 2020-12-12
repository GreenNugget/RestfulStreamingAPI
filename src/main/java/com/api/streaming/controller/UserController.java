package com.api.streaming.controller;

import javax.validation.Valid;

import com.api.streaming.model.User;
import com.api.streaming.model.dto.TokenDto;
import com.api.streaming.model.request.LoginUserRequest;
import com.api.streaming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginUserRequest request) {
        return ResponseEntity.ok().body(userService.loadUser(request));
    }

    /*Ejemplos de seguridad por roles*/

    //Cualquiera puede hacer uso del endpoint
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    //Solo USER puede usarlo
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roleUser")
    public ResponseEntity<String> userExample(){
        return ResponseEntity.ok().body("Only Users can see this");
    }

    //Solo ADMIN puede usarlo
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roleAdmin")
    public ResponseEntity<String> adminExample(){
        return ResponseEntity.ok().body("Only Admins can see this");
    }

    
    
}