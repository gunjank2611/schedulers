#!/bin/bash
CURRENT_DATE=`date +%d-%m-%Y_%H.%M.%S`
find /opt/scripts -type d -mtime +3 | xargs rm -rf
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
    mkdir -p /opt/scripts/$app_name/failure/$CURRENT_DATE
    kubectl logs --selector=app=$app_name -c $app_name -n thermax |grep -i -e error -e warning>/opt/scripts/$app_name/failure/$CURRENT_DATE/error.log
  else
    mkdir -p /opt/scripts/$app_name/success/$CURRENT_DATE
    kubectl logs --selector=app=$app_name -c $app_name -n thermax>/opt/scripts/$app_name/success/$CURRENT_DATE/success.log
  fi
done
