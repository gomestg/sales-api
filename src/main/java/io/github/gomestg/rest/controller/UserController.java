package io.github.gomestg.rest.controller;

import io.github.gomestg.domain.entity.Users;
import io.github.gomestg.exception.InvalidPasswordException;
import io.github.gomestg.rest.dto.CredentialsDTO;
import io.github.gomestg.rest.dto.TokenDTO;
import io.github.gomestg.security.jwt.JwtService;
import io.github.gomestg.service.implement.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api("API Users")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a new User")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User saved successfully"),
            @ApiResponse(code = 400, message = "Validation Error ")
    })
    public Users save(@RequestBody @Valid Users user) {
        String passwordCrypto = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordCrypto);
        return userService.save(user);
    }

    @PostMapping("/auth")
    @ApiOperation("Authentication API")
    public TokenDTO authentic(@RequestBody CredentialsDTO credentials) {
        try {
            Users user = Users.builder()
                    .login(credentials.getLogin())
                    .password(credentials.getPassword())
                    .build();
            UserDetails userDetails = userService.authentic(user);
            String token = jwtService.generateToken(user);
            return new TokenDTO(user.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
