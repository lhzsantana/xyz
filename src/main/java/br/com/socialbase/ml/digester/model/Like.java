package br.com.socialbase.ml.digester.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "engine4_activity_likes")
public class Like {

    @Id
    private Integer like_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "resource_id", nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "poster_id", nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    @Column(name="created_at")
    private Date createdAt;

    public Integer getLike_id() {
        return like_id;
    }

    public void setLike_id(Integer like_id) {
        this.like_id = like_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

