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
  sensor_name2 VARCHAR (64) COMMENT '传感器2名称',
  sensor_unit2 VARCHAR (16)  COMMENT '传感器2单位',
  sensor_name3 VARCHAR (64) COMMENT '传感器3名称',
  sensor_unit3 VARCHAR (16)  COMMENT '传感器3单位',
  sensor_name4 VARCHAR (64) COMMENT '传感器4名称',
  sensor_unit4 VARCHAR (16)  COMMENT '传感器4单位',
  sensor_name5 VARCHAR (64) COMMENT '传感器5名称',
  sensor_unit5 VARCHAR (16)  COMMENT '传感器5单位',
  sensor_name6 VARCHAR (64) COMMENT '传感器6名称',
  sensor_unit6 VARCHAR (16)  COMMENT '传感器6单位',
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