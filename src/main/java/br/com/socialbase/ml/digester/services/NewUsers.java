package br.com.socialbase.ml.digester.services;

import br.com.socialbase.ml.digester.model.User;
import br.com.socialbase.ml.digester.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NewUsers {

    @Autowired
    UserRepository userRepository;

    public List<User> getUser(){

        List<User> newUsers = getNewUsers( new DateTime(new Date()).minusDays(30).toDate());

        int countZeroGroups = 0;

        for(User user: newUsers) {

            if(user.getGroups().size()==0) {

                System.out.println("New user "+user.getName()+" no groups");
                System.out.println(user.getLastActivityDate());
                countZeroGroups++;
            }else {

                System.out.println("New user "+user.getName()+" with "+user.getGroups().size()+" groups");

            }
        }

        System.out.println("Total zero groups = "+countZeroGroups+" out of "+newUsers.size());

        return newUsers;
    }

    private List<User> getNewUsers(Date lastNewUsers){
        return userRepository.findByEnabledAndCreationDateGreaterThanEqual(true, lastNewUsers);
    }

}
