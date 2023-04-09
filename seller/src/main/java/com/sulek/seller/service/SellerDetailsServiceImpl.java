package com.sulek.seller.service;

import com.sulek.seller.entity.Seller;
import com.sulek.seller.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class SellerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SellerRepository sellerRepository;

    public UserDetails loadUserByUsername(String businessName) throws UsernameNotFoundException {
        Seller user = sellerRepository.findByBusinessName(businessName);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getBusinessName(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")));
    }

}
