package br.com.socialbase.ml.digester.services.graph;

import br.com.socialbase.ml.digester.model.User;

public class Neo4J
{
    private static Neo4J ourInstance = new Neo4J();
    public static Neo4J getInstance() {
        return ourInstance;
    }

    private Neo4J(){

    }

    public void insertUser(User user){

    }

    public void insertConnection(User user1, User user2){

    }
}
