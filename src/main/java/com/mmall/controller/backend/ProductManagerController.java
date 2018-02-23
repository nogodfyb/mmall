package com.mmall.controller.backend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;

@Controller
@RequestMapping("/manage/product")
public class ProductManagerController {
	@Autowired
	private IUserService userService;

	@Autowired
	private IProductService productService;
	
	@Autowired
	private IFileService fileService;

	@RequestMapping("save.do")
	@ResponseBody
	public ServerResponse productSave(HttpSession session, Product product) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

		}
		if (userService.checkAdminRole(user).isSuccess()) {
			// 填充我们增加产品的业务逻辑
			return productService.saveOrUpdateProduct(product);
		} else {
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}

	@RequestMapping("set_sale_status.do")
	@ResponseBody
	public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

		}
		if (userService.checkAdminRole(user).isSuccess()) {
			return productService.setSaleStatus(productId, status);
		} else {
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}

	@RequestMapping("detail.do")
	@ResponseBody
	public ServerResponse getDetail(HttpSession session, Integer productId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

		}
		if (userService.checkAdminRole(user).isSuccess()) {
			// 填充业务
			return productService.manageProductDetail(productId);

		} else {
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}

	@RequestMapping("list.do")
	@ResponseBody
	public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

		}
		if (userService.checkAdminRole(user).isSuccess()) {
			// 填充业务
			return productService.getProductList(pageNum, pageSize);
		} else {
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}

	@RequestMapping("search.do")
	@ResponseBody
	public ServerResponse productSearch(HttpSession session, String productName, Integer productId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

		}
		if (userService.checkAdminRole(user).isSuccess()) {
			// 填充业务
			return productService.searchProduct(productName, productId, pageNum, pageSize);
		} else {
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}

	@RequestMapping("upload.do")
	@ResponseBody
	public ServerResponse upload(HttpSession session,
			@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
		}
		if (userService.checkAdminRole(user).isSuccess()) {
			String path = request.getSession().getServletContext().getRealPath("upload");
			Map map = fileService.upload(file, path);
			String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + map.get("targetFileName");

			Map fileMap = Maps.newHashMap();
			fileMap.put("uri", map.get("targetFileName"));
			fileMap.put("url", url);
			if((boolean) map.get("result")){
				return ServerResponse.createBySuccess(fileMap);
			}
			return ServerResponse.createByErrorMessage("上传失败");
			
		} else {
			return ServerResponse.createByErrorMessage("无权限操作");
		}
	}

}
