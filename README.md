## 定时任务要做的事
1.分类型逐日检查文件，数据没问题则拷贝，有问题终止
2.ungrib
fnl: /path/ungrib.csh /path/renv-ungrib-fnl.csh /path/fnl/fnl_ /path/output/
gfs: /path/ungrib.csh /path/renv-ungrib-gfs.csh /path/gfs/gfs. /path/output/

## wrf pre
1./path/wrfpre.csh /path/renv-wrfpre.csh

## wrf
1. /path/wrf.csh /path/


------------------------------------
如果是自定义基准情景
1.生成griddesc，ocean file
2.生成/public/home/tsinghua/alei/mc/$userid/$missionid/common/run/wrfpre/renv-wrfpre.csh
3.link wrfpre.csh to /public/home/tsinghua/alei/mc/$userid/$missionid/common/run/wrfpre/
4.cd /public/home/tsinghua/alei/mc/$userid/$missionid/common/run/wrfpre;./wrfpre.csh /path/renv-wrfpre.csh
5.生成/public/home/tsinghua/alei/mc/$userid/$missionid/common/run/wrf/renv-wrf.csh
6.link wrf.csh to /public/home/tsinghua/alei/mc/$userid/$missionid/common/run/wrf
7.cd /public/home/tsinghua/alei/mc/$userid/$missionid/common/run/wrf;./wrf.csh /path/renv-wrf.csh

如果是预报