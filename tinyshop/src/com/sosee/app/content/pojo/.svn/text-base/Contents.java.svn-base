package com.sosee.app.content.pojo;


import com.jfinal.plugin.activerecord.Model;

/**
 * @author  :outworld
 * @date    :2012-11-28 上午10:23:08
 * @Copyright:2012 outworld Studio Inc. All rights reserved.
 * @function:内容对象
 modifier varchar(20), modifyTime datetime NULL, creator varchar(20), createTime datetime NULL, PRIMARY KEY (id));

 * String id;								//标识 UUID
 * String itemType varchar(30) NOT NULL;    //栏目类型，用于区分栏目
 * String title varchar(200) NOT NULL;		//标题
 * String subTitle varchar(250);			//副标题
 * String contentType varchar(30) NOT NULL;	//新闻类型,利用常量来定义:文字：word、图片photo、视频video、广告ad、其他other
 * String itemId varchar(40) NOT NULL;		//所属栏目
 * String source varchar(120);				//来源
 * String sourceUrl varchar(200);			//来源的URL，如果非空则直接连接此连接即可
 * String author varchar(20);				//作者
 * String summary varchar(500);				//摘要
 * String contents longtext;			    //内容
 * String imageFile varchar(200);			//图片文件名，图片文件，视频时也需要上传一个图片
 * String videoFile varchar(200);			//视频文件名，上传的视频文件名
 * String attachFile varchar(200);			//普通文本，只支持一个普通附件上传功能
 * Boolean isTop tinyint(1) DEFAULT 0;		//是否置顶
 * Boolean isShared tinyint(1) DEFAULT 0;	//是否分享
 * Date    newsDate;                        //新闻日期
 * String checker varchar(20);              //审核人
 * String reviewReason varchar(300);		//审批理由
 * Date reviewTime datetime;				//审核时间
 * String status varchar(30) NOT NULL;		//状态 S01:草稿，S02:待审核,S03:已审核,S04:已发布,S05:已归档
 * int browseSum;							//浏览次数
 * String modifier varchar(20);				//修改人名称
 * Date modifyTime datetime;				//修改日期
 * String creator varchar(20);				//创建人user 名称
 * Date createTime;			    			//新闻制作时间
 */

@SuppressWarnings("serial")
public class Contents extends Model<Contents> {
}
