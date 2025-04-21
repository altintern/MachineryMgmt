package com.machinarymgmt.service.api.utils;

public class Constants {

    // URLs
    public static final String API_VERSION = "v1";
    public static final String BASE_URL = "/api/" + API_VERSION;
    public static final String EQUIPMENT_URL = BASE_URL + "/equipment";
    public static final String EQUIPMENTAPI_URL = BASE_URL + "/equipment/api";
    public static final String PROJECT_URL = BASE_URL + "/projects";
    public static final String PROJECTAPI_URL = BASE_URL + "/projects/api";
    public static final String MAINTENANCE_URL = BASE_URL + "/maintenance";
    public static final String MAINTENANCEAPI_URL = BASE_URL + "/maintenance/api";
    public static final String CATEGORY_URL = BASE_URL + "/categories";
    public static final String MAKE_URL = BASE_URL + "/makes";
    public static final String MODEL_URL = BASE_URL + "/models";
    public static final String ITEM_URL = BASE_URL + "/items";
    public static final String INCIDENT_URL = BASE_URL + "/incidents";

    // Success messages
    public static final String RESPONSE_MESSAGE_KEY_SUCCESS = "success";
    public static final String RESPONSE_MESSAGE_KEY_CREATED = "created";
    public static final String RESPONSE_MESSAGE_KEY_NO_CONTENT = "no.content";

    // Client-side error messages
    public static final String RESPONSE_MESSAGE_KEY_VALIDATION_ERROR = "validation.error";
    public static final String RESPONSE_MESSAGE_KEY_NOT_FOUND = "not.found";
    public static final String RESPONSE_MESSAGE_KEY_UNAUTHORIZED = "unauthorized";
    public static final String RESPONSE_MESSAGE_KEY_FORBIDDEN = "forbidden";
    public static final String RESPONSE_MESSAGE_KEY_CONFLICT = "conflict";
    public static final String RESPONSE_MESSAGE_KEY_UNPROCESSABLE_ENTITY = "unprocessable.entity";

    // Server-side error messages
    public static final String RESPONSE_MESSAGE_KEY_SERVER_ERROR = "internal.server.error";
    public static final String RESPONSE_MESSAGE_KEY_SERVICE_UNAVAILABLE = "service.unavailable";
}

