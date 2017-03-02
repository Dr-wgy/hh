## 文件存储目录结构
├── share
│   ├── input
│   │   ├── fnl
│   │   │   └── 20170106
│   │   │       ├── fnl_20170106_00_00.grib2
│   │   │       ├── fnl_20170106_06_00.grib2
│   │   │       ├── fnl_20170106_12_00.grib2
│   │   │       └── fnl_20170106_18_00.grib2
│   │   ├── geog
│   │   ├── gfs
│   │   │   └── 2016070818
│   │   │       └── gfs.t18z.pgrb2.1p00.f000
│   │   ├── sst
│   │   ├── ungrib_fnl
│   │   │   └── 20170106
│   │   └── ungrib_gfs
│   │       └── 2016070818
│   └── run
│       ├── ungrib_fnl
│       └── ungrib_gfs
└── $userid
    └── $domainid
        ├── common
        │   ├── data
        │   │   ├── geogrid
        │   │   ├── $globaldatasets
        │   │   │   ├── mcip
        │   │   │   │   └── $pathdata
        │   │   │   ├── megan
        │   │   │   │   └── $pathdata
        │   │   │   ├── metgrid
        │   │   │   ├── real
        │   │   │   │   └── $pathdata
        │   │   │   │       ├── wrfinputd01
        │   │   │   │       ├── wrfinputd02
        │   │   │   │       └── wrfinputd03
        │   │   │   ├── wrf
        │   │   │   │   └── $pathdata
        │   │   │   └── wrfdp
        │   │   │       └── $pathdata
        │   │   │           └── $domain
        │   │   │               └── a
        │   │   │                   ├── daily
        │   │   │                   └── hourly
        │   │   ├── gridesc
        │   │   │   ├── GRIDDESC_04
        │   │   │   ├── GRIDDESC_12
        │   │   │   └── GRIDDESC_36
        │   │   └── ocean
        │   │       ├── oceanfile_China04.nc
        │   │       ├── oceanfile_China12.nc
        │   │       └── oceanfile_China36.nc
        │   └── run
        │       ├── dp
        │       │   └── $pathdata
        │       ├── geogrid
        │       ├── megan
        │       │   └── $pathdata
        │       ├── ocean
        │       │   └── $pathdata
        │       ├── wrf
        │       └── wrfpre
        │           └── $pathdata
        └── $missionid
            └── $scenarioid
                ├── data
                │   ├── bcon
                │   ├── cctm
                │   ├── dp
                │   │   └── $domain
                │   │       ├── conc
                │   │       │   ├── a
                │   │       │   │   ├── avg
                │   │       │   │   │   └── $species
                │   │       │   │   ├── daily
                │   │       │   │   └── hourly
                │   │       │   └── b
                │   │       └── emis
                │   │           └── a
                │   │               └── daily
                │   ├── icon
                │   └── video
                │       └── $species.mp4
                └── run
                    ├── cctm
                    ├── cctmpre
                    ├── dp
                    └── video

## 定时任务要做的事
1.分类型逐日检查文件，数据没问题则拷贝，有问题终止
2.ungrib
fnl: /path/ungrib.csh /path/renv-ungrib.csh fnl
gfs: /path/ungrib.csh /path/renv-ungrib.csh gfs

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

## 打包
mvn clean compile package