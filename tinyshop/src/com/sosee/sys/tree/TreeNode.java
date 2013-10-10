package com.sosee.sys.tree;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class TreeNode implements Serializable{
	private String id;
	private String pId;
    private String name;          		//节点显示 
    private boolean isParent=false;     //是否父节点 
    private boolean open=false;         //是否展开
    private String target;        		//显示的位置
    private String click;           	//点击事件
    private String url;                 //跳转的url
    private List<TreeNode> children;    //子节点 

    public TreeNode(){
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}


	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
    
}
