package com.apurva.assignment.justlightapp.Utility;

public class Constants
{
    public static final String BASE_USER_URL="http://ec2-34-210-191-128.us-west-2.compute.amazonaws.com:8080/JustLightAppBackend/justLight/user/";
    public static final String BASE_CUSTOMER_URL="http://ec2-34-210-191-128.us-west-2.compute.amazonaws.com:8080/JustLightAppBackend/justLight/customer/";
    public static final String BASE_PATNERS_URL="http://ec2-34-210-191-128.us-west-2.compute.amazonaws.com:8080/JustLightAppBackend/justLight/greenEnergySolution/";

    public static final String AddUser = "addNewUser";
    public static final String AddUserProfile = "addUserDetails";
    public static final String Authenticate = "credentials/verify";
    public static final String AuthorizationRequest = "associatedRequest/";
    public static final String OrderHistoryCustomer = "associatedOrder/";
    public static final String customerProfile="getUserDetails/";
    public static final String PatnerInfo="associatedPartner/";
    public static final String Rating="addAppRating";
    public static final String DemoRequest="addNewRequest";
    public static final String Credentials="getUserCredentials/1/Customer";
    public static final String CredentialsUpdate="credentials/update";
    public static final String ProfileUpdate="userDetails/update";
}
