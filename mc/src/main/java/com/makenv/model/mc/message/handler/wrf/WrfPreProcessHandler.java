package com.makenv.model.mc.message.handler.wrf;

import com.makenv.model.mc.core.config.McConfigManager;
import com.makenv.model.mc.core.constant.Constant;
import com.makenv.model.mc.core.util.FilePathUtil;
import com.makenv.model.mc.core.util.FileUtil;
import com.makenv.model.mc.core.util.VelocityUtil;
import com.makenv.model.mc.message.handler.AbstractHandlerConfig;
import com.makenv.model.mc.message.handler.Handler;
import com.makenv.model.mc.message.handler.HandlerChain;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgy on 2017/2/22.
 * 所有的脚本是预处理脚本 c shell 脚本
 */
public class WrfPreProcessHandler extends AbstractHandlerConfig implements Handler {

    private WrfParams wrfPreParams;

    private String wrfPathdateRunPath;

    public WrfPreProcessHandler(McConfigManager mcConfigManager) {

        this.mcConfigManager = mcConfigManager;

        init();
    }

    private void init() {

        wrfPathdateRunPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getRun().getWrfpre_pathdate();

        wrfPathdateRunPath = replaceRegex(wrfPathdateRunPath);

    }

    private boolean generate_renv_wrf_pre_csh() {

        String geogridDataPath = mcConfigManager.getSystemConfig().getWorkspace().getUserid().getDomainid().getCommon().getData().getGeogrid().getDirPath();

        String geoDirPath = mcConfigManager.getSystemConfig().getWorkspace().getShare().getInput().getGeog().getDirPath();

        geogridDataPath = replaceRegex(geogridDataPath);

        WrfPreBean preBean = new WrfPreBean();

        preBean.setStart_hour(mcConfigManager.getSystemConfig().getModel().getStart_hour());

        preBean.setNamelist_wps_geogrid_template(FilePathUtil.joinByDelimiter(wrfPathdateRunPath, Constant.NAMELIST_WPS_GEOGRID_TEMPLATE));

        preBean.setNamelist_wps_metgrid_template(FilePathUtil.joinByDelimiter(wrfPathdateRunPath, Constant.NAMELIST_WPS_METGRID_TEMPLATE));

        preBean.setNamelist_wrf_template(FilePathUtil.joinByDelimiter(wrfPathdateRunPath, Constant.NAMELIST_WRF_TEMPLATE));

        preBean.setGeog_data(geoDirPath);

        preBean.setGeogrid_data(geogridDataPath);

        BeanUtils.copyProperties(wrfPreParams,preBean);

        String fileNamePath =  "";//mcConfigManager.getSystemConfig().getTemplate().getRenv_wrfpre_csh();

        mcConfigManager.getSystemConfig().
                getWorkspace().getUserid().
                getDomainid().getCommon().getRun();

        Map map = new HashMap();

        map.put("wrfPre",preBean);

        String content = VelocityUtil.buildTemplate(fileNamePath,map);

        return false;//FileUtil.save(FilePathUtil.joinByDelimiter(wrfPathdateRunPath,Constant.WRF_PRE_CSH),content);
    }

    private boolean link_wrf_pre_csh() {

        String exsitsPath = "";

        //String exsitsPath = mcConfigManager.getSystemConfig().getCsh().getWrfpre_csh();

        return false;//FileUtil.symbolicLink(exsitsPath,FilePathUtil.joinByDelimiter(wrfPathdateRunPath,Constant.WRF_PRE_CSH));

    }

    private void runShell() {


    }

    private String replaceRegex(String path) {

        return path.replaceAll("\\{userid\\}",wrfPreParams.getUserid()).replaceAll("\\{domainid\\}",wrfPreParams.getDomainid())

                .replaceAll("\\{globaldatasets\\}",wrfPreParams.getGlobaldatasets());
    }

    @Override
    public void doHandler(HandlerChain handlerChain) {

        try {

            //生成wrf预处理环境变量 c shell 脚本
            generate_renv_wrf_pre_csh();

            //链接wrf预处理脚本
            link_wrf_pre_csh();

            //执行预处理脚本
            runShell();



        } catch (Exception e) {

            e.printStackTrace();
        }

        handlerChain.doHandler(handlerChain);

    }
    
}
