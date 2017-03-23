package com.makenv.model.mc.server.message.task;

import com.makenv.model.mc.core.config.DomainRangeDir;
import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.JacksonUtil;
import com.makenv.model.mc.core.util.LocalTimeUtil;
import com.makenv.model.mc.core.util.StringUtil;
import com.makenv.model.mc.server.message.pojo.ModelStartBean;
import com.makenv.model.mc.server.message.pojo.TaskDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by alei on 2017/3/8.
 */
public abstract class ModelTask implements IModelTask {
  private IModelTask nextTask;
  protected ModelStartBean modelStartBean;
  protected McConfigManager configManager;

  protected int startHour;
  protected int timeDiff;
  protected String metgridTemplate;
  protected String wrfTemplate;
  protected String scriptPath;
  protected String wrfBuildPath;
  protected String geogridOutputPath;
  private String modelRunFile;
  private String modelRunDir;
  protected String cmaqBuildPath;
  private String domainInfoFile;
  protected LocalDate startDate, endDate;
  protected int debugLevel;
  protected TaskDomain taskDomain;

  private Logger logger = LoggerFactory.getLogger(ModelTask.class);

  public ModelTask(ModelStartBean modelStartBean, McConfigManager configManager) throws IOException {
    this.modelStartBean = modelStartBean;
    this.configManager = configManager;
    init();
  }

  protected boolean checkParams() {
    String start = modelStartBean.getCommon().getTime().getStart();
    String end = modelStartBean.getCommon().getTime().getEnd();
    startDate = LocalTimeUtil.parse(start, LocalTimeUtil.YMD_DATE_FORMAT);
    endDate = LocalTimeUtil.parse(end, LocalTimeUtil.YMD_DATE_FORMAT);
    if (startDate.isAfter(endDate)) {
      logger.error(StringUtil.formatLog("common.time invalid", start, end));
      return false;
    }
    return true;
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

  protected TaskDomain getTaskDomain() throws IOException {
    String content = FileUtil.readLocalFile(new File(domainInfoFile));
    return JacksonUtil.jsonToObj(content, TaskDomain.class);
  }

  protected String processPath(String path) {
    return path.replace("{userid}", modelStartBean.getUserid()).
        replace("{domainid}", modelStartBean.getDomainid()).
        replace("{globaldatasets}", modelStartBean.getCommon().getDatatype()).
        replace("{scenarioid}", modelStartBean.getScenarioid()).
        replace("{missionid}", modelStartBean.getMissionid());
  }

  public void setNextTask(IModelTask nextTask) {
    this.nextTask = nextTask;
  }

  public boolean handleRequest() {
    return beforeHandle() && doHandle() && afterHandle();
  }

  private void init() throws IOException {
    startHour = configManager.getSystemConfig().getModel().getStart_hour();
    timeDiff = configManager.getSystemConfig().getModel().getTime_difference();
    DomainRangeDir domainId = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid();
    String templateDir = processPath(domainId.getCommon().getTemplate().getDirPath());
    metgridTemplate = String.format("%s%s%s", templateDir, File.separator, Constant.NAMELIST_WPS_METGRID_TEMPLATE);
    wrfTemplate = String.format("%s%s%s", templateDir, File.separator, Constant.NAMELIST_WRF_TEMPLATE);
    scriptPath = configManager.getSystemConfig().getRoot().getScript();
    taskDomain = getTaskDomain();
    wrfBuildPath = FilePathUtil.joinByDelimiter(configManager.getSystemConfig().getRoot().getWrf(), taskDomain.getWrf().getVersion());
    geogridOutputPath = processPath(domainId.getCommon().getData().getGeogrid().getDirPath());
    modelRunDir = processPath(domainId.getMissionid().getScenarioid().getRun().getBigscript().getDirPath());
    modelRunDir = FilePathUtil.joinByDelimiter(modelRunDir, modelStartBean.getDateKey());
    FileUtil.checkAndMkdir(modelRunDir);
    modelRunFile = String.format("%s%s%s", modelRunDir, File.separator, Constant.MODEL_SCRIPT_FILE);
    cmaqBuildPath = FilePathUtil.joinByDelimiter(configManager.getSystemConfig().getRoot().getCmaq(), taskDomain.getCmaq().getVersion());
    debugLevel = configManager.getSystemConfig().getModel().getDebug_level();
    domainInfoFile = configManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getDirPath();
    domainInfoFile = processPath(domainInfoFile);
    domainInfoFile = FilePathUtil.joinByDelimiter(domainInfoFile, Constant.DOMAIN_JSON);
  }

  protected abstract boolean beforeHandle();

  protected abstract boolean doHandle();

  private boolean afterHandle() {
    return nextTask == null || nextTask.handleRequest();
  }

}
