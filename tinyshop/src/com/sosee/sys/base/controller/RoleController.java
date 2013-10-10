package com.sosee.sys.base.controller;

import java.util.List;
import java.util.UUID;

import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.sosee.sys.base.interceptor.LoginInterceptor;
import com.sosee.sys.base.pojo.Moudle;
import com.sosee.sys.base.pojo.Role;
import com.sosee.sys.base.pojo.RoleMoudle;
import com.sosee.sys.base.pojo.UserRole;
import com.sosee.sys.base.validator.RoleValidator;
import com.sosee.sys.util.SysConstants;
/**
 * 
 *作者：强乐
 *功能：角色管理，角色授权用户，角色授权模块
 * 2012-12-4 上午11:19:32
 */
@Before(LoginInterceptor.class)
public class RoleController extends BaseController {
   
	/*
	 * 查询
	 */
	public void index(){
		this.createToken("roleToken",1800);
		this.setAttr("roleList", getModel(Role.class).find("select * from t_role"));
		this.render("/WEB-INF/sys/user/roleList.html");
	}
	
	/*
	 * 保存（添加和编辑）
	 */
	@Before(RoleValidator.class)
	public void save() {
		try{
			Role role=getModel(Role.class);
			boolean bInfo=false;
			if(role.getStr("id")!=null && !role.getStr("id").trim().equals("")){
				//编辑
				this.setAttr("type", "edit");
				bInfo=role.update();				
			}else{
				//添加
				this.setAttr("type", "add");
				role.set("id", UUID.randomUUID().toString());
				bInfo=role.save();	
			}
			if(bInfo){
				this.setAttr("msg", "数据保存成功!");
			}else{
				this.setAttr("errorinfo", "数据保存失败!");
			}
		}catch (Exception e) {
			this.keepModel(Moudle.class);
			this.setAttr("errorinfo", "数据保存失败!");
		}
		index();
	}
	
	/*
	 * 编辑
	 */
	public void edit() {
		try{
			String id=this.getPara();
			if(id!=null && id!=null){
				Role role=this.getModel(Role.class).findById(id);
				if(role!=null && !role.equals("")){
					this.renderJson("{\"result\":1,\"role\":"+role.toJson()+",\"msg\":\"\"}");
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/*
	 * 删除
	 */
	public void del() {
		try{
			Role role=getModel(Role.class);
			if(role.getStr("id")!=null && !role.getStr("id").trim().equals("")){
				Db.update("delete from t_rolemoudle where roleId='"+role.getStr("id").trim()+"'");
				Db.update("delete from t_userrole where roleId='"+role.getStr("id").trim()+"'");
				boolean bInfo=role.deleteById(role.getStr("id"));
				if(bInfo){
					this.setAttr("msg", "数据删除成功!");
				}else{
					this.setAttr("errorinfo", "数据删除失败!");
				}
			}else{
				this.setAttr("errorinfo", "数据删除失败!");
			}
		}catch(Exception e){
			this.setAttr("errorinfo", "数据删除失败!");
		}
		index();
	}
	
	/*
	 * 授权模块查询
	 */
	public void configMoudleQuery() {
		try{
			String id=this.getPara();
			if(id!=null && !id.trim().equals("")){
				List<Record> moudleList=Db.find("select m.id,m.moudleName,m.code,m.parentId,rrm.moudleId from t_moudle as m left join (select rm.moudleId from t_rolemoudle as rm where rm.roleId='"+id+"') as rrm on rrm.moudleId=m.id order by m.code ");
				if(moudleList!=null && moudleList.size()>0){
					this.renderJson("{\"result\":1,\"moudleRole\":"+JsonKit.listToJson(moudleList, 4)+",\"msg\":\"\"}");
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/*
	 * 授权模块保存
	 */
	public void configMoudleSave() {
		try{
			String id=this.getPara("id");
			String[] model=this.getParaValues("model[]");
			if(id!=null && !id.trim().equals("") && model!=null && model.length>0){
				//查询原始角色模块，循环出需要添加的新模块，删除老的模块
				List<RoleMoudle> rmList=this.getModel(RoleMoudle.class).find("select * from t_rolemoudle where roleId='"+id+"'");
				if(rmList!=null && rmList.size()>0){
					String strRM="";
					for(int i=rmList.size()-1;i>=0;i--){
						RoleMoudle rm=rmList.get(i);
						for(int j=model.length-1;j>=0;j--){
							String mStr=model[j];
							if(mStr.equals(rm.getStr("moudleId"))){
								model[j]="";
								rmList.remove(rm);
								strRM+=" and moudleId!='"+rm.getStr("moudleId")+"' ";
							}
						}
					}
					if(!strRM.equals("")){
						Db.update("delete from t_rolemoudle where roleId='"+id+"' "+strRM);
					}
				}
				RoleMoudle rm=this.getModel(RoleMoudle.class);
				rm.set("roleId", id);
				for(String m : model){
					if(m!=null && !m.equals("")){
						rm.set("moudleId", m);
						rm.save();
					}
				}
				this.renderJson("{\"result\":1,\"msg\":\"授权成功!\"}");
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"数据异常!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"数据异常!\"}");
		}
	}
	
	/*
	 * 授权用户查询
	 */
	public void configUserQuery() {
		try{
			String id=this.getPara("id");
			int pageIndex=this.getParaToInt("pageIndex",1);
			if(id!=null && !id.trim().equals("") && pageIndex>=0){
				Page<Record> userList=Db.paginate(pageIndex, 10, "select u.id,u.name,u.account,b.name as status ", "from t_user as u left join (select ur.userId from t_userrole as ur where ur.roleId='"
						+id+"') as uur on uur.userId=u.id left join t_basecode as b on u.status=b.code and b.categoryId='"+SysConstants.BASECODE_USER_STATUS+"' where u.id!='"+SysConstants.ADMIN_ID+"'  order by u.createTime desc");
				if(userList!=null && userList.getList()!=null && userList.getList().size()>0){
					this.renderJson("{\"result\":1,\"userRole\":"+JsonKit.listToJson(userList.getList(), 4)
							+",\"msg\":\"\",\"pageIndex\":"+userList.getPageNumber()+",\"pageCount\":"+userList.getTotalPage()+"}");
				}else{
					this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
				}
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"查询出错!\"}");
		}
	}
	
	/*
	 * 授权用户保存
	 */
	public void configUserSave() {
		try{
			String id=this.getPara("id");
			String[] user=this.getParaValues("user[]");
			String[] nouser=this.getParaValues("nouser[]");
			if(id!=null && !id.trim().equals("") && ((user!=null && user.length>0 ) || ( nouser!=null && nouser.length>0))){				
				String strDel="";
				String strAdd="";
				//删除移除的
				if(nouser!=null && nouser.length>0 ){
					for(String s : nouser){
						strDel+=" or userId='"+s+"' ";
					}
					Db.update("delete from t_userrole where roleId='"+id+"' and ("+strDel.substring(3, strDel.length()-1)+") ");
				}
				//添加新增的
				if(user!=null && user.length>0 ){
					for(String s : user){
						strAdd+=" or userId='"+s+"' ";
					}
					List<UserRole> urList=this.getModel(UserRole.class).find("select * from t_userrole where roleId='"+id+"' and ("+strAdd.substring(3, strAdd.length()-1)+")");
					UserRole ur=this.getModel(UserRole.class);
					ur.set("roleId", id);
					for(String s : user){
						boolean b=true;
						for(UserRole ur1 : urList){
							if(ur1.getStr("userId").equals(s)){
								b=false;
								break;
							}
						}
						if(b){
							ur.set("userId", s);
							ur.save();
						}
					}
				}
				this.renderJson("{\"result\":1,\"msg\":\"授权成功!\"}");
			}else{
				this.renderJson("{\"result\":0,\"msg\":\"数据异常!\"}");
			}
		}catch(Exception e){
			this.renderJson("{\"result\":0,\"msg\":\"数据异常!\"}");
		}
	}
}