/**
 *
 *gender:性别;0-女,1-男
 *marriageStatus:婚姻状况;0-未婚,1-已婚
 *education:教育情况;1-初中,2-高中,3-大专,4-本科,5-研究生
 *fertilityStatus:生育情况;0-未育,1-已育 
 *accommodation:住宿情况;0-宿舍,1-租房,2-其它
 *insurance:保险情况;0-新型农村合作医疗保险;1-城镇医疗保险;2-商业保险;3-其它
 * 
 **/

/*constructed function*/
function Convert(value){
	this.value = value;
}

Convert.prototype = {
	gender:function(gender){
		if(gender=='0'){
			return '女';
		}else if(gender=='1'){
			return '男';
		}else{
			return '-';
		}
	},
	
	marriageStatus:function(status){
		if(status=='0'){
			return '未婚';
		}else if(status=='1'){
			return '已婚';
		}else{
			return '-';
		}
	},
	
	education:function(edu){
		if(edu=='1'){
			return '初中';
		}else if(edu=='2'){
			return '高中';
		}else if(edu=='3'){
			return '大专';
		}else if(edu=='4'){
			return '本科';
		}else if(edu=='5'){
			return '研究生';
		}else{
			return '-';
		}
	},
	
	fertilityStatus:function(status){
		if(status=='0'){
			return '未育';
		}else if(status=='1'){
			return '已育';
		}else{
			return '-';
		}
	},
	
	accommodation:function(status){
		if(status=='0'){
			return '宿舍';
		}else if(status=='1'){
			return '租房';
		}else if(status=='2'){
			return '其它';
		}else{
			return '-';
		}
	},
	
	insurance:function(status){
		if(status=='0'){
			return '新型农村合作医疗保险';
		}else if(status=='1'){
			return '城镇医疗保险';
		}else if(status=='2'){
			return '商业保险';
		}else if(status=='2'){
			return '其它';
		}else{
			return '-';
		}
	}
};