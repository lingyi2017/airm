/***********************************************
 * CONFIDENTIAL AND PROPRIETARY 
 * 
 * The source code and other information contained herein is the confidential and the exclusive property of
 * ZIH Corp. and is subject to the terms and conditions in your end user license agreement.
 * This source code, and any other information contained herein, shall not be copied, reproduced, published, 
 * displayed or distributed, in whole or in part, in any medium, by any means, for any purpose except as
 * expressly permitted under such license agreement.
 * 
 * Copyright ZIH Corp. 2012
 * 
 * ALL RIGHTS RESERVED
 ***********************************************/

package com.infosoul.mserver.common.utils.aqi;

/**
 *
 *
 */
public class PpmConversion {

    static float[] molecularVal = { 0, // nothing
            0, // flammable
            28, // co
            0, // o2
            2, // h2
            16, // ����
            44, // ����
            44, // CO2
            48, // o3
            34, // ����
            64, // ��������
            17, // ����
            71, // ����
            44, // ��������
            (float) 36.5, // �Ȼ���
            34, // �׻���
            81, // �廯��
            27, // �軯��
            78, // ���⻯��
            20, // ������
            160, // ����
            30, // һ������
            46, // ��������
            0, // ��������
            (float) 67.5, // ��������
            32, // ����
            76, // ����̼
            38, // ����
            0, // ������
            0, 0, 4, 26, // ��Ȳ
            28, // ��ϩ
            30, // ��ȩ
            0, 0, 0, 0, 0, 0, 92, // �ױ�
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 46, // �Ҵ�
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

    };
//
//    public static void main(String args[]) {
//        float tmpval = retUgVal((byte) 2, (byte) 2, 2.44);
//
//    }

    public static float retUgVal(byte unit, byte type, float val) {
        float ret = 0;
        if (unit == 0x00) {
            // do something %LEL convert to ug/
            return val;
        } else if (unit == 0x01) {
            // do something %VOL to ug/m3

            return val;
        } else if (unit == 0x02)// ppm to ug/m3
        {
            System.out.println("type=" + type + " val=" + val);
            ret = (float) ((val * molecularVal[type] / 22.4) * 1000);
            System.out.println("ret=" + ret);
            return ret;
        } else if (unit == 0x03)// ppb to ug/m3
        {
            System.out.println("type=" + type + " val=" + val);
            ret = (float) (((val / 1000) * molecularVal[type] / 22.4) * 1000);
            System.out.println("ret=" + ret);
            return ret;
        } else
            return -1;

    }

}
