package com.sulek.order.service;




import com.sulek.order.dto.RegistrationRequest;
import com.sulek.order.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Boolean register(RegistrationRequest registrationRequest);

    User findByUserName(String userName);


}
