package com.sulek.seller.service;

import com.sulek.seller.dto.RegistrationRequest;
import com.sulek.seller.entity.Seller;
import org.springframework.stereotype.Service;


@Service
public interface SellerService {

    Boolean register(RegistrationRequest registrationRequest);

    Seller findByUserName(String userName);


}
