package com.scdq.manager.attach.model;

import com.scdq.manager.common.BasicModel;

/**
 * 待办事项
 * @author zenghaibo
 * @date 2019-04-10
 */
public class Todo extends BasicModel {
	
	// 待办类型
	private int type;
	
	// 待办状态
	private int status;
	
	// 待办内容
	private String content;
	
	public Todo() {
		super();
	}
	
	public Todo(long id) {
		super(id);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
