package br.com.socialbase.ml.digester.services.engagement;

import br.com.socialbase.ml.digester.model.User;
import br.com.socialbase.ml.digester.repository.PostRepository;
import br.com.socialbase.ml.digester.repository.UserRepository;
import br.com.socialbase.ml.digester.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EngagementService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    PostRepository postRepository;

    public Long getTotalViews(User user){

        Date lastActivityDate = userRepository.findAccesses(user).get(0);

        return viewRepository.countViewByUserAndViewedAtGreaterThanEqual(user, lastActivityDate);
    }
}