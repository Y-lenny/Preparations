package com.lvyongwenhouzi.architecture.spring.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应结构体
 *
 * @author 11114396
 */
@Data
@Builder
public class Result<T> implements Serializable {

	/**
	 * 响应码
	 */
	private String code;

	/**
	 * 响应消息
	 */
	private String desc;

	/**
	 * 响应值
	 */
	private T data;

	/**
	 * 成功响应快速创建方法
	 *
	 * @param data 响应值
	 * @return {@link Result} 响应结构体
	 */
	public static Result<Object> success(Object data) {

		return Result.builder().code("200").desc("成功").data(data).build();
	}

	/**
	 * 失败响应快速创建方法
	 *
	 * @param code 响应码
	 * @param msg  响应消息
	 * @return {@link Result} 响应结构体
	 */
	public static Result<Object> fail(String code, String msg) {

		return Result.builder().code(code).desc(msg).build();
	}


}
