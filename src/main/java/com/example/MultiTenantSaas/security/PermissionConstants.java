package com.example.MultiTenantSaas.security;

public final class PermissionConstants {

    private PermissionConstants(){

    }

    //USER PERMISSIONS
    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_READ = "USER_READ";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String USER_DELETE = "USER_DELETE";

    //ROLE PERMISSIONS
    public static final String ROLE_CREATE = "ROLE_CREATE";
    public static final String ROLE_READ = "ROLE_READ";
    public static final String ROLE_UPDATE = "ROLE_UPDATE";
    public static final String ROLE_DELETE = "ROLE_DELETE";

    //Permission PERMISSIONS
    public static final String PERMISSION_CREATE = "USER_CREATE";
    public static final String PERMISSION_READ = "USER_READ";
    public static final String PERMISSION_DELETE = "USER_DELETE";

    //TENANT PERMISSIONS
    public static final String TENANT_READ = "TENANT_READ";
    public static final String TENANT_UPDATE = "TENANT_UPDATE";


}
