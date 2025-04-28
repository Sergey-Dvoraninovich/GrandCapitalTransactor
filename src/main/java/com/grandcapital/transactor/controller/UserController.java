package com.grandcapital.transactor.controller;

import com.grandcapital.transactor.controller.model.PhoneRequest;
import com.grandcapital.transactor.controller.model.UpdateEmailRequest;
import com.grandcapital.transactor.controller.model.UpdatePhoneRequest;
import com.grandcapital.transactor.service.IUserService;
import com.grandcapital.transactor.controller.model.EmailRequest;
import com.grandcapital.transactor.service.model.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Validated
public class UserController {

    private IUserService userService;

    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getUsers(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateOfBirth,

            @RequestParam(required = false)
            @Pattern(regexp = "^\\+\\d{12}$", message = "Phone number must start with '+' followed by 12 digits")
            String phone,

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            @Email(message = "Email must be a valid email address")
            String email,

            @RequestParam(defaultValue = "0")
            @Valid
            int page,

            @RequestParam(defaultValue = "10")
            @Valid
            int size,

            Principal principal) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<UserDTO> users = userService.findUsersWithFilters(dateOfBirth, phone, name, email, pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/email")
    public ResponseEntity putUserEmail(@PathVariable Long id, @RequestBody @Valid EmailRequest request, Principal principal) {
        if (!Long.valueOf(principal.getName()).equals(id)) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        userService.putUserEmail(id, request.getEmail());
        return new ResponseEntity<>(CREATED);
    }

    @PatchMapping("/users/{id}/email")
    public ResponseEntity replaceUserEmail(@PathVariable Long id, @RequestBody @Valid UpdateEmailRequest request, Principal principal) {
        if (!Long.valueOf(principal.getName()).equals(id)) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        userService.replaceUserEmail(id, request.getOldEmail(), request.getNewEmail());
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/users/{id}/email")
    public ResponseEntity deleteUserEmail(@PathVariable Long id, @RequestBody @Valid EmailRequest request, Principal principal) {
        if (!Long.valueOf(principal.getName()).equals(id)) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        userService.deleteUserEmail(id, request.getEmail());
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/users/{id}/phone")
    public ResponseEntity putUserPhone(@PathVariable Long id, @RequestBody @Valid PhoneRequest request, Principal principal) {
        if (!Long.valueOf(principal.getName()).equals(id)) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        userService.putUserPhone(id, request.getPhone());
        return new ResponseEntity<>(CREATED);
    }

    @PatchMapping("/users/{id}/phone")
    public ResponseEntity replaceUserPhone(@PathVariable Long id, @RequestBody @Valid UpdatePhoneRequest request, Principal principal) {
        if (!Long.valueOf(principal.getName()).equals(id)) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        userService.replaceUserPhone(id, request.getOldPhone(), request.getNewPhone());
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/users/{id}/phone")
    public ResponseEntity deleteUserPhone(@PathVariable Long id, @RequestBody @Valid PhoneRequest request, Principal principal) {
        if (!Long.valueOf(principal.getName()).equals(id)) {
            return new ResponseEntity<>(FORBIDDEN);
        }

        userService.deleteUserPhone(id, request.getPhone());
        return new ResponseEntity<>(OK);
    }
}

