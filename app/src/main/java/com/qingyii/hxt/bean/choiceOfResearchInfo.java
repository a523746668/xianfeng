package com.qingyii.hxt.bean;

import java.io.Serializable;

public class choiceOfResearchInfo implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	  * 调查问卷选项实体类
	  */
		private String choiceId;
		private String choiceContent;

		public String getChoiceId() {
			return choiceId;
		}
		public void setChoiceId(String choiceId) {
			this.choiceId = choiceId;
		}
		public String getChoiceContent() {
			return choiceContent;
		}
		public void setChoiceContent(String choiceContent) {
			this.choiceContent = choiceContent;
		}
}
