package com.infosoul.mserver.repository.mybatis.report;

import java.util.List;

import com.infosoul.mserver.common.persistence.annotation.MyBatisRepository;
import com.infosoul.mserver.vo.report.AxisVo;

@MyBatisRepository
public interface IItemReportDao {
    // 新增商品数:日报、周报、月报
    List<AxisVo> getItemNewAmountByDaily(String beginDate, String endDate);
    List<AxisVo> getItemNewAmountByWeekly(String beginDate, String endDate);
    List<AxisVo> getItemNewAmountByMonthly(String beginDate, String endDate);

    // 上架商品数/下架商品数
    List<AxisVo> getItemUpDownAmountByDaily(String beginDate, String endDate, String delFlag);
    List<AxisVo> getItemUpDownAmountByWeekly(String beginDate, String endDate, String delFlag);
    List<AxisVo> getItemUpDownAmountByMonthly(String beginDate, String endDate, String delFlag);

    // 下架商品数
    //List<Integer> getItemDownAmountByDaily(String beginDate, String endDate);
    //List<Integer> getItemDownAmountByWeekly(String beginDate, String endDate);
    //List<Integer> getItemDownAmountByMonthly(String beginDate, String endDate);

    // 商品库数
    // List<AxisVo> getItemAllAmountByDaily(String beginDate, String endDate);
}
