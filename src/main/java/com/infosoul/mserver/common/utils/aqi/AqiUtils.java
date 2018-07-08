package com.infosoul.mserver.common.utils.aqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AqiUtils {

	private static void testDaily() {
		System.out.println("==========DailyCalc==========");

		// 分指数
		HashMap<String, Integer> indexMap = new HashMap<String, Integer>();
		DailyCalc dailyCalc = new DailyCalc();
		int iAqi_so2 = dailyCalc.getIAqi("so2", 7);
		indexMap.put("so2", iAqi_so2);
		int iAqi_no2 = dailyCalc.getIAqi("no2", 45);
		indexMap.put("no2", iAqi_no2);
		int iAqi_pm10 = dailyCalc.getIAqi("pm10", 35);
		indexMap.put("pm10", iAqi_pm10);
		int iAqi_co = dailyCalc.getIAqi("co", 1200);
		indexMap.put("co", iAqi_co);
		int iAqi_o3 = dailyCalc.getIAqi("o3", 0);
		indexMap.put("o3", iAqi_o3);
		int iAqi_pm25 = dailyCalc.getIAqi("pm25", 19);
		indexMap.put("pm25", iAqi_pm25);
		System.out.println(indexMap);

		AqiCalc aqiCalc = new AqiCalc(indexMap);
		// 首要污染物
		ArrayList<String> primaryPollutant = aqiCalc.getPrimaryPollutant();
		System.out.println(primaryPollutant);
		// aqi
		int aqi = indexMap.get(primaryPollutant.get(0));
		System.out.println(aqi);
		// 空气质量指数级别
		String level = aqiCalc.getLevel(aqi);
		System.out.println(level);
		// 空气质量指数类别
		String cls = aqiCalc.getCls(aqi);
		System.out.println(cls);
		// 空气质量指数类别表示颜色
		String color = aqiCalc.getColor(aqi);
		System.out.println(color);
		// 全部AQI数据
		System.out.println(aqiCalc.getAllData());
	}

	public static Map<String, Object> getRealTime(float so2, float no2, float pm10, float co, float o3, float pm25) {
		System.out.println("==========RealTimeCalc==========");

		// 分指数
		HashMap<String, Integer> indexMap = new HashMap<String, Integer>();
		RealTimeCalc realTimeCalc = new RealTimeCalc();
		int iAqi_so2 = realTimeCalc.getIAqi("so2", so2);//7
		indexMap.put("so2", iAqi_so2);
		int iAqi_no2 = realTimeCalc.getIAqi("no2", no2);//45
		indexMap.put("no2", iAqi_no2);
		int iAqi_pm10 = realTimeCalc.getIAqi("pm10",pm10);//35
		indexMap.put("pm10", iAqi_pm10);
		int iAqi_co = realTimeCalc.getIAqi("co", co);//1200
		indexMap.put("co", iAqi_co);
		int iAqi_o3 = realTimeCalc.getIAqi("o3", o3);//0
		indexMap.put("o3", iAqi_o3);
		int iAqi_pm25 = realTimeCalc.getIAqi("pm25", pm25);//19
		indexMap.put("pm25", iAqi_pm25);
		System.out.println(indexMap);

		AqiCalc aqiCalc = new AqiCalc(indexMap);
		// 首要污染物
		ArrayList<String> primaryPollutant = aqiCalc.getPrimaryPollutant();
		System.out.println(primaryPollutant);
		// aqi
		int aqi = indexMap.get(primaryPollutant.get(0));
		System.out.println(aqi);
		// 空气质量指数级别
		String level = aqiCalc.getLevel(aqi);
		System.out.println(level);
		// 空气质量指数类别
		String cls = aqiCalc.getCls(aqi);
		System.out.println(cls);
		// 空气质量指数类别表示颜色
		String color = aqiCalc.getColor(aqi);
		System.out.println(color);
		// 全部AQI数据
		return aqiCalc.getAllData();
	}

	public static void main(String[] args) {


		byte[] datainfo={(byte)0x01,(byte)0x03,(byte)0x18,(byte)0x00,(byte)0x52,(byte)0x00,(byte)0x24,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x50,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x10,(byte)0x00,(byte)0x18,(byte)0x00,(byte)0x1A,(byte)0x01,(byte)0x08,(byte)0x02,(byte)0x62,(byte)0x2C,(byte)0x09};

		float coval,so2val,no2val,o3val,pm25val,pm10val,temperature,humidity;
		float covalug,so2valug,no2valug,o3valug;

		coval=(float)(((datainfo[3]<<8)&0xff00)|(datainfo[4]& 0xff));
		coval=coval/100;
		System.out.println("coval="+coval);

		so2val=(float)(((datainfo[5]<<8)&0xff00)|(datainfo[6]& 0xff));
		so2val=so2val/1000;
		System.out.println("so2val="+so2val);
		no2val=(float)(((datainfo[7]<<8)&0xff00)|(datainfo[8]& 0xff));
		no2val=no2val/1000;
		System.out.println("no2val="+no2val);
		o3val=(float)(((datainfo[9]<<8)&0xff00)|(datainfo[10]& 0xff));
		o3val=o3val/1000;
		System.out.println("o3val="+o3val);
		pm25val=(float)(((datainfo[19]<<8)&0xff00)|(datainfo[20]& 0xff));

		pm10val=(float)(((datainfo[21]<<8)&0xff00)|(datainfo[22]& 0xff));



		temperature=(float)( (((datainfo[23]<<8)&0xff00)|(datainfo[24]& 0xff))  *0.1);
		if((datainfo[23] & 0x80) !=0)
		{
			temperature=0-temperature;
		}



		humidity=(float) ((((datainfo[25]<<8)&0xff00)|(datainfo[26]& 0xff))*0.1);


		System.out.println("temp+"+temperature);
		System.out.println("humidity+"+humidity);
		covalug= PpmConversion.retUgVal((byte)2, (byte)2, coval);

		so2valug=PpmConversion.retUgVal((byte)2, (byte)0xa, so2val);

		no2valug=PpmConversion.retUgVal((byte)2, (byte)0x16, no2val);
		o3valug=PpmConversion.retUgVal((byte)2, (byte)0x8, o3val);

		System.out.println("col ppm:"+coval+"  colug :"+covalug);
		System.out.println("so2 ppm:"+so2val+"  so2lug :"+so2valug);
		System.out.println("no2 ppm:"+no2val+"  no2valug :"+no2valug);
		System.out.println("o3 ppm:"+o3val+"  o3valug :"+o3valug);
		System.out.println("pm25val:"+pm25val);
		System.out.println("pm10val:"+pm10val);

	//	testDaily();
		AqiUtils.getRealTime(so2valug,no2valug,pm10val,covalug,o3valug,pm25val);
	}

}
