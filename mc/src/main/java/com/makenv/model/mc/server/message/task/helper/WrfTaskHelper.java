package com.makenv.model.mc.server.message.task.helper;

import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.server.message.task.ModelTaskConstant;
import com.makenv.model.mc.server.message.task.bean.WrfFnlCondBean;
import com.makenv.model.mc.server.message.task.bean.WrfSubBean;
import org.assertj.core.util.Arrays;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alei on 2017/3/23.
 */
public class WrfTaskHelper {

  /**
   * 自动check spinup
   */
  public static WrfFnlCondBean adjustStartDate(WrfFnlCondBean wrfFnlCondBean) {
    WrfFnlCondBean wfcb = wrfFnlCondBean.clone();
    LocalDate ld = wrfFnlCondBean.getStart();
    while (!LocalTimeUtil.needReInitial(wrfFnlCondBean.getBaseDate(), ld, wrfFnlCondBean.getReinitCycleDays())) {
      ld = ld.plusDays(-1);
    }
    wfcb.setStart(ld);
    return wfcb;
  }

  private static boolean isWrfExist(String fileDir) {
    File dir = new File(fileDir);
    if (dir.exists()) {
      String[] files = dir.list();
      return !Arrays.isNullOrEmpty(files) && files.length >= 24;
    }
    return false;
  }

  public static List<WrfFnlCondBean> separateDateByFile(WrfFnlCondBean wrfFnlCondBean) {
    List<WrfFnlCondBean> wfcbs = new LinkedList<>();
    LocalDate start = wrfFnlCondBean.getStart();
    int i = 0;
    WrfFnlCondBean wfcb = null;
    while (!start.isAfter(wrfFnlCondBean.getEnd())) {
      String datePath = LocalTimeUtil.format(start, LocalTimeUtil.YMD_DATE_FORMAT);
      String wrfPath = FilePathUtil.joinByDelimiter(wrfFnlCondBean.getWrfOutDir(), datePath);
      if (isWrfExist(wrfPath)) {
        if (i > 0) {
          assert wfcb != null;
          wfcb.setEnd(start);
          wfcbs.add(wfcb);
        }
        i = 0;
      } else if (i++ == 0) {
        wfcb = wrfFnlCondBean.clone();
        wfcb.setStart(start);
      }
      start = start.plusDays(1);
    }
    if (i > 0) {
      wfcb.setEnd(wrfFnlCondBean.getEnd());
      wfcbs.add(wfcb);
    }
    return wfcbs;
  }

  public static List<WrfSubBean> separateDateByRule(WrfFnlCondBean wfcb) throws IOException {
    LocalDate _current = wfcb.getStart(), lastDate = wfcb.getStart();
    LocalDate baseDate = wfcb.getBaseDate();
    int i = 0, j = 0;
    WrfSubBean bean;
    List<WrfSubBean> separateWrfBeans = new LinkedList<>();
    while (!_current.isAfter(wfcb.getEnd())) {
      if (LocalTimeUtil.needReInitial(baseDate, _current, wfcb.getReinitCycleDays())) {
        long runDays = LocalTimeUtil.between(_current, lastDate);
        bean = new WrfSubBean();
        bean.setStart_date(lastDate);
        bean.setRun_days((int) runDays);
        bean.setRun_hours(wfcb.getReinitRunHours());
        int runType = (j == 0 ? (wfcb.isFirsTime() ? ModelTaskConstant.RUN_TYPE_INIT : ModelTaskConstant.RUN_TYPE_RESTART) : ModelTaskConstant.RUN_TYPE_REINIT);
        bean.setRun_type(runType);
        lastDate = _current;
        separateWrfBeans.add(bean);
        j++;
      }
      i++;
      _current = _current.plusDays(1);
    }
    _current = _current.plusDays(-1);
    bean = new WrfSubBean();
    bean.setStart_date(lastDate);
    bean.setRun_hours(0);
    int runType;
    long runDays = LocalTimeUtil.between(_current, lastDate) + 1;
    if (j == 0) {
      runType = wfcb.isFirsTime() ? ModelTaskConstant.RUN_TYPE_INIT : ModelTaskConstant.RUN_TYPE_RESTART;
    } else {
      runType = ModelTaskConstant.RUN_TYPE_REINIT;
    }
    bean.setRun_type(runType);
    bean.setRun_days((int) runDays);
    separateWrfBeans.add(bean);
    return separateWrfBeans;
  }

//  private static boolean isReInitial(LocalDate compDate) {
//    LocalDate baseDate = configManager.getSystemConfig().getModel().getReinitOriginDate();
//    int reinitialDays = configManager.getSystemConfig().getModel().getReinit_cycle_days();
//    return LocalTimeUtil.needReInitial(baseDate, compDate, reinitialDays);
//  }
}
