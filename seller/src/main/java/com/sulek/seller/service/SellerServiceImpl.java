package com.sulek.seller.service;



import com.sulek.seller.dto.RegistrationRequest;
import com.sulek.seller.entity.Seller;
import com.sulek.seller.exception.SellerNotFoundException;
import com.sulek.seller.exception.DataSaveException;
import com.sulek.seller.repository.SellerRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("SellerServiceImpl")
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final static Logger log = LogManager.getLogger(SellerServiceImpl.class);

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean register(RegistrationRequest registrationRequest) {
        try {
            Seller seller = Seller.builder()
                    .email(registrationRequest.getEmail())
                    .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                    .businessName(registrationRequest.getBusinessName())
                    .emailActivision(false)
                    .businessAddress(registrationRequest.getAddress())
                    .build();
            seller.setStatus(true);
            seller.setCreatedAt(new Date());
            sellerRepository.save(seller);
            return Boolean.TRUE;
        }catch (DataIntegrityViolationException ex){
            throw new DataSaveException(ex.getMessage());
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Seller findByUserName(String businessName) {
        Seller seller = sellerRepository.findByBusinessName(businessName);
        if(seller == null){
            throw new SellerNotFoundException("Seller not found");
        }
        return seller;
    }
}
