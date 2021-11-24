package com.thermax.cp.salesforce.query;

public interface QueryConstants {

    String ACCOUNT_INFO_QUERY = "SELECT+name,ID,TH_GST_Number__c,TH_CIN_Number__c,Rating,TH_Status__c+from+Account+limit+10";

    String ASSET_DETAIL_QUERY = "SELECT+Id,Name,TH_Asset__c,TH_Spare__c,TH_Asset_Name__c,TH_Change_Type__c,TH_Description_for__c+from+TH_Asset_History__c+ where+TH_Asset__c+=";

    String ASSET_TAGGED_TO_ACCOUNT_QUERY = "SELECT+Id,Name,TH_IBG_Division__c,THCH_Region__c ,Asset_Make__c,AccountId,THCH_Asset_Status__c,THCH_Sales_Order_Number__c+from Asset+where+accountId+=";

    String FILE_VIA_CONTENT_VERSION_QUERY = "SELECT+ContentDocumentId+from+ContentVersion+ where + Id+ =";

    String ASSETS_FOR_ACCOUNT_QUERY = "SELECT+Id,Name,TH_IBG_Division__c,THCH_Region__c ,Asset_Make__c,AccountId,THCH_Asset_Status__c,THCH_Sales_Order_Number__c+from Asset+where+accountId+=";

    String FETCH_CONTENT_DOCUMENT_QUERY = "SELECT+ContentDocumentId+from+ContentVersion+ where + Id+ = +";

    String PRODUCT_DETAILS_QUERY = "SELECT+Id,Description,Family,IsActive,Name,TH_UOM__c,ProductCode+FROM+Product2+ where+ lastmodifiedDate + = + ";

    String ACCOUNT_DETAILS_QUERY = "select+id,name,Type,website,AccountSource,TH_Existing_Customer_for_Water_Division__c,TH_Existing_Customer_forHeating_Division__c,Account_Number__c," +
            "THCMG_ERP_Operating_Unit__c,Rating,TH_Status__c,TH_CIN_Number__c,TH_GST_Number__c,TH_Pan_No__c,ParentId,Phone,AccountNumber ,THCH_Email__c,TH_IsActive__c+from account where +Account_Number__c+!=null+and+TH_Status__c+=+'Approved'+and+recordtype.developerName+=+'Customer_Account_Thermax_account'+and+lastmodifiedDate += +";

    String RECOMMENDATIONS_QUERY = "SELECT+Id,LastModifiedDate,CreatedDate,Name,OwnerId,Owner.Name,TH_Account_Name__c,TH_Additional_Information__c,TH_Asset__c,TH_City__c,TH_Closure_Notes__c,TH_Description_for__c,TH_Description__c,TH_Interaction_Planned_Date__c,TH_Opportunity__c," +
            "TH_Planned_Activities__c,TH_Planned_Shutdown_date__c,TH_Potential_Value__c,TH_Purpose__c,TH_Recommendation_Close_Date__c,TH_Reject_Reason__c,TH_Reminder_For_Next_Contact__c,TH_Service__c,TH_Service__r.THSC_Contract_Start_Date__c,TH_Service__r.THSC_Contract_End_Date__c," +
            "TH_Service__r.THSC_No_of_Visits__c,TH_Service__r.THSC_Description__c,TH_Service__r.THSC_Ordered_Item__c,TH_Spare__c,TH_Spare__r.TH_DESCRIPTION__c," +
            "TH_Spare__r.TH_LifeCycle_days__c,TH_Spare__r.TH_ORDER_LINE_ID__c,TH_Status__c,TH_SubType__c,TH_Type__c,TH_Selected_Services__c,TH_Part_Code__c,TH_Selected_Spares__c+FROM+TH_Interaction__c where  recordtype.name = 'Recommendation' and lastmodifiedDate = +";

    String SERVICES_DETAILS_QUERY = "select+id,name,TH_Count_of_Visit_Log__c,THSC_No_of_Visits__c,THSC_Contract_Start_Date__c,THSC_Contract_End_Date__c,THSC_Asset__c,THSC_Region__c,THSC_Division__c,THSC_Executor__c,THSC_Sales_Order_Number__c,THSC_Quantity__c," +
            " THSC_Unit_Selling_Price__c,THSC_UOM__c,THSC_Ordered_Item__c,THSC_Line_Total__c,THSC_Order_Line_ID__c,TH_Line_Status__c,THSC_Description__c+from+THSC_Branded_services_sales__c+where+lastmodifiedDate+=+";

    String SPARES_DETAILS_QUERY = "SELECT+Id,Name ,TH_ORDER_LINE_ID__c,Th_Asset__c,THSC_Division__c,TH_LINE_STATUS__c,TH_DESCRIPTION__c,TH_SALES_ORDER_NUMBER__c,TH_LINE_TOTAL__c,THSC_Region__c,TH_QTY__c,TH_ORDERED_ITEM__c,TH_UNIT_SELLING_PRICE__c," +
            "TH_UOM__c,TH_Thermax_Spare__c,TH_LifeCycle_days__c,TH_Account_Id__c,createdDate,lastmodifiedDate,TH_LAST_UPDATE_DATE__c+from+TH_Spare_FOC__c+where+lastmodifiedDate+=+";

    String ASSET_DETAILS_QUERY = "SELECT+Id,Name,InstallDate,THSC_Warranty_Expiry_Date__c,THS_Asset_Service_By_From_ERP__c,OwnerId,Owner.Name,Owner.UserRole.Name,Owner.MobilePhone,Owner.Email,AccountId,TH_IBG_Division__c,THS_Division_Type__c,THCH_Region__c," +
            "THCH_Sales_Order_Number__c,Asset_Make__c,THCH_Asset_Status__c,Calorie_Potential__c,THSC_I_C_Scope__c,THSC_Number_of_days_included_in_PO__c,THS_I_C_Scope__c,THS_Service_Sales_Engineer__c,TPF_User__c,THS_WARRANTY_DUR_FR_COMM_DT__c,THS_WARRANTY_DUR_FR_DISP_DT__c," +
            "THSC_First_Date_of_Dispatch__c,TH_Shipment_received_date__c,TH_IBG_Commissioning_Date__c,Revised_warranty_expiry_date__c,Warranty_Revision_Status__c,Reason_for_extended_warranty__c," +
            "THSC_Country__c,THS_Order__r.THCMG_Date_Ordered__c,TH_When_to_Engage_Customer_days__c,createdDate,LastmodifiedDate,TMAX_TCA_User__c,TMAX_TCA_User__r.Name," +
            "TMAX_TCA_User__r.UserRole.Name,TMAX_TCA_User__r.MobilePhone,TMAX_TCA_User__r.Email,TMAX_Service_SPOC_CP__c,TMAX_Service_SPOC_CP__r.Name,TMAX_Service_SPOC_CP__r.UserRole.Name,TMAX_Service_SPOC_CP__r.MobilePhone," +
            "TMAX_Service_SPOC_CP__r.Email,TMAX_Spares_Sales_SPOC_CP__c,TMAX_Spares_Sales_SPOC_CP__r.Name,TMAX_Spares_Sales_SPOC_CP__r.UserRole.Name,TMAX_Spares_Sales_SPOC_CP__r.MobilePhone,TMAX_Spares_Sales_SPOC_CP__r.Email,TMAX_Service_Sales_SPOC_CP__c," +
            "TMAX_Service_Sales_SPOC_CP__r.Name,TMAX_Service_Sales_SPOC_CP__r.UserRole.Name,TMAX_Service_Sales_SPOC_CP__r.MobilePhone,TMAX_Service_Sales_SPOC_CP__r.Email,ContactId,Contact.Name,TMAX_Product_Family_CP__c+from+asset+where+TH_IBG_Division__c+in+('Heating','Cooling','Enviro','Water')+and+Visibility_in_Sales_Portal__c+=+TRUE+and lastmodifiedDate+=+";

    String OPPORTUNITIES_QUERY = "SELECT+Id,Name,AccountId,CloseDate,StageName,Amount,TH_Opportunity_Type__c,TH_Customer_Type__c,TH_Region__c,THCH_Zone__c,THCH_Territory__c,TH_Opportunity_Number__c,THSC_Opportunity_Asset_Id__c," +
            "Probability,TH_Product_Family__c,THCH_Techno_Commercial_Acceptance_Date__c,TH_RFQ_Completed__c,TH_Reason_for_Closed_Lost_Won_Drop__c,CMG_Won_against_Whom__c,TH_Lost_to_Whom__c," +
            "TH_Closing_Summary__c,THCMG_Date_Ordered__c,createdDate,THCMG_Bill_To_Location__c,THCMG_Ship_To_Location__c,THCMG_Bill_To_Location__r.THCMG_Country__c,THCMG_Bill_To_Location__r.THCMG_State__c,THCMG_Bill_To_Location__r.THCMG_City__c," +
            "THCMG_Bill_To_Location__r.THCMG_Pin_Code__c,THCMG_Bill_To_Location__r.THCMG_Address1__c,THCMG_Bill_To_Location__r.THCMG_Address2__c,THCMG_Bill_To_Location__r.THCMG_Address3__c,THCMG_Ship_To_Location__r.THCMG_Country__c," +
            "THCMG_Ship_To_Location__r.THCMG_State__c,THCMG_Ship_To_Location__r.THCMG_City__c,THCMG_Ship_To_Location__r.THCMG_Pin_Code__c,THCMG_Ship_To_Location__r.THCMG_Address1__c,THCMG_Ship_To_Location__r.THCMG_Address2__c,THCMG_Ship_To_Location__r.THCMG_Address3__c," +
            "THCMG_Cheque_Number__c,THCMG_Customer_PO__c,THCMG_ERP_Division__c,THCMG_ERP_Division__r.Name,THCMG_Payment_Term__c,THCMG_Payment_Term__r.Name,THCMG_Warehouse__c,THCMG_Warehouse__r.Name,THCMG_Transaction_Type__c,THCMG_Transaction_Type__r.Name,THCMG_FOB__c,THCMG_Freight_Terms__c,OwnerId," +
            "Owner.Name,Owner.Email,Owner.MobilePhone,(select+id,opportunityId,product2Id,TH_Product_Family__c,TH_Forecast_Category_Name__c,TH_ENV_Enviro_Quantity__c,ProductCode,ListPrice,UnitPrice,Quantity,THCMG_Product_Name__c,TH_CNH_Division__c,TH_Asset__c+from+OpportunityLineItems),(Select+id,Asset__c+from OpportuntiyAsset__r+limit+1)" +
            "+from+Opportunity+where+recordtype.Name+=+'Spare+%26+Services'+and+lastmodifiedDate+=+";

    String OPPORTUNITY_LINE_ITEMS_QUERY = "select+id,opportunityId,product2Id,TH_Product_Family__c,TH_Forecast_Category_Name__c,TH_ENV_Enviro_Quantity__c,ProductCode," +
            "ListPrice,UnitPrice,Quantity,THCMG_Product_Name__c,TH_CNH_Division__c+from+OpportunityLineItem+where+lastmodifiedDate+=";

    String PRICEBOOKS_QUERY = "SELECT+id,Description,IsActive,Name+FROM+Pricebook2+where+lastmodifieddate+=";

    String PRICEBOOK_ENTRIES_QUERY = "SELECT+CurrencyIsoCode,Id,IsActive,LastModifiedDate,Name,Pricebook2Id,Product2Id,ProductCode," +
            "SystemModstamp,UnitPrice,UseStandardPrice+FROM+PricebookEntry+where+isActive+=+true+and+lastmodifiedDate+=";

    String USERS_QUERY = "SELECT+Id,Name,UserRole.Name,Email,Title,Address,CurrencyISOCode,ManagerId,Manager.Name,THCMG_ERP_USER_ID__c,EmployeeNumber,THCH_Services__c," +
            "TH_IBG_Regions__c,THCS_Division__c+from+User+where+isActive+=true+and+lastmodifiedDate+=";

    String COMPLAINTS_QUERY = "SELECT+Id,Owner_Role_name__c,Owners_MobilePhone__c,CaseNumber,RecordType.Name,Reason,OwnerId,owner.Name,owner.email,Status,Subject" +
            ",Description,THS_Source__c,AccountId,AssetId,ContactId,THS_Division__c,Zone__c,THS_Sub_Division__c,Origin,THS_Case_Source__c,TH_Region__c,THS_Dept_Dependency__c,THC_Country__c,THS_Asset_Status__c,Priority,CreatedDate,lastmodifieddate+from+case+where+lastmodifieddate+=";

    String ORDERS_QUERY = "SELECT Id,orderNumber,THCMG_Customer_PO__c,AccountId,THCMG_Payment_Term__c,THCMG_ERP_Operating_Unit__c,THCMG_Cheque_Number__c,THCMG_Transaction_Type_Id__c,THCMG_Bill_To_Location__c,THCMG_Warehouse__c,THCMG_Ship_To_Location__c,THCMG_Date_Ordered__c,THCMG_Payment_Type__c,THCMG_Request_Date__c,EffectiveDate,TotalAmount,THCMG_FOB__c,TH_Division__c,THCMG_Freight_Terms__c,Status,OpportunityId,ERP_Order_Number__c,TH_Opportunity_Number__c,Asset__c,ERP_Credit_Rating__c,ERP_invoice__c,ERP_Invoiced_value__c,ERP_order_booked_date__c,ERP_Order_Value__c,ERP_Order_status__c,OwnerId,Owner.Name,Opportunity.OwnerId,Opportunity.Owner.Name,Opportunity.Owner.Email,Opportunity.Owner.MobilePhone,Opportunity.Owner.UserRole.Name,Opportunity.TH_Opportunity_Type__c,(SELECT+Id,Product2Id,TH_Product_Code__c,Listprice,unitprice,totalprice,THCMG_ERP_User_Id__c,TH_Asset__c+from+OrderItems) from Order where LastmodifiedDate+=";

    String ORDER_ITEMS_QUERY = "SELECT+Id,Product2Id,TH_Product_Code__c,Listprice,unitprice,totalprice,THCMG_ERP_User_Id__c,TH_Asset__c+from+OrderItem where +lastmodifiedDate+=";

    String OPPORTUNITY_CONTACT_ROLE = "SELECT+ContactId,Id,IsPrimary,OpportunityId,Role+FROM+OpportunityContactRole+where+lastmodifiedDate+=";

    String PROPOSALS_QUERY = "SELECT Account__c,Additional_Input_By_Proposer__c,Additional_Input_By_Requester__c,Asset__c,Committed_Date__c,CreatedDate,Department__c,Id,IsLatestVersion__c,IsRevisedAfterClosure__c,LastModifiedDate,Name,Opportunity__c,Original_Proposal__c,Proposal_Number__c,Proposer_User__c,Reason__c," +
            "Status__c FROM Tmax_Proposal__c where Opportunity__r.recordtype.name='Spare+%26+Services'+and+lastmodifiedDate+=";

    String ELIGIBLE_SPARE_SERVICE_QUERY = "SELECT+Id,Asset__c,CurrencyIsoCode,Life_Cycle_Date__c,Name,Part_Number__c,TH_Thermax_Spare__c,Type__c,When_to_Engage_Customer__c+FROM+Master_Asset_to_Spare_Map__c+where+lastmodifiedDate+=";

    String ASSET_HISTORY_QUERY = "SELECT+Id,Name,TH_Asset__c,TH_Spare__c,TH_Change_Type__c,TH_Description_for__c,TH_Account__c,lastmodifiedDate+from+TH_Asset_History__c+where+TH_Asset__c !=null and lastmodifiedDate+=+";

    String CONTACTS_QUERY = "select+id,firstName,middleName,lastName ,accountId ,Account.Name, Email, department,title,phone,mobilephone,MailingStreet, MailingCity, MailingState, MailingPostalCode,MailingCountry,Salutation,TH_IBG_International_Calling_Code__c,TH_IsActive__c,TMAX_isActiveForCP__c+from+Contact+where+TMAX_isActiveForCP__c=true+AND+TH_IsActive__c=true+AND+lastmodifiedDate+=";

    String SERVICE_LOG_QUERY = "SELECT+Branded_services_sales__c,Comments__c,CreatedDate,Id,Name,Visit_Date__c+FROM+Branded_Service_Visit_Log__c+where+lastmodifiedDate+=+";

    String THERMAX_USERS_QUERY = "SELECT+Id,Name,UserRole.Name,Email,Title,Address,CurrencyISOCode,ManagerId,Manager.Name," +
            "THCMG_ERP_USER_ID__c,EmployeeNumber,THCH_Services__c,TH_IBG_Regions__c,THCS_Division__c+from+User+where+isActive+=true+and+lastmodifiedDate+=+";
}