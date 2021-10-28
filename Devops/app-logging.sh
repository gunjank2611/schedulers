#!/bin/bash
now=$(date +%m-%d-%Y)
CURRENT_DATE=`date +%d-%m-%Y_%H.%M.%S`
PURGE_DATE=$(date +%m-%d-%Y -d "$DATE + $3 day")
if [[ "$CURRENT_DATE" == "$PURGE_DATE" ]]
then
  cd /opt/scripts
  rm -rf *
fi  
az login --use-device-code
az account set --subscription 90a5cf8f-aa05-450d-9850-5d64c0f061d9
az aks get-credentials --overwrite --resource-group rgaz-cin-tcp-qa --name aks-cin-thermax-qa
kubectl config set-context --current --namespace=thermax
for app_name in $(cat /home/appnames.txt)
do
  success_status=$(kubectl get pod --field-selector status.phase=Running --no-headers|grep "$app_name"|head -1)
  echo $success_status
  if [[ $success_status == *"No resources"* ]]
  then 
    mkdir -p /opt/scripts/$app_name/failure/$now
    kubectl logs --selector=app=$app_name -c $app_name |grep -i error|head -10>/opt/scripts/$app_name/failure/$now/error.log
  else
    mkdir -p /opt/scripts/$app_name/success/$now
    kubectl logs --selector=app=$app_name -c $app_name |head -10>/opt/scripts/$app_name/success/$now/success.log
  fi
done
