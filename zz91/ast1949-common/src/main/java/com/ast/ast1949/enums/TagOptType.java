package com.ast.ast1949.enums;

/**
 * 标签操作类型
 * 
 * @author liuwb
 * 
 */
public enum TagOptType {
	CREATE {
		// 200200010001 '新建标签'
		@Override
		public String getCode() {
			return "200200010001";
		}

		@Override
		public String getLabel() {
			return "新建标签";
		}
	},
	UPDATE {
		//200200010002 '更新标签'
		@Override
		public String getCode() {
			return "200200010002";
		}

		@Override
		public String getLabel() {
			return "更新标签";
		}
	},
	DELETE {
		//200200010003 '删除标签'
		@Override
		public String getCode() {
			return "200200010003";
		}

		@Override
		public String getLabel() {
			return "删除标签";
		}
	},
	CLICK {
		//200200010004  '点击标签'
		@Override
		public String getCode() {
			return "200200010004";
		}

		@Override
		public String getLabel() {
			return "点击标签";
		}
	},
	SEARCH {
		//200200010005 '搜索标签'
		@Override
		public String getCode() {
			return "200200010005";
		}

		@Override
		public String getLabel() {
			return "搜索标签";
		}
	},
	RELATE {
		//200200010006 '关联标签'
		@Override
		public String getCode() {
			return "200200010006";
		}

		@Override
		public String getLabel() {
			return "关联标签";
		}
	},
	DEL_RELATION {
		//200200010007 '删除标签关联'
		@Override
		public String getCode() {
			return "200200010007";
		}

		@Override
		public String getLabel() {
			return "删除标签关联";
		}
	};
	public abstract String getLabel();

	public abstract String getCode();

}
