package com.sulek.seller.controller;



import com.sulek.seller.dto.LoginRequest;
import com.sulek.seller.dto.RegistrationRequest;
import com.sulek.seller.dto.TokenResponse;
import com.sulek.seller.entity.Seller;
import com.sulek.seller.security.JwtTokenUtil;
import com.sulek.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/account")
@RestController
@CrossOrigin
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SellerService sellerService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) throws AuthenticationException {
        Seller seller = sellerService.findByUserName(request.getBusinessName());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(seller.getBusinessName(), request.getPassword()));
        final String token = jwtTokenUtil.generateToken(seller);
        return ResponseEntity.ok(new TokenResponse(seller.getBusinessName(), token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Boolean> register(@RequestBody RegistrationRequest registrationRequest) throws AuthenticationException {
        Boolean response = sellerService.register(registrationRequest);
        return ResponseEntity.ok(response);
    }
}
