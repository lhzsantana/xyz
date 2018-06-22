package br.com.socialbase.ml.digester.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "profile_answer")
public class Answer implements Serializable {

    @Id
    @Column(name="id")
    private Integer answerId;

    @Column(name="value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = true)
    private Profile profile;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
