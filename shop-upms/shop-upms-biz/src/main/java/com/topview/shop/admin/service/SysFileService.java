package com.topview.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.topview.shop.admin.api.entity.SysFile;
import com.topview.shop.common.core.util.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
public interface SysFileService extends IService<SysFile> {

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	CommonResult uploadFile(MultipartFile file);

	/**
	 * 读取文件
	 * @param bucket 桶名称
	 * @param fileName 文件名称
	 * @param response 输出流
	 */
	void getFile(String bucket, String fileName, HttpServletResponse response);

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	Boolean deleteFile(Long id);

	/**
	 * 获取外网访问地址
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	String onlineFile(String bucket, String fileName);

}
