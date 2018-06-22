package br.com.socialbase.ml.digester.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "engine4_users")
public class User {

    @Id
    private Integer userId;

    @Column(name="displayname")
    private String name;
    private String email;
    private boolean enabled;

    @Column(name="creation_date", nullable = true)
    private Date creationDate;

    @Column(name="last_activity_date", nullable = true)
    private Date lastActivityDate;

    @Column(name="lastlogin_date", nullable = true)
    private Date lastLoginDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private File avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="engine4_group_membership", joinColumns=
            {@JoinColumn(name="user_id")}, inverseJoinColumns=
            {@JoinColumn(name="resource_id")})
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="engine4_user_membership", joinColumns=
            {@JoinColumn(name="user_id")}, inverseJoinColumns=
            {@JoinColumn(name="resource_id")})
    private Set<User> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<View> views;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Access> accesses;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
