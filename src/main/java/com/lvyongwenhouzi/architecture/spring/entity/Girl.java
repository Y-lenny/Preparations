package com.lvyongwenhouzi.architecture.spring.entity;

import lombok.Data;

/**
 * <b>实体类</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-12-30 15:57
 * @since 1.0
 */
@Data
public class Girl {

	private Long id;

	/**
	 * 昵称
	 */
	private String username;

	/**
	 * 性别
	 */
	private String sex;
}
