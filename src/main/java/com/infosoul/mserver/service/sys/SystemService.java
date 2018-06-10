/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/free lance/infosys">infosys</a> All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.infosoul.mserver.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosoul.mserver.common.persistence.Page;
import com.infosoul.mserver.common.security.SystemAuthorizingRealm;
import com.infosoul.mserver.common.security.utils.Digests;
import com.infosoul.mserver.common.utils.Encodes;
import com.infosoul.mserver.common.utils.UserUtils;
import com.infosoul.mserver.entity.sys.Menu;
import com.infosoul.mserver.entity.sys.Role;
import com.infosoul.mserver.entity.sys.User;
import com.infosoul.mserver.repository.hibernate.sys.MenuDao;
import com.infosoul.mserver.repository.hibernate.sys.RoleDao;
import com.infosoul.mserver.repository.hibernate.sys.UserDao;
import com.infosoul.mserver.service.BaseService;


/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author free lance
 * @version 2013-5-15
 */
@Service
@Transactional(readOnly = true)
// 报错：Connection is read-only. Queries leading to data modification are not allowed
// @Transactional
public class SystemService extends BaseService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private SystemAuthorizingRealm systemRealm;

    // -- User Service --//

    public User getUser(String id) {
        return userDao.get(id);
    }

    public User getUserByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    public User getUserByNick(String nick) {
        return userDao.findByNick(nick);
    }

    public Page<User> findUser(Page<User> page, User user) {
        User currentUser = UserUtils.getUser();
        DetachedCriteria dc = userDao.createDetachedCriteria();

        // 如果不是超级管理员，则不显示超级管理员用户
        if (!currentUser.isAdmin()) {
            dc.add(Restrictions.ne("id", "1"));
        }
        dc.add(dataScopeFilter(currentUser, "office", ""));

        if (StringUtils.isNotEmpty(user.getLoginName())) {
            dc.add(Restrictions.like("loginName", "%" + user.getLoginName() + "%"));
        }
        if (StringUtils.isNotEmpty(user.getPhone())) {
            dc.add(Restrictions.like("phone", "%" + user.getPhone() + "%"));
        }
        if (StringUtils.isNotEmpty(user.getName())) {
            dc.add(Restrictions.like("name", "%" + user.getName() + "%"));
        }

        if (!currentUser.isAdmin()) {
            dc.add(Restrictions.eq(User.FIELD_DEL_FLAG, User.DEL_FLAG_NORMAL));
        }
        if (!StringUtils.isNotEmpty(page.getOrderBy())) {
            dc.addOrder(Order.desc("name"));
        }
        try {
            return userDao.find(page, dc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // 取用户的数据范围
    public String getDataScope(User user) {
        return dataScopeFilterString(user, "office", "");
    }

    public User getUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    public User getUserById(String id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        userDao.clear();
        userDao.save(user);
        systemRealm.clearAllCachedAuthorizationInfo();
    }

    @Transactional(readOnly = false)
    public void deleteUser(String id) {
        userDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void updatePasswordById(String id, String loginName, String newPassword) {
        userDao.updatePasswordById(entryptPassword(newPassword), id);
        systemRealm.clearCachedAuthorizationInfo(loginName);
    }

    @Transactional(readOnly = false)
    public void activateRetailerUserById(String id, Date date) {
        userDao.updateDelFlagById(User.DEL_FLAG_NORMAL, id);
        userDao.updateActiveDate(date, id);
    }

    @Transactional(readOnly = false)
    public void updateUserLoginInfo(String id) {
        userDao.updateLoginInfo(SecurityUtils.getSubject().getSession().getHost(), new Date(), id);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword
     *            明文密码
     * @param password
     *            密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    // -- Role Service --//

    public Role getRole(String id) {
        return roleDao.get(id);
    }

    public Role findRoleByName(String name) {
        return roleDao.findByName(name);
    }

    /**
     * 返回全部的角色清单，只有超级管理员才能管理超级管理员角色
     * @return
     */
    public List<Role> findAllRole() {
        User currentUser = UserUtils.getUser();
        return UserUtils.getRoleList(currentUser.isAdmin());
    }

    public List<User> findAllUser() {
        return userDao.findAllList();
    }

    @Transactional(readOnly = false)
    public void saveRole(Role role) {
        roleDao.clear();
        roleDao.save(role);
        systemRealm.clearAllCachedAuthorizationInfo();
        User currentUser = UserUtils.getUser();
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST + currentUser.isAdmin());
    }

    @Transactional(readOnly = false)
    public void deleteRole(String id) {
        roleDao.deleteById(id);
        systemRealm.clearAllCachedAuthorizationInfo();
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }

    @Transactional(readOnly = false)
    public Boolean outUserInRole(Role role, String userId) {
        User user = userDao.get(userId);
        List<String> roleIds = user.getRoleIdList();
        List<Role> roles = user.getRoleList();
        //
        if (roleIds.contains(role.getId())) {
            roles.remove(role);
            saveUser(user);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = false)
    public User assignUserToRole(Role role, String userId) {
        User user = userDao.get(userId);
        List<String> roleIds = user.getRoleIdList();
        if (roleIds.contains(role.getId())) {
            return null;
        }
        user.getRoleList().add(role);
        saveUser(user);
        return user;
    }

    // -- Menu Service --//

    public Menu getMenu(String id) {
        return menuDao.get(id);
    }

    public List<Menu> findAllMenu() {
        return UserUtils.getMenuList();
    }

    @Transactional(readOnly = false)
    public void saveMenu(Menu menu) {
        menu.setParent(this.getMenu(menu.getParent().getId()));
        String oldParentIds = menu.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");
        menuDao.clear();
        menuDao.save(menu);
        // 更新子节点 parentIds
        List<Menu> list = menuDao.findByParentIdsLike("%," + menu.getId() + ",%");
        for (Menu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
        }
        menuDao.save(list);
        systemRealm.clearAllCachedAuthorizationInfo();
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
    }

    @Transactional(readOnly = false)
    public void deleteMenu(String id) {
        menuDao.deleteById(id, "%," + id + ",%");
        systemRealm.clearAllCachedAuthorizationInfo();
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
    }


    // @Cacheable(value = GlobalStatic.qxCacheKey, key = "'getRolesAsString_'+#userId")
    public Set<String> getRoleAsString(String userId) throws Exception {
        List<Role> list = roleDao.findByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Set<String> set = new HashSet<String>();
        for (Role r : list) {
            set.add(r.getId());
        }
        return set;
    }


    // @Cacheable(value = GlobalStatic.qxCacheKey, key = "'getPermissionsAsString_'+#userId")
    public Set<String> getPermissionsAsString(String userId) throws Exception {
        List<Menu> setMenu = findAllMenuByUserId(userId);
        if (CollectionUtils.isEmpty(setMenu)) {
            return null;
        }
        Set<String> set = new HashSet<String>();
        for (Menu m : setMenu) {
            if (StringUtils.isBlank(m.getHref())) {
                continue;
            }
            set.add(m.getHref());
        }
        return set;
    }

    private List<Menu> findAllMenuByUserId(String userId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        List<Role> roles = roleDao.findByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        List<Menu> list = new ArrayList<Menu>();
        for (Role role : roles) {
            List<Menu> menus = menuDao.findByRoleId(role.getId());
            if (CollectionUtils.isEmpty(menus)) {
                continue;
            }
            list.addAll(menus);
        }

        return list;

    }

    /**
     * 根据菜单type取menu
     * @param type
     * @return
     */
    public List<Menu> findMenuByType(String type) {
        return menuDao.findByType(type);
    }

    /**
     * 保存头像路径
     *
     * @param photo
     * @param loginName
     */
    @Transactional(readOnly = false)
    public void updatePhotoByLoginName(String photo, String loginName) {
        userDao.updatePhotoById(photo, loginName);
    }

}
