package com.kusion.vote.ext.wx.models;

import com.kusion.vote.common.models.AbstractModel;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ReedMi on 2016/9/27.
 */
@Entity
@Table(name = "wx_menu_btn")
public class MenuBtn extends AbstractModel {

    private String type;

    private String name;

    private String key;

    @Column(columnDefinition = "text")
    private String url;

    private String mediaId;

    @ManyToOne
    private MenuBtn parent;

    @OneToMany(mappedBy = "parent")
    private List<MenuBtn> subButtons;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public MenuBtn getParent() {
        return parent;
    }

    public void setParent(MenuBtn parent) {
        this.parent = parent;
    }

    public List<MenuBtn> getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(List<MenuBtn> subButtons) {
        this.subButtons = subButtons;
    }
}
