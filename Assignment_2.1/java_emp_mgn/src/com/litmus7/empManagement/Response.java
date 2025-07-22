package com.litmus7.empManagement;

public class Response {
    private int code; // 1 = int, 2 = string
    private int intValue;
    private String stringValue;

    // Constructor for integer response
    public Response(int code, int intValue) {
        this.code = code;
        this.intValue = intValue;
        this.stringValue = null;
    }

    // Constructor for string response
    public Response(int code, String stringValue) {
        this.code = code;
        this.stringValue = stringValue;
        this.intValue = 0;
    }

    public int getCode() {
        return code;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public String toString() {
        if (code == 1) {
            return "Response{code=" + code + ", intValue=" + intValue + '}';
        } else if (code == 2) {
            return "Response{code=" + code + ", stringValue='" + stringValue + "'}";
        } else {
            return "Response{code=" + code + ", value=undefined}";
        }
    }
}
