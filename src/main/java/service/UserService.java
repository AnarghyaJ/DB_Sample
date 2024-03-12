package service;

import com.example.db_sample.model.User;
import com.example.db_sample.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    @Autowired
    UserService(UserRepository repository){
        this.repository = repository;
    }

    public void saveUser(User user){
        logger.info("Saving user: " + user.getFirstName());
        repository.save(user);
        logger.info("User saved: " + user.getFirstName());
    }

}
