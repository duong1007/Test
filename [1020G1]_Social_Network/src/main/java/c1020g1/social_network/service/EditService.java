package c1020g1.social_network.service;


import c1020g1.social_network.model.User;

import org.springframework.stereotype.Service;


@Service
public interface EditService {
    User getUserInfoById(int id);
    void save(User user);


}
