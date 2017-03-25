#!/usr/bin/env bash
# usage:
# 20 11 * * * /home/modelcloud/modelcloud/mc/ungrib.sh > /home/modelcloud/modelcloud/mc/mc-crontab-$(date +\%F).log 2>&1
echo "PATH-FIRST="$PATH
export PATH="/usr/local/bin:$PATH"
echo "PATH-SECOND="$PATH
today=`date +%Y%m%d`
tagPath=/work/modelcloud/share/run/ungrib/tag
jarPath=/home/modelcloud/modelcloud/meic
cd $jarPath
if [ -f $tagPath ]; then
	lastDate=`cat $tagPath`
	if [ $today > $lastDate ];	then
		while [ $lastDate -lt $today ]
		do
			echo 'do ungrib for '${lastDate}
			java -jar mc-cli.jar -t ungrib -d ${lastDate}
			lastDate=`date -d "${lastDate} +1 day"`
		done
	else
		echo 'do not need ungrib, the last date is'${lastDate}', today is '${today}
	fi
else
	echo 'the first time to ungrib'
	java -jar mc-cli.jar -t ungrib -d $today
fi