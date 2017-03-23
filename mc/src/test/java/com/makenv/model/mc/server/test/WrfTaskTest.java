package com.makenv.model.mc.server.test;

import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.server.message.task.ModelTaskConstant;
import com.makenv.model.mc.server.message.task.bean.WrfFnlCondBean;
import com.makenv.model.mc.server.message.task.bean.WrfSubBean;
import com.makenv.model.mc.server.message.task.helper.WrfTaskHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by alei on 2017/3/23.
 */
public class WrfTaskTest {
  private int reinit_cycle_days = 5, reinit_hours = 22;

  @Before
  public void before() {
  }

  private WrfFnlCondBean buildWrfFnlCondBean(String start, String end, boolean firsTime) {
    String reinit_origin_date = "19800101";
    int timeDiff = 8;
    WrfFnlCondBean wrfFnlCondBean = new WrfFnlCondBean();
    wrfFnlCondBean.setReinitCycleDays(reinit_cycle_days);
    wrfFnlCondBean.setReinitRunHours(reinit_hours);
    wrfFnlCondBean.setFirsTime(firsTime);
    wrfFnlCondBean.setWrfOutDir("");
    wrfFnlCondBean.setBaseDate(LocalTimeUtil.parse(reinit_origin_date, LocalTimeUtil.YMD_DATE_FORMAT));
    wrfFnlCondBean.setEnd(LocalTimeUtil.minusHoursDiff(timeDiff, end, LocalTimeUtil.YMD_DATE_FORMAT));
    wrfFnlCondBean.setStart(LocalTimeUtil.minusHoursDiff(timeDiff, start, LocalTimeUtil.YMD_DATE_FORMAT));
    return wrfFnlCondBean;
  }

  @Test
  public void testSeparateDateByFile() throws IOException {
    String startDate = "20170207", endDate = "20170220";
    WrfFnlCondBean wrfFnlCondBean = buildWrfFnlCondBean(startDate, endDate, false);
    wrfFnlCondBean = WrfTaskHelper.adjustStartDate(wrfFnlCondBean);
    List<WrfFnlCondBean> list = WrfTaskHelper.separateDateByFile(wrfFnlCondBean);
    List<WrfSubBean> wsbs = WrfTaskHelper.separateDateByRule(list.get(0));
    for (int i = 0; i < wsbs.size(); i++) {
      switch (i) {
        case 0:
          assertWsb(wsbs.get(i), ModelTaskConstant.RUN_TYPE_REINIT, "20170205", reinit_cycle_days, reinit_hours);
          break;
        case 1:
          assertWsb(wsbs.get(i), ModelTaskConstant.RUN_TYPE_REINIT, "20170210", reinit_cycle_days, reinit_hours);
          break;
        case 2:
          assertWsb(wsbs.get(i), ModelTaskConstant.RUN_TYPE_REINIT, "20170215", reinit_cycle_days, 0);
          break;
      }
    }
  }

  @Test
  public void testSeparateDateByRule() throws IOException {
    String startDate = "20170207", endDate = "20170220";
    WrfFnlCondBean wrfFnlCondBean = buildWrfFnlCondBean(startDate, endDate, false);
    List<WrfSubBean> wsbs = WrfTaskHelper.separateDateByRule(wrfFnlCondBean);
    for (int i = 0; i < wsbs.size(); i++) {
      switch (i) {
        case 0:
          assertWsb(wsbs.get(i), ModelTaskConstant.RUN_TYPE_RESTART, "20170206", 4, reinit_hours);
          break;
        case 1:
          assertWsb(wsbs.get(i), ModelTaskConstant.RUN_TYPE_REINIT, "20170210", reinit_cycle_days, reinit_hours);
          break;
        case 2:
          assertWsb(wsbs.get(i), ModelTaskConstant.RUN_TYPE_REINIT, "20170215", reinit_cycle_days, 0);
          break;
      }
    }
  }

  private void assertWsb(WrfSubBean wsb, int runType, String startDate, int days, int hours) {
    Assert.assertEquals(wsb.getRun_type(), runType);
    Assert.assertEquals(wsb.getStartDate(), startDate);
    Assert.assertEquals(wsb.getRun_days(), days);
    Assert.assertEquals(wsb.getRun_hours(), hours);
  }
}
