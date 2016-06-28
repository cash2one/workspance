package com.ast.ast1949.enums;

public enum Gender {
	MAN {
		public String getName() {
			return "男";
		}
		public String getTitle(){
			return "先生";
		}
		public int getValue() {
			return 1;
		}
	},
	WOMAN {
		public String getName() {
			return "女";
		}
		public String getTitle(){
			return "女士";
		}
		public int getValue() {
			return 0;
		}
	};
	//名称
	public abstract String getName();
	//称谓
	public abstract String getTitle();
	//值
	public abstract int getValue();
}
