#!/bin/bash
base64 --decode /home/configuration.txt>/home/config.txt
source /home/config.txt
az login -u $username -p $password
az account set --subscription 90a5cf8f-aa05-450d-9850-5d64c0f061d9
az aks get-credentials --overwrite --resource-group rgaz-cin-tcp-qa --name aks-cin-thermax-qa
kubectl config set-context --current --namespace=thermax
for app_name in $(cat /home/appnames.txt)
do
  success_status=$(kubectl get pod --field-selector status.phase=Running --no-headers|grep "$app_name"|head -1)
  echo $success_status
  if [[ $success_status == *"No resources"* ]]
  then 
    mkdir -p /opt/scripts/failure/$app_name
    kubectl logs --selector=app=$app_name -c $app_name |grep -i error|head -10>/opt/scripts/$app_name/error.log
  else
    mkdir -p /opt/scripts/success/$app_name
    kubectl logs --selector=app=$app_name -c $app_name |head -10>/opt/scripts/success/$app_name/success.log
  fi
done
