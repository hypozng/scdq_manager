package com.scdq.manager.sys.model;

import com.scdq.manager.common.BasicModel;

import java.util.List;

/**
 * Created by Administrator on 2019/12/21.
 */
public class Menu extends BasicModel {

    /** 菜单名 */
    private String name;

    /** 菜单标识 */
    private String identifier;

    /** 菜单地址 */
    private String url;

    /** 父级菜单ID */
    private Long parentId;

    /** 备注 */
    private String remark;

    /** 子菜单 */
    private List<Menu> subMenus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }
}
