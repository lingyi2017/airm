DROP TABLE IF EXISTS airm_device;
CREATE TABLE airm_device
(
  id VARCHAR (64) NOT NULL COMMENT '编号',
  name VARCHAR (64) COMMENT '设备名称',
  device_id VARCHAR(64) NOT NULL COMMENT '设备ID',
  register CHAR (1) DEFAULT '0' COMMENT '设备是否注册：0-未注册；1-注册',
  lon DECIMAL (9, 6) COMMENT '经度',
  lat DECIMAL (9, 6) COMMENT '纬度',
  status VARCHAR (1) COMMENT '状态：1-正常；2-告警；3-离线',
  station VARCHAR (128) COMMENT '检测站名称',
  address VARCHAR (256) COMMENT '设备地址',
  sensor_name1 VARCHAR (64) COMMENT '传感器1名称',
  sensor_unit1 VARCHAR (16)  COMMENT '传感器1单位',
  sensor_decimal1 tinyint(1) COMMENT '传感器1小数位',
  sensor_name2 VARCHAR (64) COMMENT '传感器2名称',
  sensor_unit2 VARCHAR (16)  COMMENT '传感器2单位',
  sensor_decimal2 tinyint(1) COMMENT '传感器2小数位',
  sensor_name3 VARCHAR (64) COMMENT '传感器3名称',
  sensor_unit3 VARCHAR (16)  COMMENT '传感器3单位',
  sensor_decimal3 tinyint(1) COMMENT '传感器3小数位',
  sensor_name4 VARCHAR (64) COMMENT '传感器4名称',
  sensor_unit4 VARCHAR (16)  COMMENT '传感器4单位',
  sensor_decimal4 tinyint(1) COMMENT '传感器4小数位',
  sensor_name5 VARCHAR (64) COMMENT '传感器5名称',
  sensor_unit5 VARCHAR (16)  COMMENT '传感器5单位',
  sensor_decimal5 tinyint(1) COMMENT '传感器5小数位',
  sensor_name6 VARCHAR (64) COMMENT '传感器6名称',
  sensor_unit6 VARCHAR (16)  COMMENT '传感器6单位',
  sensor_decimal6 tinyint(1) COMMENT '传感器6小数位',
  create_date DATETIME COMMENT '添加时间',
  update_date DATETIME COMMENT '更新时间',
  remarks varchar(255) COMMENT '备注信息',
  del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  UNIQUE KEY (device_id),
  PRIMARY KEY (id)
) COMMENT = '设备表';

DROP TABLE IF EXISTS airm_record;
CREATE TABLE airm_record
(
  id VARCHAR (64) NOT NULL COMMENT '编号',
  device_id VARCHAR (16) NOT NULL COMMENT '设备ID',
  device_name VARCHAR (64) COMMENT '设备名称',
  status CHAR (1) COMMENT '状态：1-正常；2-告警',
  sensor_val1 DOUBLE (6, 2) COMMENT '传感器1采样值',
  sensor_status1 CHAR (1) COMMENT '传感器1状态',
  sensor_val2 DOUBLE (6, 2) COMMENT '传感器2采样值',
  sensor_status2 CHAR (1) COMMENT '传感器2状态',
  sensor_val3 DOUBLE (6, 2) COMMENT '传感器3采样值',
  sensor_status3 CHAR (1) COMMENT '传感器3状态',
  sensor_val4 DOUBLE (6, 2) COMMENT '传感器4采样值',
  sensor_status4 CHAR (1) COMMENT '传感器4状态',
  sensor_val5 DOUBLE (6, 2) COMMENT '传感器5采样值',
  sensor_status5 CHAR (1) COMMENT '传感器5状态',
  sensor_val6 DOUBLE (6, 2) COMMENT '传感器6采样值',
  sensor_status6 CHAR (1) COMMENT '传感器6状态',
  sensor_val7 DOUBLE (6, 2) COMMENT '传感器7采样值',
  sensor_status7 CHAR (1) COMMENT '传感器7状态',
  sensor_val8 DOUBLE (6, 2) COMMENT '传感器8采样值',
  sensor_status8 CHAR (1) COMMENT '传感器8状态',
  sensor_val9 DOUBLE (6, 2) COMMENT '传感器9采样值',
  sensor_status9 CHAR (1) COMMENT '传感器9状态',
  sensor_val10 DOUBLE (6, 2) COMMENT '传感器10采样值',
  sensor_status10 CHAR (1) COMMENT '传感器10状态',
  sensor_val11 DOUBLE (6, 2) COMMENT '传感器11采样值',
  sensor_status11 CHAR (1) COMMENT '传感器11状态',
  sensor_val12 DOUBLE (6, 2) COMMENT '传感器12采样值',
  sensor_status12 CHAR (1) COMMENT '传感器12状态',
  create_date DATETIME COMMENT '添加时间',
  update_date DATETIME COMMENT '更新时间',
  del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '历史记录表';

DROP TABLE IF EXISTS airm_sensor;
CREATE TABLE airm_sensor
(
  id VARCHAR (64) NOT NULL COMMENT '编号',
  serial_num INT COMMENT '传感器编号',
  name VARCHAR (32) COMMENT '传感器名称',
  create_date DATETIME COMMENT '添加时间',
  del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '传感器表';

DROP TABLE IF EXISTS airm_latch_config;
CREATE TABLE airm_latch_config
(
  id VARCHAR (64) NOT NULL COMMENT '编号',
  serial_num INT COMMENT '传感器编号',
  name VARCHAR (64) COMMENT '传感器名称',
  max_val DOUBLE (6, 2) COMMENT '参数最大值',
  create_date DATETIME COMMENT '添加时间',
  del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
  PRIMARY KEY (id)
) COMMENT = '门限配置';

DROP TABLE IF EXISTS airm_record_read;
CREATE TABLE airm_user_record
(
  id VARCHAR (64) NOT NULL COMMENT '编号',
  user_id VARCHAR (64) COMMENT '用户ID',
  record_id VARCHAR (64) COMMENT '设备记录ID',
  status CHAR (1) DEFAULT '0' COMMENT '状态：0-未读；1-已读',
  PRIMARY KEY (id)
) COMMENT = '用户-设备告警记录表';

-- 创建索引
CREATE INDEX airm_device_device_id ON airm_device (device_id ASC);
CREATE INDEX airm_record_device_id ON airm_record(device_id ASC);
CREATE INDEX airm_user_record_user_id ON airm_user_record(user_id ASC);
CREATE INDEX airm_user_record_record_id ON airm_user_record(record_id ASC);

-- 初始化传感器数据
INSERT INTO `airm_sensor` VALUES ('1', 0x00, '无', now(), 0);
INSERT INTO `airm_sensor` VALUES ('2', 0x01, '可燃气体', now(), 0);
INSERT INTO `airm_sensor` VALUES ('3', 0x02, '一氧化碳', now(), 0);
INSERT INTO `airm_sensor` VALUES ('4', 0x03, '氧气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('5', 0x04, '氢气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('6', 0x05, '甲烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('7', 0x06, '丙烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('8', 0x07, '二氧化碳', now(), 0);
INSERT INTO `airm_sensor` VALUES ('9', 0x08, '臭氧', now(), 0);
INSERT INTO `airm_sensor` VALUES ('10', 0x09, '硫化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('11', 0x0A, '二氧化硫', now(), 0);
INSERT INTO `airm_sensor` VALUES ('12', 0x0B, '氨气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('13', 0x0C, '氯气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('14', 0x0D, '环氧乙烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('15', 0x0E, '氯化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('16', 0x0F, '磷化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('17', 0x10, '溴化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('18', 0x11, '氰化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('19', 0x12, '三氢化砷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('20', 0x13, '氟化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('21', 0x14, '溴气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('22', 0x15, '一氧化氮', now(), 0);
INSERT INTO `airm_sensor` VALUES ('23', 0x16, '二氧化氮', now(), 0);
INSERT INTO `airm_sensor` VALUES ('24', 0x17, '氮氧化物', now(), 0);
INSERT INTO `airm_sensor` VALUES ('25', 0x18, '二氧化氯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('26', 0x19, '硅烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('27', 0x1A, '二硫化碳', now(), 0);
INSERT INTO `airm_sensor` VALUES ('28', 0x1B, '氟气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('29', 0x1C, '乙硼烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('30', 0x1D, '锗烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('31', 0x1E, '氮气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('32', 0x1F, '四氢噻吩', now(), 0);
INSERT INTO `airm_sensor` VALUES ('33', 0x20, '乙炔', now(), 0);
INSERT INTO `airm_sensor` VALUES ('34', 0x21, '乙烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('35', 0x22, '甲醛', now(), 0);
INSERT INTO `airm_sensor` VALUES ('36', 0x23, '液化石油气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('37', 0x24, '碳氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('38', 0x25, '苯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('39', 0x26, '过氧化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('40', 0x27, 'VOC', now(), 0);
INSERT INTO `airm_sensor` VALUES ('41', 0x28, '六氟化硫 ', now(), 0);
INSERT INTO `airm_sensor` VALUES ('42', 0x29, '甲苯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('43', 0x2A, '联乙烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('44', 0x2B, '氧硫化碳', now(), 0);
INSERT INTO `airm_sensor` VALUES ('45', 0x2C, '联氨', now(), 0);
INSERT INTO `airm_sensor` VALUES ('46', 0x2D, '硒化氢', now(), 0);
INSERT INTO `airm_sensor` VALUES ('47', 0x2E, '苯乙烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('48', 0x2F, '异丁烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('49', 0x30, '亚甲基', now(), 0);
INSERT INTO `airm_sensor` VALUES ('50', 0x31, '笑气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('51', 0x32, '天然气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('52', 0x33, '光气', now(), 0);
INSERT INTO `airm_sensor` VALUES ('53', 0x34, '氯乙烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('54', 0x35, '甲醇', now(), 0);
INSERT INTO `airm_sensor` VALUES ('55', 0x36, '乙醇', now(), 0);
INSERT INTO `airm_sensor` VALUES ('56', 0x37, '异丙醇', now(), 0);
INSERT INTO `airm_sensor` VALUES ('57', 0x38, '丙酮', now(), 0);
INSERT INTO `airm_sensor` VALUES ('58', 0x39, '乙醛', now(), 0);
INSERT INTO `airm_sensor` VALUES ('59', 0x3A, '丙烯腈', now(), 0);
INSERT INTO `airm_sensor` VALUES ('60', 0x3B, '二甲基硫醚', now(), 0);
INSERT INTO `airm_sensor` VALUES ('61', 0x3C, '环氧氯丙烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('62', 0x3D, '乙酸乙酯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('63', 0x3E, '甲基乙基酮', now(), 0);
INSERT INTO `airm_sensor` VALUES ('64', 0x3F, '甲硫醇', now(), 0);
INSERT INTO `airm_sensor` VALUES ('65', 0x40, '四氯乙烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('66', 0x41, '亚硫酰氯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('67', 0x42, '乙酸乙烯酯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('68', 0x43, '硫醇TBM', now(), 0);
INSERT INTO `airm_sensor` VALUES ('69', 0x44, 'TVOC', now(), 0);
INSERT INTO `airm_sensor` VALUES ('70', 0x45, '环己烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('71', 0x46, '三氯乙烯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('72', 0x47, '二甲苯', now(), 0);
INSERT INTO `airm_sensor` VALUES ('73', 0x48, '氟利昂', now(), 0);
INSERT INTO `airm_sensor` VALUES ('74', 0x49, '一氯甲烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('75', 0x4A, '二氯甲烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('76', 0x4B, '三氯甲烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('77', 0x4C, '一甲胺', now(), 0);
INSERT INTO `airm_sensor` VALUES ('78', 0x4D, '正戊烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('79', 0x4E, '正己烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('80', 0x4F, '正庚烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('81', 0x50, '异辛烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('82', 0x51, '乙烷', now(), 0);
INSERT INTO `airm_sensor` VALUES ('83', 0x52, '石油醚', now(), 0);
INSERT INTO `airm_sensor` VALUES ('84', 0x53, '丁烷', now(), 0);