// namespace initialize
//  ASTO .ITC EP   hetao138@gmail.com
//  structures based on Jquery 1.6

(function(){
		
		/**
		 ctner 容器
		 spd 切换速度
		 intval 切换间隔
		 toggleImg 图片列
		 toggleBtn 按钮列
		**/
		var astslide = function(config){
			astslide = this;
			config = config || {};
			var conter = config.conter || "#zz91slide",
				conterBtn = config.conterBtn ||".toggleBtn",
				spd = config.spd || 300,
				intval = config.intval || 1000,
				toggleImg = config.toggleImg||".toggleImg>a",
				toggleBtn = config.toggleBtn||".toggleBtn>a";
				nowP = config.nowP || 0;
			
			//initilzation  turnBtn
			creatBtn($(toggleImg).length,conterBtn);
			
			var pic=$(conter+ " " + toggleImg),
				turnBt=$(conter + " " + toggleBtn);
			
			pic.eq(0).css({"display":"block"});
			
			var os = function(){
				nowP=nowP>(pic.length-2)?0:nowP=nowP+1;
				if($(toggleImg).length>1){
					toggleBtnF(nowP);
					toggleImgF(nowP);
				}
			};
			
			var t=setInterval(os,intval);
			
			//检测鼠标位置
			$(conter).bind({
				mouseover:function(){clearInterval(t);},
				mouseout:function(){t=setInterval(os,intval);}
			});	
			
			//切换
			turnBt.click(function(event){
					event.preventDefault();
					nowP=$(this).index();		
					toggleBtnF(nowP);
					toggleImgF(nowP);
			})
			
			//toggle images
			function toggleImgF(i){pic.fadeOut(spd).eq(i).fadeIn(spd);}
			
			//toggle button
			function toggleBtnF(i){turnBt.removeClass("selected").eq(i).addClass("selected");}
			
			//add turn btn
			function creatBtn(leg,contBtn){
				leg = leg || 0;
				var b = document.createElement('a');
				if(leg > 1) $(contBtn).append("<a class='selected' href='javascript:void(0)'>1</a>");
				for(var i = 2; i <= leg; i++){		
						$(contBtn).append("<a href='javascript:void(0)'>"+i+"</a>");
				}
			}
		}




		window['hb'] = {};
   		
		
		
		//slide
		window['hb']['astslide']=astslide;
		
		$(document).ready(function(e) {
           new hb.astslide(
					config={
						conter:"#zz91slide",
						spd:500,
						intval:3000
					}
				);
        });
		
		
	})();
	
	
	
