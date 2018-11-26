#!/usr/bin/bash

TASK=`echo $1 | awk '{print toupper($0)}'`
MSG=$2
LOG=$3
DATE=`date '+%Y-%m-%d %H:%M:%S'`

if [ "$TASK" == "RESTART" ]
then 
	echo -e "[$DATE] $TASK\t$MSG" > $LOG
else
	echo -e "[$DATE] $TASK\t$MSG" >> $LOG
fi
