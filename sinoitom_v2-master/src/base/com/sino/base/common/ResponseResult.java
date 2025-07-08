package com.sino.base.common;

import com.alibaba.fastjson.JSON;

public class ResponseResult<T> {

	private int status;
	private T result;
	private String message;
	private Long timestamp;
	private String code;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static <T> ResponseResult<T> ok(T result, String message) {
		return new ResponseResult<T>().result(result).putTimeStamp().message(message).status(200);
	}

	public static <T> ResponseResult<T> ok(T result) {
		return ok(result, "");
	}

	public static <T> ResponseResult<T> ok() {
		return ok(null, "");
	}

	public static <T> ResponseResult<T> error(String message) {
		return new ResponseResult<T>().putTimeStamp().message(message).status(500);
	}

	private ResponseResult<T> putTimeStamp() {
		this.timestamp = System.currentTimeMillis();
		return this;
	}

	public ResponseResult<T> result(T result) {
		this.result = result;
		return this;
	}

	public ResponseResult<T> status(int status) {
		this.status = status;
		return this;
	}

	public ResponseResult<T> code(String code) {
		this.code = code;
		return this;
	}

	public ResponseResult<T> message(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
