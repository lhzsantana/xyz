package br.com.socialbase.ml.digester.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@IdClass(br.com.socialbase.ml.digester.model.View.class)
@Entity
@Table(name = "engine4_activity_views")
public class View implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "action_id", nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    @Column(name = "view_date")
    private Date viewedAt;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Date viewedAt) {
        this.viewedAt = viewedAt;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (post != null ? !post.equals(view.post) : view.post != null) return false;
        return user != null ? user.equals(view.user) : view.user == null;
    }

    @Override
    public int hashCode() {
        int result = post != null ? post.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

}
