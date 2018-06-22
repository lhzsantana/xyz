package br.com.socialbase.ml.digester.model.graph;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class UserGraph
{
    @GraphId
    private Long graphId;

    @Index(unique = true, primary = true)
    private Integer userId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Relationship(type="following", direction = Relationship.INCOMING)
    private Set<UserGraph> followers = new HashSet<UserGraph>();

    public Set<UserGraph> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserGraph> followers) {
        this.followers = followers;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }
}
