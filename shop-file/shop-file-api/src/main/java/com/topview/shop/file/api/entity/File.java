package com.topview.shop.file.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author namelesssss
 */
@ToString
@EqualsAndHashCode
@Data
@TableName(value = "file")
@Accessors(chain = true)
public class File {

	public static final String COL_ID = "id";

	public static final String COL_ADDRESS = "address";

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 文件地址
	 */
	@TableField(value = "address")
	private String address;

	/**
	 * 文件名
	 */
	@TableField(value = "file_name")
	private String fileName;

	/**
	 * 文件上传时间
	 */
	@TableField(value = "upload_time")
	private LocalDateTime uploadTime;

	/**
	 * MIME类型
	 */
	@TableField(value = "content_type")
	private String contentType;

	/**
	 * 文件唯一标识
	 */
	@TableField(value = "specific_value")
	private String specificValue;

	/**
	 * 原文件地址
	 */
	@TableField(value = "origin_address")
	private String originAddress;

	/**
	 * 原文件类型
	 */
	@TableField(value = "origin_type")
	private String originType;

	/**
	 * 文件容量
	 */
	@TableField(value = "capacity")
	private Long capacity;

	/**
	 * 文件总容量
	 */
	@TableField(exist = false)
	private Long totalCapacity;

	/**
	 * 文件是否被使用（针对资料文件）
	 */
	@TableField(value = "is_used")
	private Byte isUsed;

}
