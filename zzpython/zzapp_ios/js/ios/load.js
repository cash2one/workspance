
// 空函数
function shield() {
	return false;
}
document.addEventListener('touchstart',shield,false);//取消浏览器的所有事件，使得active的样式在手机上正常生效
document.oncontextmenu=shield;//屏蔽选择函数