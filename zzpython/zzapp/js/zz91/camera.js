//图片上传
var server = zzweburl+"/tradeimgupload.html";
var files = [];
var index = 1;
 
// 拍照添加文件
function appendByCamera() {
		plus.camera.getCamera().captureImage(function(p) {
			upload(p);
		});
	}
	// 从相册添加文件

function appendByGallery() {
		plus.gallery.pick(function(p) {
			upload(p)
		});
	}
	// 产生一个随机数

function getRandid() {
	//alert(document.getElementById("randid").value)
	return document.getElementById("randid").value;
	//return Math.floor(Math.random() * 100000000 + 10000000).toString();
}
//发布供求弹窗选择
function getselectvalue(obj){
	var selectname=obj.parentNode.title;
	document.getElementById(selectname).value=obj.title;
	closeoverlay();
}
function getpricevalue(obj){
	var selectname=obj.parentNode.title;
	var pricevalue=obj.parentNode.childNodes[1].value;
	document.getElementById(selectname).value=pricevalue;
	closeoverlay();
}
