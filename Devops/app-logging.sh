#!/bin/bash
now=$(date +%m-%d-%Y)
TIME=`date +%d-%m-%Y_%H.%M.%S`
for i in {0..3}
do
  NEXT_DATE=$(date +%m-%d-%Y -d "$DATE + $i day")
  if [[ $i=3 ]]
  then
    cd /opt/scripts
    rm -rf *
  fi  
done
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
    mkdir -p /opt/scripts/$app_name/failure/$TIME
    kubectl logs --selector=app=$app_name -c $app_name |grep -i error|head -10>/opt/scripts/$app_name/failure/$TIME/error.log
  else
    mkdir -p /opt/scripts/$app_name/success/$TIME
    kubectl logs --selector=app=$app_name -c $app_name |head -10>/opt/scripts/$app_name/success/$TIME/success.log
  fi
done
