package com.mmall.common;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class ServerResponse<T> implements Serializable {

	private static final long serialVersionUID = -8512156636584777557L;
	//状态码
	private int status;
	//供前端提示使用
	private String msg;
	//供前端使用的数据
	private T data;

	private ServerResponse(int status) {
		this.status = status;
	}

	private ServerResponse(int status, T data) {
		this.status = status;
		this.data = data;
	}

	private ServerResponse(int status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	private ServerResponse(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	@JsonIgnore
	//使之不在json序列化结果当中
	//即返回响应对象时json当中不包含Success字段
	public boolean isSuccess() {
		return this.status == ResponseCode.SUCCESS.getCode();
	}

	public int getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 返回成功的响应对象
	 * 响应对象包含status
	 * @return
	 */
	public static <T> ServerResponse<T> createBySuccess() {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
	}

	/**
	 * 返回成功的响应对象
	 * 响应对象包含status msg
	 * @return
	 */
	public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
	}
	/**
	 * 返回成功的响应对象
	 * 响应对象包含status data
	 * @return
	 */

	public static <T> ServerResponse<T> createBySuccess(T data) {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
	}
	/**
	 * 返回成功的响应对象
	 * 响应对象包含status msg data
	 * @return
	 */

	public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
	}
	/**
	 * 返回失败的响应对象
	 * 响应对象包含status msg 
	 * status对应默认的状态码：1
	 * msg对应默认的失败描述:ERROR
	 * @return
	 */

	public static <T> ServerResponse<T> createByError() {
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
	}
	/**
	 * 返回失败的响应对象
	 * 响应对象包含status msg 
	 * status对应默认的状态码：1
	 * msg对应自定义的失败描述
	 * @return
	 */

	public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
	}
	/**
	 * 返回失败的响应对象
	 * 响应对象包含status msg 
	 * status对应自定义的状态码
	 * msg对应自定义的失败描述
	 * @return
	 */
	public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
		return new ServerResponse<T>(errorCode, errorMessage);
	}

}
