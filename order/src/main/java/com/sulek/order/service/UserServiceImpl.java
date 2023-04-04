package com.sulek.order.service;



import com.sulek.order.dto.RegistrationRequest;
import com.sulek.order.exception.CustomerNotFoundException;
import com.sulek.order.exception.DuplicateKeyValueException;
import com.sulek.order.model.User;
import com.sulek.order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Boolean register(RegistrationRequest registrationRequest) {
        try {
            User user = User.builder()
                    .email(registrationRequest.getEmail())
                    .nameSurname(registrationRequest.getNameSurname())
                    .password(bCryptPasswordEncoder.encode(registrationRequest.getPassword()))
                    .username(registrationRequest.getUsername())
                    .build();
            user.setStatus(true);
            user.setCreatedAt(new Date());
            userRepository.save(user);
            return Boolean.TRUE;
        }catch (DataIntegrityViolationException ex){
            throw new DuplicateKeyValueException(ex.getMessage());
        }
        catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public User findByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw new CustomerNotFoundException("User not found");
        }
        return user;
    }
}
