SET NAMES 'utf8';
CREATE DATABASE IF NOT EXISTS airm DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

USE airm;

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `sys`
-- ----------------------------
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict
(
  id varchar(64) NOT NULL COMMENT '编号',
  label varchar(100) NOT NULL COMMENT '标签名',
  value varchar(100) NOT NULL COMMENT '数据值',
  type varchar(100) NOT NULL COMMENT '类型',
  description varchar(100) NOT NULL COMMENT '描述',
  sort int NOT NULL COMMENT '排序（升序）',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  update_by varchar(64) COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '字典表';


DROP TABLE IF EXISTS sys_area;
CREATE TABLE sys_area
(
  id varchar(64) NOT NULL COMMENT '编号',
  parent_id varchar(64) NOT NULL COMMENT '父级编号',
  parent_ids varchar(2000) NOT NULL COMMENT '所有父级编号',
  code varchar(100) COMMENT '区域编码',
  name varchar(100) NOT NULL COMMENT '区域名称',
  type char(1) COMMENT '区域类型',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  update_by varchar(64) COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '区域表';

-- ----------------------------
-- type=0 通用菜单，type=1 其它
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu
(
  id varchar(64) NOT NULL COMMENT '编号',
  parent_id varchar(64) NOT NULL COMMENT '父级编号',
  parent_ids varchar(2000) NOT NULL COMMENT '所有父级编号',
  name varchar(100) NOT NULL COMMENT '菜单名称',
  href varchar(255) COMMENT '链接',
  target varchar(20) COMMENT '目标',
  icon varchar(100) COMMENT '图标',
  sort int NOT NULL COMMENT '排序（升序）',
  is_show char(1) NOT NULL COMMENT '是否在菜单中显示',
  type char(1) DEFAULT '0' NOT NULL COMMENT '类型',
  permission varchar(200) COMMENT '权限标识',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  update_by varchar(64) COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '菜单表';


DROP TABLE IF EXISTS sys_office;
CREATE TABLE sys_office
(
  id varchar(64) NOT NULL COMMENT '编号',
  parent_id varchar(64) NOT NULL COMMENT '父级编号',
  parent_ids varchar(2000) NOT NULL COMMENT '所有父级编号',
  area_id varchar(64) NOT NULL COMMENT '归属区域',
  code varchar(100) COMMENT '区域编码',
  name varchar(100) NOT NULL COMMENT '机构名称',
  type char(1) NOT NULL COMMENT '机构类型',
  grade char(1) NOT NULL COMMENT '机构等级',
  address varchar(255) COMMENT '联系地址',
  zip_code varchar(100) COMMENT '邮政编码',
  master varchar(100) COMMENT '负责人',
  phone varchar(200) COMMENT '电话',
  fax varchar(200) COMMENT '传真',
  email varchar(200) COMMENT '邮箱',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  update_by varchar(64) COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '机构表';



DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
  id varchar(64) NOT NULL COMMENT '编号',
  office_id varchar(64) COMMENT '归属机构',
  name varchar(100) NOT NULL COMMENT '角色名称',
  data_scope char(1) COMMENT '数据范围',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  update_by varchar(64) COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '角色表';


DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu
(
  role_id varchar(64) NOT NULL COMMENT '角色编号',
  menu_id varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (role_id, menu_id)
) COMMENT = '角色-菜单';


DROP TABLE IF EXISTS sys_role_office;
CREATE TABLE sys_role_office
(
  role_id varchar(64) NOT NULL COMMENT '角色编号',
  office_id varchar(64) NOT NULL COMMENT '机构编号',
  PRIMARY KEY (role_id, office_id)
) COMMENT = '角色-机构';


DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(
  id varchar(64) NOT NULL COMMENT '编号',
  company_id varchar(64) NOT NULL COMMENT '归属公司',
  office_id varchar(64) NOT NULL COMMENT '归属部门',
  login_name varchar(100) binary NOT NULL COMMENT '登录名',
  password varchar(100) NOT NULL COMMENT '密码',
  no varchar(100) COMMENT '工号',
  name varchar(100) COMMENT '姓名',
  email varchar(200) COMMENT '邮箱',
  phone varchar(200) COMMENT '电话',
  mobile varchar(200) COMMENT '手机',
  user_type char(1) COMMENT '用户类型',
  login_ip varchar(100) COMMENT '最后登陆IP',
  login_date datetime COMMENT '最后登陆时间',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  update_by varchar(64) COMMENT '更新者',
  update_date datetime COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag char(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  qq varchar(100) COMMENT 'QQ',
  weixin varchar(100) COMMENT '微信',
  nick varchar(100) COMMENT '昵称',
  photo varchar(100) COMMENT '头像',
  sex char(1) COMMENT '性别',
  address varchar(255) COMMENT '地址',
  active_date date DEFAULT NULL COMMENT '激活日期',
  birthday date DEFAULT NULL COMMENT '生日',
  postalcode varchar(200) COMMENT '邮编',
  PRIMARY KEY (id)
) COMMENT = '用户表';


DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role
(
  user_id varchar(64) NOT NULL COMMENT '用户编号',
  role_id varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (user_id, role_id)
) COMMENT = '用户-角色';


DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log
(
  id varchar(64) NOT NULL COMMENT '编号',
  type char(1) DEFAULT '1' COMMENT '日志类型',
  create_by varchar(64) COMMENT '创建者',
  create_date datetime COMMENT '创建时间',
  remote_addr varchar(255) COMMENT '操作IP地址',
  user_agent varchar(255) COMMENT '用户代理',
  request_uri varchar(255) COMMENT '请求URI',
  method varchar(5) COMMENT '操作方式',
  params text COMMENT '操作提交的数据',
  exception text COMMENT '异常信息',
  PRIMARY KEY (id)
) COMMENT = '日志表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1',  '正常', '0', 'del_flag', '删除标记', '10', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('2',  '删除', '1', 'del_flag', '删除标记', '20', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('3',  '显示', '1', 'show_hide', '显示/隐藏', '10', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('4',  '隐藏', '0', 'show_hide', '显示/隐藏', '20', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('5',  '是', '1', 'yes_no', '是/否', '10', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('6',  '否', '0', 'yes_no', '是/否', '20', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('7',  '红色', 'red',     'color', '颜色值', '10', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('8',  '绿色', 'green',   'color', '颜色值', '20', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('9',  '蓝色', 'blue',    'color', '颜色值', '30', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('10', '黄色', 'yellow',  'color', '颜色值', '40', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('11', '橙色', 'orange',  'color', '颜色值', '50', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('12', '默认主题', 'default',  'theme', '主题方案', '10', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('13', '天蓝主题', 'cerulean', 'theme', '主题方案', '20', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('14', '橙色主题', 'readable', 'theme', '主题方案', '30', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('15', '红色主题', 'united',   'theme', '主题方案', '40', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('16', 'Flat主题', 'flat',     'theme', '主题方案', '50', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('27', '所有数据',             '1', 'sys_data_scope', '数据范围', '10',  '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('28', '所在机构及以下数据',   '2', 'sys_data_scope', '数据范围', '20',  '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('29', '所在机构数据',         '3', 'sys_data_scope', '数据范围', '30',  '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('30', '所在部门及以下数据',   '4', 'sys_data_scope', '数据范围', '40', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('31', '所在部门数据',         '5', 'sys_data_scope', '数据范围', '50', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('32', '仅本人数据',           '8', 'sys_data_scope', '数据范围', '40',  '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('33', '按明细设置',           '9', 'sys_data_scope', '数据范围', '50', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('34', '超级系统管理员', '0', 'sys_user_type', '用户类型', '10', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('35', '管理员', '1', 'sys_user_type', '用户类型', '20', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('36', '普通工作人员', '2', 'sys_user_type', '用户类型', '30', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('37', '男', '0', 'sys_user_sex', '性别', '10', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('38', '女', '1', 'sys_user_sex', '性别', '20', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('39', '正常', '1', 'device_status', '设备状态', '10', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('40', '告警', '2', 'device_status', '设备状态', '20', 'admin', now(), 'admin', now(), null, '0');
INSERT INTO `sys_dict` VALUES ('41', '离线', '3', 'device_status', '设备状态', '30', 'admin', now(), 'admin', now(), null, '0');

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '顶级菜单', null, null, null, '0', '1', '0', null, null, null, null, now(), null, '0');

-- 系统管理
INSERT INTO `sys_menu` VALUES ('2', '1', '0,1,', '系统管理', null, null, null, '700', '1', '0', null, null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('3', '2', '0,1,2,', '系统设置', null, null, null, '980', '1', '0', null, null, null, null, null, null, '0');
INSERT INTO `sys_menu` VALUES ('4', '3', '0,1,2,3,', '菜单管理', '/sys/menu/', null, 'list-alt', '30', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('5', '4', '0,1,2,3,4,', '查看', null, null, null, '30', '0', '0', 'sys:menu:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('6', '4', '0,1,2,3,4,', '修改', null, null, null, '30', '0', '0', 'sys:menu:edit', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('7', '3', '0,1,2,3,', '角色管理', '/sys/role/', null, 'lock', '50', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('8', '7', '0,1,2,3,7,', '查看', null, null, null, '30', '0', '0', 'sys:role:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('9', '7', '0,1,2,3,7,', '修改', null, null, null, '30', '0', '0', 'sys:role:edit', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '0,1,2,3,', '字典管理', '/sys/dict/', null, 'th-list', '60', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('11', '10', '0,1,2,3,10,', '查看', null, null, null, '30', '0', '0', 'sys:dict:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('12', '10', '0,1,2,3,10,', '修改', null, null, null, '30', '0', '0', 'sys:dict:edit', '1', now(), '1', now(), null, '0');

-- 机构管理
INSERT INTO `sys_menu` VALUES ('13', '2', '0,1,2,', '机构用户', null, null, null, '900', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('20', '13', '0,1,2,13,', '用户管理', '/sys/user/', null, 'user', '30', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('21', '20', '0,1,2,13,20,', '查看', null, null, null, '30', '0', '0', 'sys:user:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('22', '20', '0,1,2,13,20,', '修改', null, null, null, '30', '0', '0', 'sys:user:edit', '1', now(), '1', now(), null, '0');

-- 我的面板
INSERT INTO `sys_menu` VALUES ('27', '1', '0,1,', '我的面板', null, null, 'user', '800', '1', '0', '', null, now(), 'admin', null, null, '0');
INSERT INTO `sys_menu` VALUES ('28', '27', '0,1,27,', '个人信息', null, null, null, '990', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('29', '28', '0,1,27,28,', '个人信息', '/sys/user/info', null, 'user', '30', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('30', '28', '0,1,27,28,', '修改密码', '/sys/user/modifyPwd', null, 'lock', '40', '1', '0',  null, '1', now(), '1', now(), null, '0');

-- 地图展示
INSERT INTO `sys_menu` VALUES ('100', '1', '0,1,', '地图展示', null, null, 'user', '100', '1', '0', '', null, now(), 'admin', null, null, '0');
INSERT INTO `sys_menu` VALUES ('101', '100', '0,1,100,', '地图展示', null, null, null, '100', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('102', '101', '0,1,100,101,', '地图展示', '/airm/map', null, 'th', '10', '1', '0', null, '1', now(), '1', now(), null, '0');

-- 设备管理
INSERT INTO `sys_menu` VALUES ('200', '1', '0,1,', '设备管理', null, null, 'user', '100', '1', '0', '', null, now(), 'admin', null, null, '0');
INSERT INTO `sys_menu` VALUES ('201', '200', '0,1,200,', '设备管理', null, null, null, '100', '1', '0', null, '1', now(), '1', now(), null, '0');

INSERT INTO `sys_menu` VALUES ('202', '201', '0,1,200,201,', '设备管理', '/airm/device/list', null, 'th-list', '10', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('203', '202', '0,1,200,201,202,', '查看', null, null, null, '20', '0', '0', 'airm:device:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('204', '202', '0,1,200,201,202,', '修改', null, null, null, '20', '0', '0', 'airm:device:edit', '1', now(), '1', now(), null, '0');

INSERT INTO `sys_menu` VALUES ('205', '201', '0,1,200,201,', '门限配置', '/airm/latchConfig/list', null, 'th-list', '20', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('206', '202', '0,1,200,201,205,', '查看', null, null, null, '20', '0', '0', 'airm:latchConfig:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('207', '202', '0,1,200,201,205,', '配置', null, null, null, '20', '0', '0', 'airm:latchConfig:edit', '1', now(), '1', now(), null, '0');

INSERT INTO `sys_menu` VALUES ('208', '201', '0,1,200,201,', '传感器管理', '/airm/sensor/list', null, 'th-list', '20', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('209', '208', '0,1,200,201,208,', '查看', null, null, null, '20', '0', '0', 'airm:sensor:view', '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('210', '208', '0,1,200,201,208,', '修改', null, null, null, '20', '0', '0', 'airm:sensor:edit', '1', now(), '1', now(), null, '0');

-- 报表统计
INSERT INTO `sys_menu` VALUES ('300', '1', '0,1,', '报表统计', null, null, 'list', '100', '1', '0', '', null, now(), 'admin', null, null, '0');
INSERT INTO `sys_menu` VALUES ('301', '300', '0,1,300,', '报表统计', null, null, null, '100', '1', '0', null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_menu` VALUES ('302', '301', '0,1,300,301,', '参数指标', '/airm/param/stat', null, 'list', '10', '1', '0', null, '1', now(), '1', now(), null, '0');

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', '0', '0,', '100000', '中国', '1', '1', now(), '1', now(), null, '0');

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '0', '0,', '1', '100000', '一级机构', '1', '1', null, null, null, null, null, null, null,null, null, null, null, '0');
INSERT INTO `sys_office` VALUES ('2', '1', '0,1,', '1', '100001', '一级部门', '2', '1', null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `sys_office` VALUES ('27', '1', '0,1,', '1', '00000003', '用户机构', '1', '2', null, null, null, null, null, null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_office` VALUES ('28', '27', '0,1,27,', '1', '00000004', '用户部门', '2', '2', null, null, null, null, null, null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_office` VALUES ('29', '1', '0,1,', '1', '00000000', '商户机构', '1', '2', null, null, null, null, null, null, '1', now(), '1', now(), null, '0');
INSERT INTO `sys_office` VALUES ('30', '29', '0,1,29,', '1', '00000001', '商户部门', '2', '2', null, null, null, null, null, null, '1', now(), '1', now(), null, '0');

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('admin', '1', '超级系统管理员',   '1', '1', now(), '1', now(), null, '0');

INSERT INTO `sys_user` VALUES ('admin', '1', '2', 'admin', '02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032', '0001', '超级系统管理员',   'admin@admin.com', '02885858383', '13388888888', '0', null, null, null, null, '1', null, '最高管理员', '0', null, null, null, null, '0', null, null, null,null);

INSERT INTO `sys_user_role` VALUES ('admin', 'admin');

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('admin', '1');
INSERT INTO `sys_role_menu` VALUES ('admin', '10');
INSERT INTO `sys_role_menu` VALUES ('admin', '11');
INSERT INTO `sys_role_menu` VALUES ('admin', '12');
INSERT INTO `sys_role_menu` VALUES ('admin', '13');
INSERT INTO `sys_role_menu` VALUES ('admin', '14');
INSERT INTO `sys_role_menu` VALUES ('admin', '15');
INSERT INTO `sys_role_menu` VALUES ('admin', '16');
INSERT INTO `sys_role_menu` VALUES ('admin', '17');
INSERT INTO `sys_role_menu` VALUES ('admin', '18');
INSERT INTO `sys_role_menu` VALUES ('admin', '19');
INSERT INTO `sys_role_menu` VALUES ('admin', '2');
INSERT INTO `sys_role_menu` VALUES ('admin', '20');
INSERT INTO `sys_role_menu` VALUES ('admin', '21');
INSERT INTO `sys_role_menu` VALUES ('admin', '22');
INSERT INTO `sys_role_menu` VALUES ('admin', '26');
INSERT INTO `sys_role_menu` VALUES ('admin', '27');
INSERT INTO `sys_role_menu` VALUES ('admin', '28');
INSERT INTO `sys_role_menu` VALUES ('admin', '29');
INSERT INTO `sys_role_menu` VALUES ('admin', '3');
INSERT INTO `sys_role_menu` VALUES ('admin', '30');
INSERT INTO `sys_role_menu` VALUES ('admin', '31');
INSERT INTO `sys_role_menu` VALUES ('admin', '32');
INSERT INTO `sys_role_menu` VALUES ('admin', '4');
INSERT INTO `sys_role_menu` VALUES ('admin', '5');
INSERT INTO `sys_role_menu` VALUES ('admin', '6');
INSERT INTO `sys_role_menu` VALUES ('admin', '7');
INSERT INTO `sys_role_menu` VALUES ('admin', '8');
INSERT INTO `sys_role_menu` VALUES ('admin', '9');
INSERT INTO `sys_role_menu` VALUES ('operation', '1');
INSERT INTO `sys_role_menu` VALUES ('operation', '100');
INSERT INTO `sys_role_menu` VALUES ('operation', '101');
INSERT INTO `sys_role_menu` VALUES ('operation', '102');
INSERT INTO `sys_role_menu` VALUES ('operation', '103');
INSERT INTO `sys_role_menu` VALUES ('operation', '110');
INSERT INTO `sys_role_menu` VALUES ('operation', '111');
INSERT INTO `sys_role_menu` VALUES ('operation', '112');
INSERT INTO `sys_role_menu` VALUES ('operation', '113');
INSERT INTO `sys_role_menu` VALUES ('operation', '114');
INSERT INTO `sys_role_menu` VALUES ('operation', '115');
INSERT INTO `sys_role_menu` VALUES ('operation', '116');
INSERT INTO `sys_role_menu` VALUES ('operation', '120');
INSERT INTO `sys_role_menu` VALUES ('operation', '121');
INSERT INTO `sys_role_menu` VALUES ('operation', '122');
INSERT INTO `sys_role_menu` VALUES ('operation', '123');
INSERT INTO `sys_role_menu` VALUES ('operation', '124');
INSERT INTO `sys_role_menu` VALUES ('operation', '125');
INSERT INTO `sys_role_menu` VALUES ('operation', '126');
INSERT INTO `sys_role_menu` VALUES ('operation', '130');
INSERT INTO `sys_role_menu` VALUES ('operation', '131');
INSERT INTO `sys_role_menu` VALUES ('operation', '132');
INSERT INTO `sys_role_menu` VALUES ('operation', '133');
INSERT INTO `sys_role_menu` VALUES ('operation', '134');
INSERT INTO `sys_role_menu` VALUES ('operation', '135');
INSERT INTO `sys_role_menu` VALUES ('operation', '136');
INSERT INTO `sys_role_menu` VALUES ('operation', '33');
INSERT INTO `sys_role_menu` VALUES ('operation', '50');
INSERT INTO `sys_role_menu` VALUES ('operation', '51');
INSERT INTO `sys_role_menu` VALUES ('operation', '52');
INSERT INTO `sys_role_menu` VALUES ('operation', '53');
INSERT INTO `sys_role_menu` VALUES ('operation', '54');
INSERT INTO `sys_role_menu` VALUES ('operation', '54edit');
INSERT INTO `sys_role_menu` VALUES ('operation', '54view');
INSERT INTO `sys_role_menu` VALUES ('operation', '55');
INSERT INTO `sys_role_menu` VALUES ('operation', '56');
INSERT INTO `sys_role_menu` VALUES ('operation', '57');
INSERT INTO `sys_role_menu` VALUES ('operation', '58');
INSERT INTO `sys_role_menu` VALUES ('operation', '60');
INSERT INTO `sys_role_menu` VALUES ('operation', '61');
INSERT INTO `sys_role_menu` VALUES ('operation', '62');
INSERT INTO `sys_role_menu` VALUES ('operation', '63');
INSERT INTO `sys_role_menu` VALUES ('operation', '64');
INSERT INTO `sys_role_menu` VALUES ('operation', '65');
INSERT INTO `sys_role_menu` VALUES ('operation', '66');
INSERT INTO `sys_role_menu` VALUES ('operation', '70');
INSERT INTO `sys_role_menu` VALUES ('operation', '71');
INSERT INTO `sys_role_menu` VALUES ('operation', '72');
INSERT INTO `sys_role_menu` VALUES ('operation', '73');
INSERT INTO `sys_role_menu` VALUES ('operation', '74');
INSERT INTO `sys_role_menu` VALUES ('operation', '75');
INSERT INTO `sys_role_menu` VALUES ('operation', '76');
INSERT INTO `sys_role_menu` VALUES ('operation', '77');
INSERT INTO `sys_role_menu` VALUES ('operation', '78');
INSERT INTO `sys_role_menu` VALUES ('operation', '79');
INSERT INTO `sys_role_menu` VALUES ('operation', '80');
INSERT INTO `sys_role_menu` VALUES ('operation', '81');
INSERT INTO `sys_role_menu` VALUES ('operation', '82');
INSERT INTO `sys_role_menu` VALUES ('operation', '83');
INSERT INTO `sys_role_menu` VALUES ('operation', '90');
INSERT INTO `sys_role_menu` VALUES ('operation', '91');
INSERT INTO `sys_role_menu` VALUES ('operation', '92');
INSERT INTO `sys_role_menu` VALUES ('operation', '93');

/* Create Indexes */
CREATE INDEX sys_office_parent_id ON sys_office (parent_id ASC);
CREATE INDEX sys_office_del_flag ON sys_office (del_flag ASC);
CREATE INDEX sys_role_del_flag ON sys_role (del_flag ASC);
CREATE INDEX sys_user_office_id ON sys_user (office_id ASC);
CREATE INDEX sys_user_login_name ON sys_user (login_name ASC);
CREATE INDEX sys_user_company_id ON sys_user (company_id ASC);
CREATE INDEX sys_user_del_flag ON sys_user (del_flag ASC);
CREATE INDEX sys_user_mobile ON sys_user (mobile ASC);

SET FOREIGN_KEY_CHECKS = 1;
