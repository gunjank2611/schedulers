#!/bin/bash
CURRENT_DATE=`date +%d-%m-%Y_%H.%M.%S`
az login --use-device-code
az account set --subscription 90a5cf8f-aa05-450d-9850-5d64c0f061d9
az aks get-credentials --overwrite --resource-group rgaz-cin-tcp-qa --name aks-cin-thermax-qa
kubectl config set-context --current --namespace=thermax
sed 's/\r$//' /home/appnames.txt>/home/app.txt
for app_name in $(cat /home/app.txt)
do
  success_status=$(kubectl get pod --field-selector status.phase=Running --no-headers|grep "$appname"|head -1)
  if [[ $success_status == *"No resources"* ]]
  then 
    kubectl logs --selector=app=$app_name -c $app_name -n thermax |grep -i -e error -e warning>/opt/scripts/failure/$app_name/$CURRENT_DATE/error.log
    find /opt/scripts/failure/$app_name -type d -mtime +1 | xargs rm -rf
  else
    kubectl logs --selector=app=$app_name -c $app_name -n thermax>/opt/scripts/success/$app_name/$CURRENT_DATE/success.log
    find /opt/scripts/success/$app_name -type d -mtime +1 | xargs rm -rf
  fi
done