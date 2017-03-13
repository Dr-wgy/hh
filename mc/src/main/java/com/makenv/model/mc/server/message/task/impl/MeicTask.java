package com.makenv.model.mc.server.message.task.impl;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.task.ModelTask;

import java.io.File;

/**
 * Created by alei on 2017/3/8.
 */
public class MeicTask extends ModelTask {
  private String runDir, meicConfPath, jarConfPath, renvFilePath, jarPath;

  public MeicTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    super(modelStartBean, configManager);
  }

  protected boolean checkParams() {
    return super.checkParams();
  }

  private boolean processDirectory() {
    String base = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getMissionid().getScenarioid().getRun().getEmis().getDirPath();
    runDir = processPath(base);
    runDir = String.format("%s%s%s", runDir, File.separator, System.currentTimeMillis());
    renvFilePath = FilePathUtil.joinByDelimiter(runDir, Constant.MODEL_RENV_FILE);
    meicConfPath = FilePathUtil.joinByDelimiter(runDir, Constant.MEIC_CONF);
//    jarConfPath = FilePathUtil.joinByDelimiter(runDir, Constant.);
    return true;
  }

  private void buildCacheConf() {

  }

  private void buildServerConf() {

  }

  private void buildRenv() {

  }

  @Override
  protected boolean beforeHandle() {
    return false;
  }

  @Override
  protected boolean doHandle() {
    return false;
  }
}
