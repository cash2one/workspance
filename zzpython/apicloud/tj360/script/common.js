//var serverUrl = 'http://192.168.80.52:3000';//xyb本地

//var serverUrl = 'http://117.78.3.9:3000';//华为云试用

var serverUrl = 'http://125.94.215.156';//生产线
	
function subStr(str){
	var width = window.innerWidth;
	var num = (width - 104)/14 * 2;
	if(str.length > num){
		return str.substring(0, (num-2)) + '...';
	}else{
		return str;
	}
}

function subStr2(str){
	var width = window.innerWidth;
	var num = (width - 102 - 48)/14 * 2;
	if(str.length > num){
		return str.substring(0, (num-2)) + '...';
	}else{
		return str;
	}
}
	
function valid(){
	if(!$api.getStorage('logined')){	
		api.openWin({
			name:'login',
			url:'login.html'
		});
		return true;
	}
	return false;
}

function convertDate(str){
	if(str){
		var year = str.substring(0, 4);
		var month = str.substring(5, 7) - 1;
		var day = str.substring(8, 10);
		var hours = str.substring(11, 13);
		var minutes = str.substring(14, 16);
		var seconds = str.substring(17, 19);
		var ms = str.substring(20, 23);

		var worddate = Date.UTC(year, month, day, hours, minutes, seconds, ms);
		var actualDate = new Date();
		actualDate.setTime(worddate);

		var aYear = actualDate.getFullYear();
		var aMonth = actualDate.getMonth() + 1;
		aMonth = (aMonth.toString().length == 1 ? '0' + aMonth : aMonth);
		var aDate = actualDate.getDate();
		aDate = (aDate.toString().length == 1 ? '0' + aDate : aDate);
		var aHours = actualDate.getHours();
		aHours = (aHours.toString().length == 1 ? '0' + aHours : aHours);
		var aMin = actualDate.getMinutes();
		aMin = (aMin.toString().length == 1 ? '0' + aMin : aMin);
		return (aYear + '.' + aMonth + '.' + aDate + '\t' + aHours + ':' + aMin);
	}
}

function convertDate2(str){
	if(str){
		var year = str.substring(0, 4);
		var month = str.substring(5, 7);
		var date = str.substring(8, 10);
		return year + '.' + month + '.' + date;
	}
}

function convertDate3(str){
	if(str){
		var m = str.slice(-2);
		var length = str.length;
		if( m == 'PM' ){
			return '下午' + str.slice(0, length - 2);
		}else{
			return '上午' + str.slice(0, length - 2);
		}
	}
}

function hasClsRemoveIt(el, cls){
	if($api.hasCls(el, cls)){
		$api.removeCls(el, cls);
	}
}

function hasNoClsAddIt(el, cls){
	if(!$api.hasCls(el, cls)){
		$api.addCls(el, cls);
	}
}