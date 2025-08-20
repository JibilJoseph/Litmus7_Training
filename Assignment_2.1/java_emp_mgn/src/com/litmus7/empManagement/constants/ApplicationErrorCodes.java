package com.litmus7.empManagement.constants;

public interface ApplicationErrorCodes {

    // Database Errors
    int GET_EMPLOYEE_BY_ID_FAILURE = 501;
    int SAVE_EMPLOYEE_FAILURE = 502;
    int GET_ALL_EMPLOYEES_FAILURE = 503;
    int EMPLOYEE_EXISTS_FAILURE = 504;
    int DELETE_EMPLOYEE_BY_ID_FAILURE = 505;
    int UPDATE_EMPLOYEE_FAILURE = 506;
    int ADD_EMPLOYEES_IN_BATCH_FAILURE = 507;
    int TRANSFER_EMPLOYEES_TO_DEPARTMENT_FAILURE = 508;
    int DATABASE_CONNECTION_FAILURE = 509;

    // Validation Errors
    int INVALID_EMPLOYEE_ID = 601;
    int INVALID_EMPLOYEE_DATA = 602;

    // General Errors
    int UNEXPECTED_ERROR = 701;
}
