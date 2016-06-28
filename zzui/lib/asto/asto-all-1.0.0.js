//regist name space

Asto={};
/**
 * Example:
 * Asto.register("Asto.util");
 * **/
Asto.register= function(fname){
	var nArray = fname.split('.');
	var tfn = '';
	var feval= '';
	
	for(var i= 0; i< nArray.length;i++){
		if(i!=0){tfn += '.';}
		tfn += nArray[i];
		feval += "if (typeof("+tfn+") == 'undefined'){" + tfn + "={};}";
	}
	if(feval!=''){eval(feval)}
}

/**
 * Example:
 * Asto.ns("Asto.util");
 * **/
Asto.ns=function(fname){
	Asto.register(fname);
}

/**
 * Regist util
 * */
Asto.ns("Asto.util");

Asto.LIB_PATH="http://img0.zz91.com/lib";
Asto.JQUERY_EVENT_MONIT=false;
Asto.DEBUG=false;
Asto.SESSION_FLAG="sessionstatus";
Asto.MINI_LOGIN_URL="http://china.zz91.com/miniLogin.htm";
Asto.DEST_URL="";
Asto.EmptyFn=function(){};

Asto.util.Callback=new function(){
	this.submit=Asto.EmptyFn;
	
	this.setSubmitCallback=function(fn){
		this.submit=fn;
	}
};

/**
 * load script util
 * */
Asto.util.Script=new function(){
	
	/**
	 * is script file exists
	 * false:not exists
	 * true:exists
	 * */
//	this.isExist=function(url, filetype){
//		var scripts=document.getElementsByTagName('script');
//		
//		if(filetype=="css"){
//			scripts=document.getElementsByTagName('link');
//		}
//		
//		for(var i=(scripts.length-1);i>=0;i--){
//			var scriptsUrl="";
//			if(filetype=="css"){
//				scriptsUrl=scripts.href;
//			}else{
//				scriptsUrl=scripts.src;
//			}
//			if(scriptsUrl=url){
//				return true;
//			}
//		}
//		return false;
//	}
	
	this.loadJs=function(url, callback){
//		if(this.isExist(url,"js")){
//			if(callback){
//				callback();
//			}
//			return ;
//		}

		var script = document.createElement('script');
		script.type = 'text/javascript';
		if (callback){
			script.onload = script.onreadystatechange = function() {
				if (script.readyState && script.readyState != 'loaded' && script.readyState != 'complete') {
					return;
				}
				script.onreadystatechange = script.onload = null;
				callback();
			};
		}
		
		script.src = url;
		document.getElementsByTagName('head')[0].appendChild(script);
	}
	
	this.loadCss=function(url,callback){
//		if(this.isExist(url,"css")){
//			if(callback){
//				callback();
//			}
//			return ;
//		}
		var script = document.createElement('link');
		script.type = 'text/css';
		if (callback){
			script.onload = script.onreadystatechange = function() {
				if (script.readyState && script.readyState != 'loaded' && script.readyState != 'complete') {
					return;
				}
				script.onreadystatechange = script.onload = null;
				callback();
			};
		}
		script.href = url;
		script.rel="stylesheet";
		document.getElementsByTagName('head')[0].appendChild(script);
	}
	
}

/**
 * 系统消息提示工具
 * 示例：
 * 显示消息
 * Asto.util.Message.show({
 * 		msg:"message text",
 * 		msgType:Asto.util.Message.ERROR,
 * 		bar:[{text:Asto.util.Message.BAR_CLOSE,handler:function(){
 * 			Asto.util.Message.clear();
 * 		}}],
 * 		target:"#targetid",
 * 		autoClose:3000
 * });
 * 
 * 配置：
 * msg:要显示的消息，必填
 * msgType:显示消息的类型，有三种INFO,WARN,ERROR, error会显示成红色文字
 * bar:消息后面的工具按钮，默认有关闭按钮，设置成[]则无任何按钮
 * target:将显示到指定element
 * autoClose:自动关闭时间，不设置则不自动关闭，单位毫秒
 * 
 * 清除消息
 * Asto.util.Message.clear(time);
 * time:不设置或设置成0则立即清除消息
 * 
 * */
Asto.util.Message=new function(){
	
	this.CONTEXT={
		loading:"正在加载...",
		server_error:"连接服务器时发生错误.请再试一次。",
		session_timeout:"您没有登录或长时间没有操作，请重新登录。"
	};
	
	this.getMsg=function(key){
		return this.CONTEXT[key];
	}
	
	this.BAR_CLOSE="关闭";
	this.BAR_RETRY="重试";
	this.BAR_UNDO="撤消";
	this.BAR_REFRESH="刷新";
	
	this.INFO="black";
	this.WARN="black";
	this.ERROR="red";
	
	this.msg="";
	this.isCompleteClear=false;
	
	this.msgBody=null;
	
	this.show=function(cfg){
		cfg=cfg||{};
		var top=cfg.top||"0px";
		var left=cfg.left||"";
		var target=cfg.target||"body";
		
		var msg=cfg.msg||"";
		var msgType=cfg.msgType||this.INFO;
		
		var _this=this;
		var bar=cfg.bar||[{text:this.BAR_CLOSE,handler:function(){
			_this.clear();
		}}];
		
		this.msg=msg;
		this.isCompleteClear=cfg.isCompleteClear||false;
		
		//自动关闭选项，默认0，不自动关闭
		var autoClose=cfg.autoClose||0;
		
		this.buildMsg(msg, top, left, target, msgType, bar);
		
		if(autoClose>0){
			this.clear(autoClose);
		}
	}
	
	this.buildMsg=function(msg, t, l, target, mt, bar){
		if(this.msgBody!=null){
			this.msgBody.remove();
			this.msgBody=null;
		}
		
		this.msgBody=jQuery("<div ></div>");
		this.msgBody.addClass("message_box");
		
		if(l==""){
			l=(($(document).width()/2 )-msg.length*6)+'px';
		}
		
		this.msgBody.css({
			"margin-top":t,
			"margin-left":l,
			"color":mt,
			"font-size":"12px"
		});
		
		jQuery("<b>"+msg+"</b>").appendTo(this.msgBody);
		var _this=this;
		jQuery(bar).each(function(idx,obj){
			var b=_this.buildBar(obj);
			if(b != null){
				b.appendTo(_this.msgBody);
			}
		});

//		message.msgBody.appendTo(jQuery(target))
//		jQuery(target).append("<div style='clear:both;'></div>");
//		jQuery(target).append(message.msgBody);
		jQuery(target).prepend(this.msgBody);
	}
	
	//创建单个工具栏
	this.buildBar=function(cfg){
		cfg=cfg||{};
		var text=cfg.text||"";
		if(text==""){
			return null;
		}
		var handler=cfg.handler||function(){};
		
		var obj=jQuery("<a href='javascript:void(0);' >"+text+"</a> ");
		obj.css({
			"font-size":"12px",
			"font-weight":"bold",
			"margin-left":"10px",
			"cursor":"pointer",
			"color":"blue"
		});
		obj.click(handler);
		
		return obj;
	}
	
	//清除消息框,不带时间则立即清除,时间单位：毫秒
	this.clear=function(time){
		var _this=this;
		time=time||0;
		if(time>0){
			setTimeout(function(){
				if(_this.msgBody!=null){
					_this.msgBody.remove();
				}
			},time);
		}else{
			if(_this.msgBody!=null){
				_this.msgBody.remove();
			}
		}
	}
}

Asto.util.EventMonitor=function(){
	jQuery(document).ajaxSend(function(e,XHR,options){
		Asto.util.Message.show({
			msg:Asto.util.Message.getMsg("loading"),
			isCompleteClear:true,
			bar:[]
		});
	}).ajaxError(function(e,XHR,settings,thrownError){
		Asto.util.Message.show({
			autoClose:10000,
			msg:XHR.status+":"+Asto.util.Message.getMsg("server_error")
		});
	}).ajaxComplete(function(e, XHR, options){
		var str=XHR.getAllResponseHeaders();
		if(str.indexOf(Asto.SESSION_FLAG)>=0){
			//提示消息
			Asto.util.Message.show({
				msg:Asto.util.Message.getMsg("session_timeout"),
				autoClose:3000
			});
			
			var loginbox=new Asto.util.PopupBox({
				url:Asto.MINI_LOGIN_URL,
				destUrl:Asto.DEST_URL,
				width:500,
				height:420,
				callbackFn:function(){
					loginbox.close();
				}
			});
			
			loginbox.show();
		}else{
			if(XHR.status==200 && Asto.util.Message.isCompleteClear){
				Asto.util.Message.clear();
			}
		}
	});
}

/**
 * pop-up box
 * Example:
 * var loginbox=new Asto.util.PopupBox({
 * 		url:Asto.MINI_LOGIN_URL,
 * 		destUrl:Asto.DEST_URL,
 * 		callbackFn:function(success, data){
 * 			loginbox.close();
 * 		},
 * 		width:500,
 * 		height:420
 * });
 * 
 * loginbox.show();
 * */
Asto.util.PopupBox=function(config){
	config=config||{};
	
	this.url=config.url||"";
	this.destUrl=config.destUrl||"";
	this.html=config.html||"";
	
	if(config.callbackFn!=null && typeof config.callbackFn !="undefined"){
		Asto.util.Callback.setSubmitCallback(config.callbackFn);
	}
	
	if(this.url!=""){
		var iframeurl=this.url;
		if(this.destUrl!=""){
			if(this.url.indexOf("?")==-1){
				iframeurl=this.url+"?destUrl="+this.destUrl
			}else{
				iframeurl=this.url+"&destUrl="+this.destUrl
			}
		}
		this.body=jQuery("<iframe id='popupiframe' frameborder = 'no' scrolling = 'auto' style = 'width:100%;height:100%' src ='"+iframeurl+"' />");
		if(this.body!=null && typeof(jQuery("#popupiframe")) == "undefined"){
			jQuery("body").append(this.body);
		}
	}else {
		this.body=jQuery("<div >"+this.html+"</div>");
		if(this.body!=null && typeof(jQuery("#popupdiv")) == "undefined"){
			jQuery("body").append(this.body);
		}
	}
	
	this.show=function(iframeUrl){
		if(this.url!=""){
			var width=config.width||500;
			var height=config.height||420;
			if(typeof iframeUrl!="undefined" && iframeUrl!=null && iframeUrl!=""){
				this.body.attr("src", iframeUrl);
			}
			this.body.dialog(config).width(width-30).height(height-30)
		}else{
			this.body.dialog(config);
		}
	}
	
	this.close=function(){
		this.body.dialog("close");
	}
	
	this.destory=function(){
		this.body.dialog("destroy");
	}
	
	this.buildUrl=function(){
		var iframeurl="";
		if(this.destUrl!=""){
			if(this.url.indexOf("?")==-1){
				iframeurl=this.url+"?destUrl="+this.destUrl
			}else{
				iframeurl=this.url+"&destUrl="+this.destUrl
			}
		}
		return iframeurl;
	}
}

Asto.util.ValidationCode=function(config){
	config=config||{};
	
	this.width=config.width||0;
	this.height=config.height||0;
	
	this.targetImg=config.targetImg||"";
	this.targetVK=config.targetVK||"";
	this.kvkey=config.kvkey||"ts";
	
	this.url=config.url||"";
	
	var _this=this;
	
	if(this.width>0 && this.height>0){
		jQuery(_this.targetImg).css({
			width:_this.width+"px",
			height:_this.height+"px"
		});
	}
	
	this.randomTs=function(){
		return new Date().getTime();
	}
	
	this.refresh=function(){
		if(this.url==""){
			return false;
		}
		var kv=this.randomTs();
		jQuery(this.targetVK).val(kv);
		jQuery(this.targetImg).attr("src",this.url+"?"+this.kvkey+"="+kv);
	}
}


/**
 * 通用地址联动加载
 * 示例：
 * var selector=new Asto.util.Selector({
 * 		url:"/path/to/main/seletor/resources",
 * 		assistUrl:"/path/to/assist/selector/resources",
 * 		changeCallback:function(){
 * 			alert("alert while selector change")
 * 		},
 * 		selectors:["#s1","#s2"],
 *		assistSelects:["#assist"],
 * 		field:{code:"code",label:"name"}
 * });
 * 
 * 初始化联动菜单
 * selector.init({
 * 		rootCode:"1000",
 * 		codeLength:4,
 * 		initCode:"100010001001",
 * 		initAssistCode:""
 * });
 * 
 * alert(selector.getValue());
 * 
 * */
Asto.util.Selector=function(config){
	config = config||{};
    this.url=config.url||"/category/areaChild.htm";
    this.assistUrl=config.assistUrl||"/category/areaChild.htm";
    this.changeCallback=config.changeCallback||function(){};
    this.field=config.field||{code:"code",label:"label"};
    this.selectors=config.selects||[];
    this.assistSelectors = config.assistSelects||[];
    
    var selinstance = this;
    
    this.init =function(cfg){
        cfg=cfg||{};
        var rootCode=cfg.rootCode||"";
        var codeLength=cfg.codeLength||4;
        var initCode=cfg.initCode||"";
        var initAssistCode=cfg.initAssistCode||"";
        
        var selectors=selinstance.selectors;
        var assistSelectors=selinstance.assistSelectors;
        
        //初始化辅助类别（如果有）
        if(assistSelectors.length>0 && initAssistCode!=""){
            selinstance.fillAssistOption(initCode, initAssistCode, assistSelectors[0]);
        }
        jQuery(selectors).each(function(idx,e){
            //初始化选择项
            if(initCode.length>rootCode.length){
                if((rootCode.length+((idx+1)*codeLength))<=initCode.length){
                    var p=initCode.substring(0, rootCode.length+(idx*codeLength));
                    var c=initCode.substring(0, rootCode.length+(idx+1)*codeLength);
//                  alert(p+"   "+c)
                    selinstance.fillOption(p,c,e);
                }else{
                    if(initCode.length+codeLength == rootCode.length+((idx+1)*codeLength)){
                        selinstance.fillOption(initCode, "", e);
                    }
                }
            }else{
                if(idx==0){
                    selinstance.fillOption(rootCode,"",e);
                }
            }
            
            //为选择器绑定事件
            jQuery(e).change(function(obj){
                //如果选中值不为空，则将选中值放入指定隐藏域
                if(assistSelectors.length>0){
                    jQuery(assistSelectors[0]).val("");
                    var ahtml= jQuery(assistSelectors[0]).find('option:selected').html();
                    jQuery(assistSelectors[0]).empty();
                    jQuery(assistSelectors[0]).append("<option value=''>"+ahtml+"</option>");
                    if(jQuery(e).val()!="" && jQuery(e).val()!=null){
                        selinstance.fillAssistOption(jQuery(e).val(), "", assistSelectors[0]);
                    }
                }
                
                if(typeof(selectors[idx+1])!="undefined"){
	                //清除选择项后面的所有选择值
	                for(var ii=idx;ii<selectors.length;ii++){
	                    jQuery(selectors[ii+1]).val("");
	                    var html= jQuery(selectors[ii+1]).find('option:selected').html();
	                    jQuery(selectors[ii+1]).empty();
	                    jQuery(selectors[ii+1]).append("<option value=''>"+html+"</option>");
	                }
	                
	                //ajax获取子类别
	                if(jQuery(e).val()!="" && jQuery(e).val()!=null){
	                    selinstance.fillOption(jQuery(e).val(),"",selectors[idx+1]);
	                }
                }
                
                selinstance.changeCallback(selinstance, idx);
            });
        });
    }
    
    this.fillOption = function(pc, currentCode, targetElement){
        jQuery.ajax({
            url:selinstance.url+"?parentCode="+pc,
            type:"get",
            cache:false,
            dataType:"json",
//            data:_data,
            success:function(req){
                jQuery(req).each(function(idx,e){
			var selectFlag = "";
                        if(currentCode == e[selinstance.field.code]) {
                                selectFlag = " selected ";
                        }
                        jQuery(targetElement).append("<option value='"+e[selinstance.field.code]+"'"+selectFlag+">"+e[selinstance.field.label]+"</option>");
                });
            },
            error:function(e){
            }
        });
    }
    
    
    this.fillAssistOption = function(mainCode, currentCode, targetElement){
        jQuery.ajax({
            url:selinstance.assistUrl+"?mainCode="+mainCode,
            type:"POST",
            cache:false,
            dataType:"json",
//            data:{"mainCode":mainCode},
            success:function(req){
                jQuery(req).each(function(idx,e){
                    jQuery(targetElement).append("<option value='"+e[selinstance.field.code]+"'>"+e[selinstance.field.label]+"</option>");
                });
                jQuery(targetElement).val(currentCode);
            },
            error:function(e){
            }
        });
    }
    
    //得到联动菜单的最终选中值
    this.getValue=function(){
    	var v="";
    	jQuery(selinstance.selectors).each(function(idx,e){
    		if(jQuery(e).val()!=null && jQuery(e).val()!=""){
	    		v=jQuery(e).val();
    		}
    	});
    	return v;
    }
    
    //按顺利得到所有选中项的值
    this.getValues=function(){
    	var v=new Array();
    	jQuery(selinstance.selectors).each(function(idx,e){
    		if(jQuery(e).val()!=null && jQuery(e).val()!=""){
	    		v.push(jQuery(e).val());
    		}
    	});
    	return v;
    }
    
    //按顺序得到全部选中项的显示值
    this.getTexts=function(){
    	var v=new Array();
    	jQuery(selinstance.selectors).each(function(idx,e){
    		if(jQuery(e).val()!=null && jQuery(e).val()!=""){
	    		v.push(jQuery(e).find("option:selected").html());
    		}
    	});
    	return v;
    }
    
    //得到联动辅助菜单的选中值
    this.getAssistValue=function(){
    	if(selinstance.assistSelectors.length>0){
    		return jQuery(selinstance.assistSelectors[0]).val();
    	}
    }
    
    //TODO 未完成
    this.isLeaf=function(url,code, callback){
    	jQuery.ajax({
            url:selinstance.assistUrl+"?mainCode="+mainCode,
            type:"POST",
            cache:false,
            dataType:"json",
//            data:{"mainCode":mainCode},
            success:callback,
            error:function(e){
            }
        });
    }

};

Asto.util.ZZ91=new function(){
	this.login=function(config){
		if(config==null){
			config={};
		}
		
//		username,password,cookieMaxAge,url,randcode,randcodeKey,contextpath,fn
		
		var _username=config.username||"";
		var _password=config.password||"";
		var _cookieMaxAge=config.cookieMaxAge||"";
		var _url=config.url||"";
		var _randcode=config.randcode||"";
		var _randcodeKey=config.randcodeKey||"";
		var _contextpath=config.contextpath||Context.ROOT;
		var _success=config.success||function(response){
			if(response.success){
				window.location.href=response.data;
			}else{
				alert(response.data);
			}
		};
		
		jQuery.ajax({
			url:_contextpath+"/checkuser.htm",
			type:"POST",
			cache:false,
			dataType:"json",
			data:{username:_username,password:_password,cookieMaxAge:_cookieMaxAge,url:_url,randCode:_randcode,randCodeKey:_randcodeKey},
			success:_success,
			error:function(e){
				alert("发生了一点错误，请过一会再试!")
			}
		});
	};

}