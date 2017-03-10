package com.makenv.model.mc.core.constant;

/**
 * Created by wgy on 2017/2/28.
 */
public class Constant {

  //  public static final int START_HOUR = 18;
  public static final String SYS_RENV_CSH = "sys_renv.csh";
  public static final String[] FILE_HOURS = {"00", "06", "12", "18"};
  public static final int MODEL_DEBUG_LEVEL = 1;
  public static final String UNGRIB_RENV_FILE = "renv-ungrib";
  public static final String UNGRIB_SCRIPT_FILE = "invoke-ungrib";
  public static final String UNGRIB_LOG_FILE = "log-ungrib";
  public static final String UNGRIB_FILE_PREFIX = "FILE";
  public static final String COORDNAME = "China_d%02d";

  public static final String NAMELIST_IPXWRF_TEMPLATE = "namelist.ipxwrf.template";
  public static final String NAMELIST_OA_TEMPLATE = "namelist.oa.template";
  public static final String NAMELIST_WPS_GEOGRID_TEMPLATE = "namelist.wps.geogrid.template";
  public static final String NAMELIST_WRF_TEMPLATE = "namelist.wrf.template";
  public static final String NAMELIST_WPS_METGRID_TEMPLATE = "namelist.wps.metgrid.template";
  public static final String NAMELIST_WPS_UNGRIB_TEMPLATE = "namelist.wps.ungrib.template";

  public static final String GEOGRID_SCRIPT_FILE = "invoke-geogrid";

  public static final String DOMAIN_RENV_FILE = "renv-domain.csh";

  public static final String WRF_RENV_FILE = "renv-wrf";
  public static final String MODEL_SCRIPT_FILE = "invoke-model";
  public static final String MODEL_LOG_FILE_INFO = "log-model-info";
  public static final String MODEL_LOG_FILE_ERROR = "log-model-error";

  public static final String CSH_HEADER = "#!/bin/csh\n";

  public static final String GLOBAL_TYPE_FNL = "fnl";
  public static final String GLOBAL_TYPE_GFS = "gfs";

  public static final String DOMAIN_JSON = "domain.json";


}
