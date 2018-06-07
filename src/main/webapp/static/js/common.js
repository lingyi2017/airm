(function($) {
	accipiter=window.accipiter=$(document);//定义全局变量
	$.fn.extend({//插件扩展
		/**
		 * 获取根目录
		 * @returns
		 */
		 getRootPath:function(){
			 
			//获取当前网址
			var curPath=window.document.location.href;
			//获取主机地址之后的目录
			var pathName=window.document.location.pathname;
			var pos=curPath.indexOf(pathName);
			//获取主机地址，
			var localhostPath=curPath.substring(0,pos);
			var reg=/.*(accipiter2).*/;
			var result=reg.test(curPath);
			var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);//获取带"/"的项目名
			if(result==true){
				return localhostPath+"/accipiter2";
			}else{				return localhostPath;
			}
		},
        getProjectName:function() {
            var pathName = window.document.location.pathname;
            var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);//获取带"/"的项目名
            if(projectName=="/accipiter2"){
            	return projectName;
            }else{
            	return "";
            }
        },
		getIp:function(){//获取ip
			return accipiter.getLocaleParam("ip");
		},
		getPage:function(){//获取页码
			return accipiter.getLocaleParam("page");
		},
		/**
		 * 获取地址栏传入的参数
		 * parm : 传入需要得到的参数的值
		 * @return
		 */
		getLocaleParam:function(parm){
			var val="";
			var url=window.location.search;
			if(url.indexOf("?")!=-1){
				var str=url.slice(1);
				var strs=str.split("&");
				for(var i=0;i<strs.length;i++){
					if([strs[i].split("=")[0]]==parm){
						val=decodeURI(strs[i].split("=")[1]);
					} 
				}
			}
			return val;
		},
		/**
		 * 数字微调框
		 * 参数：
		 * 	sel:选择符
		 *  min:最小限制
		 *  max:最大限制
		 *  loop:间隔
		 * @return
		 */
		  numberPicker:function(sel,min,max,loop){
			  if(sel==null){throw new Error("参数错误");}
			  if(loop==null){loop=1;}
			  if(min==null){min=0;}
			  if(max==null){max=100;}
			var up=$(sel).siblings().eq(0).find(".up");
			var down=$(sel).siblings().eq(0).find(".down");
			up.click(function(){
				var v=$(sel).find("input").val(); 
				if(v!="" && v!=undefined && v!=null && !isNaN(v)){
					v=Number(v)+loop;
					if(v>=max){
						v=max;
					}
				}
				$(sel).find("input").val(v);
			});
			down.click(function(){
				var v=$(sel).find("input").val(); 
				if(v!="" && v!=undefined && v!=null && !isNaN(v)){
					v=Number(v)-loop;
					if(v<=min){
						v=min;
					}
				}
				$(sel).find("input").val(v);
			});
		},
		/**
		 * post方式请求数据（Json）
		 * 参数：url:请求地址，
		 * 		param:所传参数
         * 	    alwaysLoading:总是显示加载状态
         * 	    faultHandler:错误处理方法
         * 	    async:是否是异步模型 默认为异步
         **/
		  doPost:function(url,param,alwaysLoading,faultHandler, async){
              if(!alwaysLoading)
                  alwaysLoading = false; //设置参数alwaysLoading默认值为false
              async = (undefined == async ? true :async);
			  var reg=alwaysLoading ? alwaysLoading : /get|find/.test(url);   //alwaysLoading时无需正则计算
			  if(alwaysLoading || reg){accipiter.loadding(true);}
				var jq=$.ajax({
						type:'post',
						data:JSON.stringify(param),
                        async:async,
						url:url,
						contentType:"application/json; charset=UTF-8"/*,
						dataType:'json'*/});

				jq.fail(function(jqXHR, textStatus, errorThrown){
                    if(faultHandler) {
                        faultHandler(jqXHR, textStatus, errorThrown);
                    } else {
                        accipiter.back({tip:accipiter.getLang("t2"),type:1});
                    }
				});
				jq.always(function(){
					if(alwaysLoading || reg){accipiter.loadding(false);}
				});
				return jq;
		},
		/**
		 * ip地址正则验证
		 * 	ip:需要验证的ip
		 */
		ipReg:function(ip){
			var reg=/^((\d{1}|[1-9][0-9]|2[0-5][0-5]|1\d\d)\.){3}(\d{1}|[1-9][0-9]|2[0-5][0-5]|1\d\d)$/;
			var t= reg.test(ip);
			return t;
		},
		/**
		 * 获取值，如果是默认值，返回''
		 * @param sel：选择符
		 * @return
		 */
		getVal:function (sel){
			var v=$(sel).val();
			return v==sel.defaultValue?'':v;
		},
		/**
		 * 下拉列表框触发事件
		 */
		selChange:function (){
			$(".sel select").change(function(obj){
				var target=obj.target;
				var o=$(this);
				$.each(target,function(i){
					var selected=target[i].selected;
					if(selected){
						var text=target[i].text;
						$(o).parent().find(".title").text(text);
					}
				});
			});	
		},
		/**
		 * 只能输入数字正则表达
		 * val:需要验证的值
		 */
		numReg:function(val){
			var num=/^(\d)*$/g;
			return num.test(val);
		},
		/**
		 * 设置后返回
		 */
		setBack:function(status,oid,value,callback){
			if(status=="000000"){
				if(oid=="0.0.0.1"){
					alert(value);
				}else{
					accipiter.back({tip:accipiter.getLang("success")}); 
				}
				if(callback && (callback  instanceof Function)){
		              callback();
		        }
			}else{
				accipiter.back({tip:accipiter.getLang("fail"),type:1}); 
			}
		},
		/**
		 * checkbox全选
		 * sel:   例如： $("#vpnTable input[type=checkbox]")
		 */
		chk:function(sel){
			$(sel).eq(0).click(function(){
				sel.slice(1).prop("checked",this.checked);
			});
			$(sel).slice(1).click(function(){
				if($(sel).slice(1).filter(":checked").length==0){
					$(sel).eq(0).prop("checked",false);
				}else{
					$(sel).eq(0).prop("checked",true);
				}
			});
			$(sel).eq(0).prop("checked",false);
		},
		
		/**
		 * 分页
		 * @param pageNo:当前页码
		 * @param pageSize:每页显示的条数
		 * @param total :总共页数
		 * @param sel：选择符
		 * @param callback:调用的函数
		 * @return
		 */
		 doPage:function(pageNo,pageSize,total,sel){
			  $(sel).smartpaginator({
			  		 totalrecords: Number(total), //总的条数
			  		 recordsperpage: Number(pageSize), // 每页显示的记录条数
			  		 length: 10,  //上下第一和最后一页的显示依赖这个参数。当length的值大于totalrecords/recordsperpage时，不显示。
			  		 initval:Number(pageNo),
			  		 next: 'Next', 
			  		 prev: 'Prev', 
			  		 first: 'First', 
			  		 last: 'Last', 
			  		 theme: 'gray', 
			  		 controlsalways: false, 
			  		 onchange: function (page) {
			  			 if($("#page_index")){
			  				$("#page_index").val(page);
			  			 }
		          	 	changePage(page);//必须调用的函数
		      		}
		      });
		},
		/**
		 * 设置文本框默认值，及状态
		 * @param sel(元素选择符)
		 */
	    textState:function(sel){
			if($(sel).val()==this.defaultValue){
				$(sel).css({color:'#4D4D4D',fontStyle:'italic'});
			}
			$(sel).focus(function(){
				if($(sel).val()==this.defaultValue){
					$(sel).val('').css({color:'',fontStyle:'normal'});
				}else{
					$(sel).select();
				}
			});
			$(sel).blur(function(){
				if($(sel).val()==''){
					$(sel).val(this.defaultValue).css({color:'#4D4D4D',fontStyle:'italic'});
				}
			});
		},
		/**
		 * 文本框自动聚焦
		 * @param ints:文本框数组对象
		 * @param mode：号码类型。如PDT,MPT，其他等.  
		 */
		autoFocus:function(ints,mode,type){
				var len=ints.length;
				if(mode==ssi.PDT && ssi.pdt_mode==1 && type==ssi.USR){//目前浙江拨号方式
					if( $(ints[0]).is(":focus") && (accipiter.getVal(ints[0])).length==3){
						$(ints[0]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
					}
				}else{//单插入
					if(mode==ssi.PDT || (mode==ssi.MPT && Number(ssi.mpt_mode)==0) ){
						if($(ints[0]).is(":focus") && (accipiter.getVal(ints[0])).length==3){
							$(ints[0]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if($(ints[1]).is(":focus") && (accipiter.getVal(ints[1])).length==2){
							$(ints[1]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if(len==5){
							if($(ints[2]).is(":focus") && (accipiter.getVal(ints[2])).length==3){
								$(ints[2]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
							if($(ints[3]).is(":focus") && (accipiter.getVal(ints[3])).length==2){
								$(ints[3]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
						}
						
					}else if(mode==ssi.MPT && Number(ssi.mpt_mode)==1){
						if($(ints[0]).is(":focus") && (accipiter.getVal(ints[0])).length==3){
							$(ints[0]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if($(ints[1]).is(":focus") && (accipiter.getVal(ints[1])).length==4){
							$(ints[1]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
						}
						if(len==5){
							if($(ints[2]).is(":focus") && (accipiter.getVal(ints[2])).length==ssi.unlen){
								$(ints[2]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
							if($(ints[3]).is(":focus") && (accipiter.getVal(ints[3])).length==4){
								$(ints[3]).parent().nextAll(".ipt:not(:hidden):eq(0)").find("input").focus();
							}
						}
					}
				}
		},
		/** 
		 * js截取字符串，中英文
		 * @param str：需要截取的字符串 
		 * @param len: 需要截取的长度 
		 */  
		 cutstr:function(str,len) {   
		   var str_length = 0;  
		   var str_len = 0;  
		   str_cut = new String();  
		   str_len = str.length;  
		   for(var i = 0;i<str_len;i++) { 
		       var a = str.charAt(i);  
		        str_length++;  
		        if(decodeURI(a).length > 4)  { //中文字符的长度经编码之后大于4 
		        	str_length++;  
		         }  
		         str_cut = str_cut.concat(a);  
		         if(str_length>=len) {  
		        	 str_cut = str_cut.concat("..."); 
		        	 return str_cut;  
		         }  
		    }  
		    //如果给定字符串小于指定长度，则返回源字符串；  
		    if(str_length<len){  return  str;  } 
		},
		phoneRange:function(options){//号码范围
			var defaultOptions={
					mode:ssi.PDT,//PDT:0,MPT:1
					type:ssi.USR,//USR:0,GRP:1
					selector:"",//号码范围选择器（必填）
					emptyAddress:"",//隐藏空口地址Id（必填）
					errTip:"",
					btn:"",
					funcs:null,//新添加的函数
					callback_flag:0,//是否使用默认的函数（0：使用，1，不使用，调用funcs）
					flag:0,//0：查询，1：添加，
					selflag:0,//默认  0：只有pdt和mpt 1：有其他情况
					selopt:{title:"",value:"",callback:null}//添加的值
			};
			var options_=$.extend({},defaultOptions,options);
			var emptyAddress="";//空口地址值
			accipiter.phoneOptions(options_);
			var mode=options_.mode;
			var ints=$(options_.selector+" .ssi_input input");
				ints.keyup(function(){
					ints=$(options_.selector+" .ssi_input .ipt").filter(":visible").find("input");
					var selVal=$(options_.selector).find("select:first").val();
					switch (Number(selVal)) {
						case 1://pdt
							mode=ssi.PDT;
							break;
						case 2://mpt
						case 3:
							mode=ssi.MPT;
							break;
					}
					accipiter.autoFocus(ints,mode,options_.type);//自动聚焦
					var vals=[],totalEmpty=0;
					$.each(ints,function(index,value){
						vals[index]=accipiter.getVal(value);
						if(vals[index]!=""){totalEmpty++;}
					});
					var ai=[],start=[-1],end=[-1],err=0,
						inpLen=ints.length;
					//以下代码变动大（先分情况处理）
					switch(inpLen){
						case 2://单插入(区号-个号)
							start=accipiter.getssi(ints.slice(0,2),mode,options_.type);
							if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
								err=0;
								emptyAddress=start[2];
							}else{
								err=1;
							}
							break;
						case 3:
							if(ssi.pdt_mode==1 && options_.type==ssi.USR){//(多插入)目前浙江拨号方式
								start=accipiter.getssi(ints.slice(0,2),mode,options_.type);
								end=accipiter.getssi([ints[0],ints[2]],mode,options_.type);
								if(vals[2]=="" || (vals[2]!="" && vals[2]>=vals[1])){
									err=0;
									if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
										err=0;
										emptyAddress=start[2];
										if(vals[2]!=""){
											if(end[0]!=-1  && end[1]==options_.type && end[0]==mode){
												err=0;
												emptyAddress+="-"+end[2];
											}else{
												err=1;
											}
										}
									}else{err=1;}
								}else{
									err=1;
								}
							}else{//单插入
								start=accipiter.getssi(ints.slice(0,3),mode,options_.type);
								if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
									err=0;
									emptyAddress=start[2];
								}else{
									err=1;
								}
							}
							break;
						case 5://支持多插入
							start=accipiter.getssi(ints.slice(0,3),mode,options_.type);
							end=accipiter.getssi([ints[0],ints[3],ints[4]],mode,options_.type);
							if(start[0]!=-1  && start[1]==options_.type && start[0]==mode){
								err=0;
								emptyAddress=start[2];
								if(vals[3]!="" || vals[4]!=""){
									if(vals[3]>=vals[1] && vals[4]>=vals[2]){
										if(end[0]!=-1  && end[1]==options_.type && end[0]==mode){
											err=0;
											emptyAddress+="-"+end[2];
										}else{
											err=1;
										}
									}else{err=1;}
								}
							}else {
								err=1;
							}
							break;
					}
					if(totalEmpty==0){//当输入框全部为空
						err=2;
						if(options_.flag==0){//查询时
							if(options_.mode==0){
								emptyAddress="1048577-16777216";//pdt范围值
							}else if(options_.mode==1){
								emptyAddress="1-1048576";//mpt范围值
							}
						}else{
							emptyAddress="";
						}
					}
					if(options_["callback_flag"]==0){
						switch(err){
						case 1://错误时
							if($(options.emptyAddress).is("td")){
								$(options.emptyAddress).text("");
							}else if($(options.emptyAddress).is("input")){
								$(options.emptyAddress).val("");
							}
							$(options.errTip).text(accipiter.getLang("invalidno"));
							$(options.btn).hide();
							break;
						case 0://正确时
							if($(options.emptyAddress).is("td")){
								$(options.emptyAddress).text(emptyAddress);
							}else if($(options.emptyAddress).is("input")){
								$(options.emptyAddress).val(emptyAddress);
							}
							$(options.errTip).text("");
							$(options.btn).show();
							break;
						case 2://全部为空时
							if($(options.emptyAddress).is("td")){
								$(options.emptyAddress).text(emptyAddress);
							}else if($(options.emptyAddress).is("input")){
								$(options.emptyAddress).val(emptyAddress);
							}
							$(options.errTip).text("");
							if(options_.flag==0){//查询时
								$(options.btn).show();
							}else{
								$(options.btn).hide();
							}
						}
					}else{
						options_["funcs"].call(this,err,emptyAddress); //调用函数
					}
				});
			},
			getssi:function(sel,mode,type){
				var val=[],ai=[-1];
				$(sel).each(function(i){
					var v=accipiter.getVal(this);
					if(v!=''){val.push(v);}else{val.push(0);}
				});
				switch (mode) {
				case ssi.PDT:
					if(ssi.pdt_mode==1 && type==ssi.USR){
						ai=ssi.pdt_ai(val[0],val[1],null);
					}else{
						ai=ssi.pdt_ai(val[0],val[1],val[2]);
					}
					break;
				case ssi.MPT:
					ai=ssi.mpt_ai(val[0],val[1],val[2]);
					break;
				}
				return ai;
			},
			getType:function(mode,type){
				var str="";
				if(mode==ssi.UNKNW){
					str=accipiter.getLang("invaliduser");
				}else{
					if(mode==ssi.PDT){
						if(type==ssi.USR && ssi.pdt_mode==1){
							str=accipiter.getLang("zjdial");
						}else{
							str="PDT";
						}
					}else if(mode==ssi.MPT){
						str="MPT";
					}
				}
				return str;
			},
			phoneOptions:function(options){
				var html="",ini=ssi.mode[0];
				if(options["flag"]==0){//查询
					$(options.selector).find(".ssi_input").hide();
					html+="<option value=0>"+accipiter.getLang("all")+"</option>";
					ini=accipiter.getLang("all");
				}else if(options["flag"]==1){//插入
					$(options.selector).find(".ssi_input").show();
					if(ssi.pdt_mode==1 && options.type==ssi.USR){
						$(options.selector).find(".ssi_input .zj").hide();
					}
					if(ssi.pdt_mode==1 && options.type==ssi.USR){
						ini=ssi.mode[4];
					}
				}
				if(ssi.pdt_mode==1 && options.type==ssi.USR){
					 html+="<option value=1>"+ssi.mode[4]+"</option>";
				}else{
					 html+="<option value=1>"+ssi.mode[0]+"</option>";
				}
				if(ssi.mpt_mode==0){
					 html+="<option value=2>"+ssi.mode[1]+"</option>";
				}else if(ssi.mpt_mode==1){
					html+="<option value=3>"+ssi.mode[2]+"</option>";
				}
				if(options.selflag==1){
					html+="<option value="+options["selopt"]["value"]+">"+options["selopt"]["title"]+"</option>";
				}
				$(options.selector).find("select:first").append(html);
				if($.trim($(options.selector).find(".title:first").text())==""){
					$(options.selector).find(".title:first").text(ini);
				}
				$(options.selector+" .ssi_input input ").each(function(){
					$(this).val(this.defaultValue).css({color:'#4D4D4D',fontStyle:'italic'});
				});
				//初始化数据
				$(options.selector).find("select:first").change(function(){
					$(options.selector).find(".ssi_input input").each(function(){
						$(this).val(this.defaultValue).css({color:'#4D4D4D',fontStyle:'italic'});
					});
					var v=$(this).val(),emptyAddress="";
					switch (Number(v)) {
					case 0://全部
						$(options.selector).find(".ssi_input").hide();
						emptyAddress="";
						break;
					case 1:
						$(options.selector).find(".ssi_input").show();
						if(ssi.pdt_mode==1 && options.type==ssi.USR){
							$(options.selector).find(".ssi_input .zj").hide();
						}else{
							$(options.selector).find(".ssi_input .zj").show();
						}
						if(options.flag==0){
							emptyAddress="1048577-16777216";//pdt范围值
						}
						if(options.selflag==1){
							options["selopt"]["callback"].call(this,1);
						}	
						break;
					case 2:
					case 3:
						$(options.selector).find(".ssi_input").show();
						$(options.selector).find(".ssi_input .zj").show();
						if(options.flag==0){
							emptyAddress="1-1048576";//mpt范围值
						}
						if(options.selflag==1){
							options["selopt"]["callback"].call(this,1);
						}
						break;
					case 4://其他
						$(options.selector).find(".ssi_input").hide();
						emptyAddress="";
						if(options.selflag==1){
							options["selopt"]["callback"].call(this,0);
						}	
					}
					if($(options.emptyAddress).is("td")){
						$(options.emptyAddress).text(emptyAddress);
					}else if($(options.emptyAddress).is("input")){
						$(options.emptyAddress).val(emptyAddress);
					}
					$(options.errTip).text("");
					if(options.flag==1){
						$(options.btn).hide();
					}else{
						$(options.btn).show();
					}
				});
			},
			userAddress:function(v,type){//通过空口地址获取用户号码和用户类型
				var phone=ssi.ai_ssi(Number(v),type),addr=[];
				if(phone[0]!=ssi.UNKNW){
						if(ssi.pdt_mode==1 && type==ssi.USR && phone[0]==ssi.PDT){
							addr[0]=(phone[0]==ssi.UNKNW?accipiter.getLang("invalidno"):(phone[2]+'-'+phone[3]));
						}else{
							addr[0]=(phone[0]==ssi.UNKNW?accipiter.getLang("invalidno"):(phone[2]+'-'+phone[3]+'-'+phone[4]));
						}
						addr[1]=accipiter.getType(phone[0],type);
				}else{
					addr[0]=accipiter.getLang("invalidno");
					addr[1]=accipiter.getLang("invaliduser");
				}
				return addr;
			},
			back:function(options){//请求后返回提示
				var defaultOptions={
						tip:"",//提示内容
						funcs:null,
						flag:0,//0：使用默认的，1：创建新的
						type:0,//0:提示框，1：confirm框
						top:0,
						left:0,
						record:0,
						confirm_callback:null,//确定后的操作。当type结果为1，2时需要设置的
						cancel_callback:null//取消后的操作
				};
				var options_=$.extend({},defaultOptions,options);
				if(options_["flag"]==1){
					options_["funcs"].call(this); //调用函数(具体情况以后扩展)
				}else{
					var top=(options_["top"]==0?220:options_["top"]);
					var left=(options_["left"]==0?$("body").width()/2-$("body").offset().left-140:options_["left"]);
					if(options_["type"]==0){//简单提示
						var load='<div class="load_tip" style="z-index:100;width:280px;height:160px; border:1px solid #3E7FC3; background:#FFF; position:absolute; top:'+top+'px;left:'+left+'px;">' +
								'<p style="border-bottom:1px solid #3E7FC3; background:#78B8E4; height:25px; line-height: 25px; padding-left:5px; font-weight:bold;font-size:14px; color:#535252;">'+accipiter.getLang("t3")+'</p>' +
								'<div style=" text-align:center; line-height:130px; font-size:14px; color:#535252; " >' +
								''+options_["tip"]+''+
								'</div>'+
								'</div>';
						var shadow='<div class="shadow" style="position:fixed;width:100%; height:100%;filter:alpha(opacity=50);opacity:.5; '+
						'background-color:#CCC;z-index:20;left:0; top:0;"></div>	';
						$("body").append(load).append(shadow);
						setTimeout(function(){$("body").find(".load_tip,.shadow").remove();},1000);
					}else if(options_["type"]==1){//确认框（confirm）
						var load='<div class="load_tip" style="z-index:100;width:280px;height:160px; border:1px solid #3E7FC3; background:#FFF; position:fixed; top:'+top+'px;left:'+left+'px;">' +
						'<p style="border-bottom:1px solid #3E7FC3; background:#78B8E4; height:25px; line-height: 25px; padding-left:5px; font-weight:bold;font-size:14px; color:#535252;">'+accipiter.getLang("t3")+'</p>' +
						'<div style=" text-align:center; margin:28px auto; font-size:14px; height:34px;  padding:0 15px; color:#535252;" >' +
						''+options_["tip"]+''+
						'</div>'+
						'<div style="text-align:center; "><button>'+accipiter.getLang("t4")+'</button> <button>'+accipiter.getLang("t5")+'</button> </div>'+
						'</div>';
						var shadow='<div class="shadow" style="position:fixed;width:100%; height:100%;filter:alpha(opacity=50);opacity:.5; '+
							'background-color:#CCC;z-index:20;left:0; top:0;"></div>	';
						$("body").append(load).append(shadow);
						$("body").find(".load_tip button:eq(0)").click(function(){//确认
							$(".load_tip,.shadow").remove();
							if(options_["confirm_callback"]){
								options_["confirm_callback"].call(this);
							}
						});
						$("body").find(".load_tip button:eq(1)").click(function(){//取消
							$(".load_tip,.shadow").remove();
						});
					}else if(options_["type"]==2){//重复添加覆盖提示框
						var load='<div class="load_tip" style="z-index:100;width:280px;height:185px; border:1px solid #3E7FC3; background:#FFF; position:fixed; top:'+top+'px;left:'+left+'px;">' +
						'<p style="border-bottom:1px solid #3E7FC3; background:#78B8E4; height:25px; line-height: 25px; padding-left:5px; font-weight:bold;font-size:14px; color:#535252;">'+accipiter.getLang("t3")+'</p>' +
						'<div style=" text-align:center; margin:28px auto; font-size:14px; height:34px;  padding:0 15px; color:#535252;" >' +
						''+options_["tip"]+''+
						'</div>'+
						'<div style="text-align:center"; ><button>'+accipiter.getLang("yes")+'</button> <button>'+accipiter.getLang("no")+'</button> </div>'+
						'<div style="padding-top:20px"; ><input type="checkbox"/>'+accipiter.getLang("after")+'<b> '+options_["record"]+'</b> '+accipiter.getLang("check")+'</div>'+
						'</div>';
						var shadow='<div class="shadow" style="position:fixed;width:100%; height:100%;filter:alpha(opacity=50);opacity:.5; '+
							'background-color:#CCC;z-index:20;left:0; top:0;"></div>	';
						$("body").append(load).append(shadow);
						$("body").find(".load_tip button:eq(0)").click(function(){//确认
							if(options_["confirm_callback"]){
								options_["confirm_callback"].call(this,$(".load_tip :checkbox").is(":checked"));
							}
							$(".load_tip,.shadow").remove();
						});
						$("body").find(".load_tip button:eq(1)").click(function(){//取消
							if(options_["cancel_callback"]){
								options_["cancel_callback"].call(this,$(".load_tip :checkbox").is(":checked"));
							}
							$(".load_tip,.shadow").remove();
						});
					}
				}
			},
			progress:function(flag){//进度
				var load='<div class="progress" ><span><span></div>';
				if(flag){
					$("body").append(load);
				}else{
					if($(".progress")){
						$(".progress").remove();
					}
				}
			},
			loadding:function(flag){//进度
				var load='<div class="loadding" ></div>';
				if(flag){
					$("body").append(load);
				}else{
					if($(".loadding")){
						$(".loadding").remove();
					}
				}
			},
            resultMessage:function(message, duration) {
                if(!duration) duration = 1000;  //设置参数duration默认值为1000
                //duration = arguments[1] ? arguments[1] : 1000;
                new jBox('Notice', {
                    content: message,
                    autoClose:duration,
                    attributes: {
                        x: 'left',
                        y: 'top'
                    },
                    position: {  // The position attribute defines the distance to the window edges
                        x: $(document).width()/2,
                        y: $(document).height()/2
                    }
                });
            },
			getCookie:function(name){//获取cookie值
				var c=document.cookie;
				if(c){
					var s=new RegExp(name+"=([^;]*)");
					var cc=c.match(s);
					if(cc){return cc[1];}else{return null;}
				}
			},
			getLocale:function(){//获取本地话设置的语言，值en_US/zh_CN
				var c=document.cookie;
				var rtn=0;//默认中文。
				if(c){
					var s=/myAppLocaleCookie=([^\;]*)/;
					var cc=c.match(s);
					rtn=(cc && cc[1]==="en_US"?1:0);
				}
				return rtn;
			},
			getLang:function(tip){//公共语言对象切换
				if(accipiter.getLocale()==0){
					return lang[tip][0];
				}else{
					return lang[tip][1];
				}
			},
			getLang_:function(la,tip){//自定义语言对象切换
				if(accipiter.getLocale()==0){
					return la[tip][0];
				}else{
					return la[tip][1];
				}
			},
			gc:function(key){
				if(accipiter.getCookie("pdt")){
					return JSON.parse(accipiter.getCookie("pdt"))[key];
				}
			},
			getNowTime:function(){//默认获取当前时间，格式yyyy-m-d h:m:s
					var d=new Date();
					var year=d.getFullYear();
					var month=Number(d.getMonth())+1;month=(month<10?'0'+month:month);
					var day=d.getDate();day=(day<10?'0'+day:day);
					var hour=d.getHours();hour=(hour<10?'0'+hour:hour);
					var min=d.getMinutes();min=(min<10?'0'+min:min);
					var sec=d.getSeconds();sec=(sec<10?'0'+sec:sec);
					return year+'-'+month+'-'+day+" "+hour+':'+min+':'+sec;
			},
			getGroup_type_select:function(selector){//初始加载组属性
				var selector=selector || "#group_type_select",html="";
				html='<option value=0  selected >'+accipiter.getLang('partin')+'</option>'+
				     '<option value=1   >'+accipiter.getLang('respin')+'</option>' + 
				     '<option value=2   >'+accipiter.getLang('backin')+'</option>' ;
				$(selector).find("select").html(html);
				$(selector).find(".title").text(accipiter.getLang('partin'));
			},
			getGroup_type:function(val){//组属性
				var val=val||0;
				switch(Number(val)){
					case 0: return accipiter.getLang('partin');break;
					case 1: return accipiter.getLang('respin');break;
					case 2: return accipiter.getLang('backin');break;
				}
			},
			getVpnList:function(selector,flag){//加载vpn
				parm={
						"ip":accipiter.getIp(),
						"vpn_index":"-1"
					};
				 var jq=accipiter.doPost(accipiter.getRootPath()+"/rest/ajax/configuration/vpn/findList",parm);
				jq.done(function(data){
					if(data.status=="000000" && data.content.length>0){
							var html="";
							flag=flag||0;//默认添加修改
							if(flag==1){
								html+="<option value=-1>"+accipiter.getLang("unlimited")+"</option>";
								$(selector).find(".title").text(accipiter.getLang("unlimited"));
							}else{
								$(selector).find(".title").text(data.content[0].name);
							}
							$(data.content).each(function(i){
								html+="<option value="+data.content[i].vpn_index+">"+data.content[i].name+"</option>";
							});
							$(selector).find("select").append(html);
					}
				}); 	
			},
			getPriority_select:function(selector,flag){//初始优先等级//0-15
				var html="";
				flag=flag||0;
				if(flag==1){//查询。
					html+="<option value=-1>"+accipiter.getLang("unlimited")+"</option>";
					$(selector).find(".title").text(accipiter.getLang("unlimited"));
				}else{
					$(selector).find(".title").text(0+accipiter.getLang("level"));
				}
				for(var i=0;i<16;i++){
					html+="<option value="+i+">"+i+accipiter.getLang("level")+"</option>";
				}
				$(selector).find("select").append(html);
				
			},
			getGrpPriority_select:function(selector,flag){//组初始优先等级//
				var html="";
				flag=flag||0;
				if(flag==1){//查询。
					html+="<option value=-1>"+accipiter.getLang("unlimited")+"</option>";
					$(selector).find(".title").text(accipiter.getLang("unlimited"));
				}else{
					$(selector).find(".title").text(accipiter.getLang("normal"));
				}
				html+='<option value=0 >'+accipiter.getLang('normal')+'</option>' + 
			          '<option value=1 >'+accipiter.getLang('priority')+'</option>' ;
				$(selector).find("select").append(html);
			}
	});
})(jQuery);

$(function(){
	$("button,a,input[type=radio]").focus(function(){//去除虚线框
		$(this).blur();
	});
	$(".sel select").change(function(obj){//下拉列表框触发事件
		var target=obj.target;
		var o=$(this);
		$.each(target,function(i){
			var selected=target[i].selected;
			if(selected){
				var text=target[i].text;
				$(o).parent().find(".title").text(text);
			}
		});
	});
});
