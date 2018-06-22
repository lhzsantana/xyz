package br.com.socialbase.ml.digester.services;

import br.com.socialbase.ml.digester.model.User;
import br.com.socialbase.ml.digester.model.graph.UserGraph;
import br.com.socialbase.ml.digester.repository.graph.UserGraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoadToGraph {

    @Autowired
    UserGraphRepository userGraphRepository;

    private UserGraph toUserGraph(User user){

        UserGraph userGraph = new UserGraph();
        userGraph.setUserId(user.getUserId());
        userGraph.setName(user.getName());

        return userGraph;
    }

    public void register(List<User> users){

        Set<UserGraph> userGraphs = new HashSet<>();

        for(User user : users) {
            UserGraph userGraph = toUserGraph(user);

            Set<UserGraph> followers = new HashSet<>();

            for (User follower : user.getFollowers()) {
                followers.add(toUserGraph(follower));
            }

            userGraph.setFollowers(followers);

            userGraphs.add(userGraph);
        }

        userGraphRepository.save(userGraphs);

    }
}
