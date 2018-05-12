package cn.itcast.bean.user;
/**
 * 性别
 * @author 郭子灵
 *
 */
public enum Gender {
	MAN{
		public String getName() { return "男"; }
	},
	WOMEN{
		public String getName() { return "女"; }
	};
	public abstract String getName();
}
