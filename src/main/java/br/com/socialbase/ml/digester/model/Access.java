package br.com.socialbase.ml.digester.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@IdClass(Access.class)
@Entity
@Table(name = "user_activity")
public class Access implements Serializable {

    @Id
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    private User user;

    @Column(name="date", nullable = true)
    private Date activityDate;

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
