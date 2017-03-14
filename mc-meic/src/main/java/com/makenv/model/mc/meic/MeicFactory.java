package com.makenv.model.mc.meic;

import com.makenv.model.mc.meic.config.MeicCacheParams;
import com.makenv.model.mc.core.bean.MeicParams;
import com.makenv.model.mc.meic.config.MeicServerParams;
import com.makenv.model.mc.meic.constants.MeicType;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

/**
 * Created by wgy on 2017/3/14.
 */
public class MeicFactory {


    public static IMeicTask getMeciTask (String meicType, MeicParams meicParams) {

        MeicType meicTypeEnum = MeicType.getMeiType(meicType);

        if(Objects.nonNull(meicTypeEnum)) {

            switch(meicTypeEnum) {

                case MEICTYPE_SERVER:

                    MeicServerParams meicServerParams = new MeicServerParams();

                    BeanUtils.copyProperties(meicParams,meicServerParams);

                    return new MeicServerTask(meicServerParams);

                case MEICTYPE_CACHE:

                    MeicCacheParams meicCacheParams = new MeicCacheParams();

                    BeanUtils.copyProperties(meicParams, meicCacheParams);

                    return new MeicCacheTask(meicCacheParams);
            }
        }

        return null;
    }


}
