package com.litmus7.empManagement.model;

public class Response<T>
{
	private int statusCode;
	private String message;
	private T data;
	
	// default constructor
	public Response()
	{
		
	}
	
	// constructor with status and msg
	
	public Response(int statusCode,String message)
	{
		this.statusCode=statusCode;
		this.message=message;
		
	}
	
	//constructor with all
	
	public Response(int statusCode,String message,T data)
	{
		this.statusCode=statusCode;
		this.message=message;
		this.data=data;
	}
	
	// getters and setter : for statusCode
	
	public int getStatusCode()
	{
		return statusCode;
	}
	
	public void setStatusCode(int statusCode)
	{
		this.statusCode=statusCode;
	}
	
	// getters and setter : for message
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message=message;
	}
	
	// getters and setter : for data
	
	public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
	
}