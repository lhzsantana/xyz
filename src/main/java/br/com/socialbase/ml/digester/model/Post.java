package br.com.socialbase.ml.digester.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "engine4_activity_actions")
public class Post {

    @Id
    @Column(name = "action_id")
    private Integer post_id;

    @Column(name = "date")
    private Date createdAt;

    @Column(name = "body")
    private String body;

    @Column(name = "object_type")
    private String objectType;

    @Column(name = "object_id")
    private Integer objectId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = true)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="activity_attachments", joinColumns=
            {@JoinColumn(name="action_id")}, inverseJoinColumns=
            {@JoinColumn(name="file_id")})
    private Set<File> attachments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Set<View> views;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private Set<Like> likes;

    public Post() {
    }

    public Integer getPostId() {
        return post_id;
    }

    public void setPostId(Integer post_id) {
        this.post_id = post_id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return post_id != null ? post_id.equals(post.post_id) : post.post_id == null;
    }

    @Override
    public int hashCode() {
        return post_id != null ? post_id.hashCode() : 0;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<File> attachments) {
        this.attachments = attachments;
    }
}
