package com.makenv.model.mc.cli.is.validate;

import com.makenv.model.mc.cli.is.enumeration.Constants;
import com.makenv.model.mc.cli.is.enumeration.RuleEnum;
import com.makenv.model.mc.cli.is.excel.RuleBean;
import com.makenv.model.mc.cli.is.util.ExcelUtil;
import com.makenv.model.mc.core.util.FileUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.makenv.model.mc.cli.is.enumeration.Constants.UNKOWN_ERROR;

/**
 * Created by wgy on 2017/3/22.
 */
public class TaskValidator {

    private Logger logger = LoggerFactory.getLogger(TaskValidator.class);

    private List<RuleBean> list = null;

    private Map<String,List<RuleBean>> map = null;

    private String filePathName;

    public TaskValidator(Sheet sheet,String file) {

        list = ExcelUtil.excelToBean(RuleBean.class,sheet);

        map = list.stream().collect(Collectors.groupingBy(RuleBean::getFlag));

        filePathName = file;

    }

    public void validate() {

        //失败检测
        List<RuleBean> failedList  = map.get(Constants.FAIL_FLAG);

        if( Objects.nonNull(failedList) && failedList.size() != 0) {

            checkRule(failedList,false);
        }

        //成功检测
        List <RuleBean> successList = map.get(Constants.SUCCESS_FLAG);

        if(Objects.nonNull(successList) && successList.size() != 0 ) {

            checkRule(successList,true);

        }

        //假设什么也没有匹配到 吐出unkown error

        System.out.print(Constants.UNKOWN_ERROR);

        System.exit(0);
    }

    /**
     *
     * @param list 规则表
     * @param flag true 是成功 否失败
     */
    private void checkRule(List <RuleBean> list,boolean flag) {

        logger.info(filePathName);

        logger.info("check log start");

        String allContent = "";

        try {

            allContent = FileUtil.readLocalFile(new File(filePathName));

            for(RuleBean ruleBean:list) {

                int lastLine = ruleBean.getLastline();

                String source = null;

                if(lastLine != 0) {

                    source = FileUtil.readNLastLineToStr(new File(filePathName),lastLine);
                }
                else {

                    source = allContent;

                }

                String ruleType = ruleBean.getType();

                RuleEnum ruleEnum = RuleEnum.getRule(ruleType);

                String target = ruleBean.getContent();

                if(ruleEnum.getValidate().matches(source,target) && !flag) {

                    System.out.print(ruleBean.getDesc());

                    logger.info(ruleBean.getDesc());

                    logger.info("check log end");

                    System.exit(0);
                }
                else if (ruleEnum.getValidate().matches(source,target) && flag){

                    System.out.print(Constants.SUCCESS_FLAG);

                    System.exit(0);
                }
            }

            logger.info("check log end");


        } catch (IOException e) {

            logger.error("the file is not exsits",e);

            System.exit(1);
        }
    }
}
