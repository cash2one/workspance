package com.ast.ast1949.enums;

public enum OperationLogLevel {
	DEBUG {
		public String getName() {
			return "调试日志";
		}

		public String getValue() {
			return "debug";
		}

		public int getLevel() {
			return 1;
		}
	},
	INFO {
		public String getName() {
			return "消息日志";
		}

		public String getValue() {
			return "info";
		}

		public int getLevel() {
			return 2;
		}
	},
	WARN {
		public String getName() {
			return "警告日志";
		}

		public String getValue() {
			return "warn";
		}

		public int getLevel() {
			return 3;
		}
	},
	ERROR {
		public String getName() {
			return "错误日志";
		}

		public String getValue() {
			return "error";
		}

		public int getLevel() {
			return 4;
		}
	};
	public abstract String getName();

	public abstract String getValue();

	public abstract int getLevel();
}
