/***********************************************
 * CONFIDENTIAL AND PROPRIETARY
 * 
 * The source code and other information contained herein is the confidential and the exclusive
 * property of ZIH Corp. and is subject to the terms and conditions in your end user license
 * agreement. This source code, and any other information contained herein, shall not be copied,
 * reproduced, published, displayed or distributed, in whole or in part, in any medium, by any
 * means, for any purpose except as expressly permitted under such license agreement.
 * 
 * Copyright ZIH Corp. 2012
 * 
 * ALL RIGHTS RESERVED
 ***********************************************/

package com.infosoul.mserver.common.utils.airm;

/**
 * ppm 或 ppb转换成ug/m3
 */
public class PpmConversionUtils {

    static float[] molecularVal = {0, // nothing
            0, // flammable
            28, // co
            0, // o2
            2, // h2
            16, // 甲烷
            44, // 丙烷
            44, // CO2
            48, // o3
            34, // 硫化氢
            64, // 二氧化硫
            17, // 氨气
            71, // 氯气
            44, // 环氧乙烷
            (float) 36.5, // 氯化氢
            34, // 磷化氢
            81, // 溴化氢
            27, // 氰化氢
            78, // 三氢化砷
            20, // 氟化氢
            160, // 溴气
            30, // 一氧化氮
            46, // 二氧化氮
            0, // 氮氧化物
            (float) 67.5, // 二氧化氯
            32, // 硅烷
            76, // 二硫化碳
            38, // 氟气
            0, // 乙硼烷
            0, 0, 4, 26, // 乙炔
            28, // 乙烯
            30, // 甲醛
            0, 0, 0, 0, 0, 0, 92, // 甲苯
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 46, // 乙醇
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

    };

    public static double retUgVal(byte unit, byte type, Double val) {
        if (null == val) {
            return val;
        }
        double ret = 0;
        if (unit == 0x00) {
            // do something %LEL convert to ug/
            return val;
        } else if (unit == 0x01) {
            // do something %VOL to ug/m3

            return val;
        } else if (unit == 0x02)// ppm to ug/m3
        {
            ret = (val * molecularVal[type] / 22.4) * 1000;
            return ret;
        } else if (unit == 0x03)// ppb to ug/m3
        {
            ret = ((val / 1000) * molecularVal[type] / 22.4) * 1000;
            return ret;
        } else
            return -1;

    }

}
