package com.infosoul.mserver.common.utils;

import com.google.common.collect.Maps;
import com.infosoul.mserver.common.persistence.Parameter;
import com.infosoul.mserver.common.security.SystemAuthorizingRealm.Principal;
import com.infosoul.mserver.entity.sys.*;
import com.infosoul.mserver.repository.hibernate.sys.*;
import com.infosoul.mserver.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

/**
 * 用户工具类
 * 
 * @author free lance
 * @version 2013-5-29
 */
public class UserUtils extends BaseService {
    
    private static UserDao userDao = ApplicationContextHelper.getBean(UserDao.class);
    private static RoleDao roleDao = ApplicationContextHelper.getBean(RoleDao.class);
    private static MenuDao menuDao = ApplicationContextHelper.getBean(MenuDao.class);
    private static AreaDao areaDao = ApplicationContextHelper.getBean(AreaDao.class);
    private static OfficeDao officeDao = ApplicationContextHelper.getBean(OfficeDao.class);

    public static final String CACHE_USER = "user";//当前登录用户
    public static final String CACHE_VALID_CODE = "code";//验证码
    public static final String CACHE_CART = "CART";//购物车
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_FACILITY_LIST = "facilityList";

    public static User getUser() {
        User user = (User) getCache(CACHE_USER);
        if (user == null) {
            try {
                Subject subject = SecurityUtils.getSubject();
                Principal principal = (Principal) subject.getPrincipal();
                if (principal != null) {
                    user = userDao.get(principal.getId());
                    // Hibernate.initialize(user.getRoleList());
                    putCache(CACHE_USER, user);
                }
            } catch (UnavailableSecurityManagerException e) {
                e.printStackTrace();
            } catch (InvalidSessionException e) {
                e.printStackTrace();
            }
        }
        if (user == null) {
            user = new User();
            try {
                SecurityUtils.getSubject().logout();
            } catch (UnavailableSecurityManagerException e) {
                e.printStackTrace();
            } catch (InvalidSessionException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public static User getUser(boolean isRefresh) {
        if (isRefresh) {
            removeCache(CACHE_USER);
        }
        return getUser();
    }

    /**
     * 返回全部的角色清单
     * @param includeAdmin 是否包含超级管理员角色
     * @return
     */
    public static List<Role> getRoleList(boolean includeAdmin) {
        List<Role> list = (List<Role>) getCache(CACHE_ROLE_LIST + includeAdmin);
        if (list == null) {
//            User user = getUser();
//            DetachedCriteria dc = roleDao.createDetachedCriteria();
//            dc.createAlias("office", "office");
//            dc.createAlias("userList", " ", JoinType.LEFT_OUTER_JOIN);
//            dc.add(dataScopeFilter(user, "office", "userList"));
//            dc.add(Restrictions.eq(Role.FIELD_DEL_FLAG, Role.DEL_FLAG_NORMAL));
//            dc.addOrder(Order.asc("office.code")).addOrder(Order.asc("name"));
//            if (!includeAdmin) {
//                dc.add(Restrictions.ne("id", "admin"));
//            }
//            list = roleDao.find(dc);
//            putCache(CACHE_ROLE_LIST + includeAdmin, list);
            
            // Fixed performance
            StringBuffer sqlPage = new StringBuffer();
            Parameter parameter = new Parameter();
            sqlPage.append("SELECT r.* FROM sys_role AS r WHERE 1=1 ");
            // User currentUser = getUser();
            // String userId = currentUser.getId();
            // 如果不是超级管理员，则不显示超级管理员用户
            if (!includeAdmin) {
                sqlPage.append(" AND r.id != :id ");
                parameter.put("id", "admin");
            }
            list = roleDao.findBySql(sqlPage.toString(), parameter, Role.class);
            putCache(CACHE_ROLE_LIST + includeAdmin, list);
        }
        return list;
    }

    public static List<Menu> getMenuList() {
        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
        if (menuList == null) {
            User user = getUser();
            if (user.isAdmin()) {
                menuList = menuDao.findAllList();
            } else {
                menuList = menuDao.findByUserId(user.getId());
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    public static List<Area> getAreaList() {
        @SuppressWarnings("unchecked")
        List<Area> areaList = (List<Area>) getCache(CACHE_AREA_LIST);
        if (areaList == null) {
            // User user = getUser();
            // if (user.isAdmin()){
            areaList = areaDao.findAllList();
            // }else{
            // areaList = areaDao.findAllChild(user.getArea().getId(), "%,"+user.getArea().getId()+",%");
            // }
            putCache(CACHE_AREA_LIST, areaList);
        }
        return areaList;
    }

    public static List<Office> getOfficeList() {
        @SuppressWarnings("unchecked")
        List<Office> officeList = (List<Office>) getCache(CACHE_OFFICE_LIST);
        if (officeList == null) {
            User user = getUser();
            // if (user.isAdmin()){
            // officeList = officeDao.findAllList();
            // }else{
            // officeList = officeDao.findAllChild(user.getOffice().getId(), "%,"+user.getOffice().getId()+",%");
            // }
            DetachedCriteria dc = officeDao.createDetachedCriteria();
            dc.add(dataScopeFilter(user, dc.getAlias(), ""));
            dc.add(Restrictions.eq(Office.FIELD_DEL_FLAG, Office.DEL_FLAG_NORMAL));
            dc.addOrder(Order.asc("code"));
            officeList = officeDao.find(dc);
            putCache(CACHE_OFFICE_LIST, officeList);
        }
        return officeList;
    }

    /**
     * get User By Id
     * @param id
     * @return
     */
    public static User getUserById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return userDao.get(id);
        } else {
            return null;
        }
    }
    
    /**
     * 根据loginName取User
     * @param loginName
     * @return
     */
    public static User getUserByLoginName(String loginName) {
        if (StringUtils.isNotBlank(loginName)) {
            return userDao.findByLoginName(loginName);
        } else {
            return null;
        }
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getCacheMap().get(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getCacheMap().put(key, value);
    }

    public static void removeCache(String key) {
        getCacheMap().remove(key);
    }

    public static Map<String, Object> getCacheMap() {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            return principal != null ? principal.getCacheMap() : map;
        } catch (UnavailableSecurityManagerException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }
        return map;
    }

}
