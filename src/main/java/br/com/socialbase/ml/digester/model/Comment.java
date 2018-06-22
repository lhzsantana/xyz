package br.com.socialbase.ml.digester.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "engine4_activity_comments")
public class Comment {

    @Id
    @Column(name = "comment_id")
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "resource_id", nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private Post post;

    @Column(name = "poster_id")
    private Integer userId;

    @Column(name = "creation_date")
    private Date createdAt;

    @Column(name = "body")
    private String body;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
