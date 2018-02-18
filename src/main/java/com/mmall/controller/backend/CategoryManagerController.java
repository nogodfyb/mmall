package com.mmall.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserSerivce;

@RequestMapping("/manage/category")
@Controller
public class CategoryManagerController {

	@Autowired
	private IUserSerivce userService;

	@Autowired
	private ICategoryService categoryService;

	@RequestMapping("get_category.do")
	@ResponseBody
	public ServerResponse getChildrenParallelCategory(HttpSession session,
			@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
		}
		// 是管理员才有权限进行查询
		if (userService.checkAdminRole(user).isSuccess()) {
			// 查询子节点的category信息,并且不递归,保持平级
			return categoryService.getChildrenParallelCategory(categoryId);
		} else {
			return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
		}
	}
	/**
	 * 添加分类节点
	 * @param session
	 * @param categoryName
	 * @param parentId
	 * @return
	 */

	@RequestMapping("add_category.do")
	@ResponseBody
	public ServerResponse addCategory(HttpSession session, String categoryName,
			@RequestParam(value = "parentId", defaultValue = "0") int parentId) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if (user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
		}
		// 校验一下是否是管理员
		if (userService.checkAdminRole(user).isSuccess()) {
			// 是管理员
			// 增加我们处理分类的逻辑
			return categoryService.addCategory(categoryName, parentId);

		} else {
			return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
		}
	}
	
	/**
	 * 修改品类名字
	 * @param session
	 * @param categoryId
	 * @param categoryName
	 * @return
	 */
	
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return categoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
    /**
     * 查询当前节点的id和递归子节点的id
     * @param session
     * @param categoryId
     * @return
     */
    
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return categoryService.selectCategoryAndChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

}
