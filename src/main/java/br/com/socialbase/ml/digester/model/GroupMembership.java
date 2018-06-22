package br.com.socialbase.ml.digester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "engine4_group_membership")
public class GroupMembership {

    @Id
    @Column(name="resource_id")
    private Integer groupId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="active")
    private Integer active;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
