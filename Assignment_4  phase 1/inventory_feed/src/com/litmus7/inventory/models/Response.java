package com.litmus7.inventory.models;

public class Response<T>
{
	private int statusCode;
	private String message;
	private T data;
	private int totalCount;
	private int processedCount;
	
	// default constructor
	public Response()
	{
		this.totalCount = 0;
		this.processedCount = 0;
	}
	
	// constructor with status and msg
	
	public Response(int statusCode,String message)
	{
		this.statusCode=statusCode;
		this.message=message;
		this.totalCount = 0;
		this.processedCount = 0;
	}
	
	//constructor with all
	
	public Response(int statusCode,String message,T data)
	{
		this.statusCode=statusCode;
		this.message=message;
		this.data=data;
		this.totalCount = 0;
		this.processedCount = 0;
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

    // getters and setters for counts
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(int processedCount) {
        this.processedCount = processedCount;
    }
	
}
