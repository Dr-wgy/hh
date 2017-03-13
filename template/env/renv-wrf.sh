#模板文件绝对路径/path/namelist.wps.metgrid.template
setenv namelist_wps_metgrid_template /public/home/tsinghua/lixin/my_code/git/model-driver/template/namelist.wps.metgrid.template
#模板文件绝对路径/path/namelist.wrf.template
setenv namelist_wrf_template /public/home/tsinghua/lixin/my_code/git/model-driver/template/namelist.wrf.template
#开始日期
setenv start_date 20170302
#运行天数
setenv run_days 2
#运行小时数
setenv run_hours 0
#开始时间，默认18
setenv start_hour 18
#全球数据集: fnl or gfs?
setenv global fnl
#ungrib生成的文件名称前缀FILE
setenv ungrib_file FILE
#模式驱动脚本目录
setenv scripts_path /public/home/tsinghua/lixin/my_code/git/model-driver/
#WRF安装目录
setenv wrf_build_path /public/home/tsinghua/chengjing/WRF3.7/
#GEOGRID文件输出目录
setenv geogrid_output_path /public/home/tsinghua/lixin/data/test/geogrid/
#UNGRIB文件输出目录
setenv ungrib_output_path /public/home/tsinghua/lixin/data/test/ungrib/fnl/
#METGRID文件输出目录
setenv metgrid_output_path /public/home/tsinghua/lixin/data/test/metgrid/
#WRF文件输出目录
setenv wrf_output_path /public/home/tsinghua/lixin/data/test/wrf/
#模拟类型: 0-initial, 1-restart, 2-reinitial
setenv run_type 0
#是否为debug模式，0非，>0为debug
setenv debug 1
