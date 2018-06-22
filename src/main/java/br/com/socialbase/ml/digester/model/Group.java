package br.com.socialbase.ml.digester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "engine4_group_groups")
public class Group implements Serializable {

    @Id
    @Column(name="group_id")
    private Integer groupId;

    @Column(name="type")
    private String type;

    @Column(name="title")
    private String title;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
