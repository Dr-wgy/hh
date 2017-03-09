package com.makenv.model.mc.server.message.task;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;

import java.io.File;

/**
 * Created by alei on 2017/3/8.
 */
public abstract class ModelTask implements IModelTask {
  private IModelTask nextTask;
  protected ModelStartBean modelStartBean;
  protected McConfigManager configManager;
  protected final static int RUN_TYPE_INIT = 0;
  protected final static int RUN_TYPE_RESTART = 1;
  protected final static int RUN_TYPE_REINIT = 2;

  protected int startHour;
  protected int localStartHour;
  protected String metgridTemplate;
  protected String wrfTemplate;
  protected String scriptPath;
  protected String wrfBuildPath;
  protected String geogridOutputPath;
  private String modelRunFile;
  private String modelRunDir;

  public ModelTask(ModelStartBean modelStartBean, McConfigManager configManager) {
    this.modelStartBean = modelStartBean;
    this.configManager = configManager;
    init();
  }

  public String getModelRunFilePath() {
    return modelRunFile;
  }

  public String getModelRunDir() {
    return modelRunDir;
  }

  protected File getModelRunFile() {
    return new File(modelRunFile);
  }

  protected String processPath(String path) {
    return path.replace("{userid}", modelStartBean.getUserid()).
        replace("{domainid}", modelStartBean.getDomainid()).
        replace("{globaldatasets}", modelStartBean.getCommon().getDatatype());
  }

  public void setNextTask(IModelTask nextTask) {
    this.nextTask = nextTask;
  }

  public boolean handleRequest() {
    return beforeHandle() && doHandle() && afterHandle();
  }

  private void init() {
    startHour = configManager.getSystemConfig().getModel().getStart_hour();
    localStartHour = configManager.getSystemConfig().getModel().getLocal_start_hour();
    String templateDir = processPath(configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getTemplate().getDirPath());
    metgridTemplate = String.format("%s%s%s", templateDir, File.separator, Constant.NAMELIST_WPS_METGRID_TEMPLATE);
    wrfTemplate = String.format("%s%s%s", templateDir, File.separator, Constant.NAMELIST_WPS_METGRID_TEMPLATE);
    scriptPath = configManager.getSystemConfig().getRoot().getScript();
    wrfBuildPath = configManager.getSystemConfig().getRoot().getWrf();
    geogridOutputPath = processPath(configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGeogrid().getDirPath());
     modelRunDir = processPath(configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getModelRunPath());
    modelRunDir = String.format("%s%s%s", modelRunDir, File.separator, modelStartBean.getScenarioid());
    FileUtil.checkAndMkdir(modelRunDir);
    modelRunFile = String.format("%s%s%s", modelRunDir, File.separator, Constant.MODEL_SCRIPT_FILE);
  }

  protected abstract boolean beforeHandle();

  protected abstract boolean doHandle();

  private boolean afterHandle() {
    return nextTask == null || nextTask.handleRequest();
  }

}
