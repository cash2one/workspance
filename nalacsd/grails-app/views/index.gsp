<%@ page import="com.nala.csd.Role; com.nala.csd.HeroRole; com.nala.csd.Hero" %>
<html>
    <head>
        <title>NALA 牛盾系统</title>
        <meta name="layout" content="main" />
        <style type="text/css" media="screen">

        #nav {
            margin-top:20px;
            margin-left:30px;
            width:228px;
            float:left;

        }
        .homePagePanel * {
            margin:0px;
        }
        .homePagePanel .panelBody ul {
            list-style-type:none;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody h1 {
            text-transform:uppercase;
            font-size:1.1em;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody {
            background: url(images/leftnav_midstretch.png) repeat-y top;
            margin:0px;
            padding:15px;
        }
        .homePagePanel .panelBtm {
            background: url(images/leftnav_btm.png) no-repeat top;
            height:20px;
            margin:0px;
        }

        .homePagePanel .panelTop {
            background: url(images/leftnav_top.png) no-repeat top;
            height:11px;
            margin:0px;
        }
        h2 {
            margin-top:15px;
            margin-bottom:15px;
            font-size:1.2em;
        }
        #pageBody {
            margin-left:400px;
            margin-right:400px;
        }

        .jiange{
            margin-top: 200px;
            padding-left: 15px;
        }

        /*.clearBoth{*/
            /*clear:both;*/
        /*}*/
        .bigTitle{
            /*text-align: center;*/
            font-size:20px;
            /*color:#000000;*/
            /*background:#bfbfbf;*/
            font-weight:bold;
            height:60px;
            line-height: 60px;
        }

        .littleTitle{
            padding-top: 5px;
            padding-left:15px;
            padding-bottom: 5px;
            font-size:16px;
            font-weight: bold;
        }

        .dingdan{
           background:#bfbfbf;
        }

        #lidan{
            /*height: 150px;*/
            background: #f2f2f2;
        }

        #chadan{
            /*height: 150px;*/
            background: #f2f2f2;
        }


       #smallShouhou{
            background:#f2f2f2;
            /*height:120px;*/
        }
        #b2c{
            /*background:#bfbfbf;*/
        }
        #smallB2c{
            background:#f2f2f2;
        }
        .record{
            list-style: none;
        }
       .record li{
            height: 35px;
            font-size: 12px;
            line-height:35px;
            padding-left:50px;
        }

        #kongge{
            height:30px;
        }
        </style>
    </head>
    <body>
        <div id="nav">
            <div class="homePagePanel">
                <div class="panelTop"></div>
                <div class="panelBody">
                    <h1>客服文化</h1>
                    <ul>
                        <li>专注</li>
                        <li>-----</li>
                        <li>协作</li>
                        <li>-----</li>
                        <li>执行</li>
                        <li>-----</li>
                        <li>主动</li>
                        <li>-----</li>
                        <li>开放</li>
                        <li>-----</li>
                        <li>简单</li>
                    </ul>
                    <h1>客服中心通讯录</h1>
                    <ul>
                            <li><a href="http://wiki.nalashop.com/index.php/NALA%E9%80%9A%E8%AE%AF%E5%BD%95#.E5.AE.A2.E6.9C.8D.E4.B8.AD.E5.BF.83">通讯录</a></li>
                    </ul>
                </div>
                <div class="panelBtm"></div>
            </div>
        </div>
        <div id="pageBody">
            <h1>NALA 牛盾系统</h1>
            <br><br>
            <div id="controllerList" class="dialog">
                <div class="dingdan">
                    <center> <span class="bigTitle">订单</span></center>
                    <div id="lidan">
                        <div class="littleTitle">理单</div>
                        <div class="listDan">
                            <ul class="record">
                                <li class="controller">&nbsp;&nbsp;&nbsp;<g:link controller="errorTrade">《异常单记录列表》</g:link>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="/errorTrade/list?storeType=true">《TP异常单记录列表》</a></li>


                                %{--<li class="controller"><a href="/errorTrade/list?storeType=true">《TP异常单记录列表》</a></li>--}%
                            </ul>
                        </div>
                    </div>
                    <div id="chadan">
                        <div  class="littleTitle">查单</div>
                        <div class="listDan">
                            <ul class="record">
                                <li class="controller"><g:link controller="chajianPassive">《被动查单记录列表》</g:link> &nbsp;&nbsp;|&nbsp;&nbsp;<a href="chajianPassive/list?storeType=true">《TP被动查单记录列表》</a></li>


                                <li class="controller"><g:link controller="chajianInitiative">《主动查单记录列表》</g:link>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<g:link controller="logisticsProblem">《快递查单记录列表》</g:link></li>


                                %{--<li class="controller"><g:link controller="logisticsProblem">《快递查单记录列表》</g:link></li>--}%

                                %{--<li class="controller"><a href="chajianPassive/list?storeType=true">《TP被动查单记录列表》</a></li>--}%
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clearBoth"></div>
                <div class="shouhou">
                    <div class="dingdan">
                     <center><span class="bigTitle" >售后</span></center>
                    </div>
                     <div id="smallShouhou">
                         <div class="listDan">
                            <ul class="record">
                              <li class="controller"><g:link controller="jinGuanShouHou">《金冠店售后记录列表》</g:link>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="/jinGuanShouHou/shouhouList?tab=weiwanjie">售后入口</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="/jinGuanShouHou/moneyList?tab=tuikuan">货款入口</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="/jinGuanShouHou/evaList?tab=Evaluate">中差评入口</a></li>

                               <li class="controller"><a href="/tmallShouhou/list?tp=true">《TP店售后记录列表》</a></li>

                               <li class="controller"><g:link controller="tmallShouhou">《天猫/B2C/拍拍店售后记录列表》</g:link></li>
                            </ul>
                          </div>
                     </div>
                </div>
                <div class="clearBoth"></div>
                <div id="b2c">
                    <div class="dingdan">
                         <center> <span class="bigTitle">B2C</span></center>
                    </div>
                     <div id="smallB2c">
                        <ul class="record">
                            <li class="controller"><g:link controller="b2CConsult">《B2C顾客咨询登记表》</g:link></li>
                        </ul>
                     </div>
                </div>
                <div id="kongge"></div>

                <g:if test="${com.nala.csd.Role.get(com.nala.csd.HeroRole.findByHero(Hero.get(userId))?.roleId)?.authority == 'ROLE_ADMIN'}">
                        <h2>自定义</h2>
                        <ul>
                            <li class="controller"><g:link controller="questionType">问题类型</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="hero">客服人员</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="chajianCode">查件代码</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="notifyMode">通知类型</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="store">店铺</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="express">快递公司</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="errorReasonForTrade">异常原因</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="solveType">处理方式</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="questionSource">咨询方式</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="b2CConsultResult">处理结果</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="questionReason">问题原因</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="returnGoodsConfirm">退货待确认项</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="role">角色</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="jinTmallSolveType">金冠天猫问题类型</g:link></li>
                        </ul>
                        <ul>
                            <li class="controller"><g:link controller="dealPriority">处理优先级</g:link></li>
                        </ul>
                        </div>
                        <br>
                        <br>
                        <div>
                            <sec:ifAllGranted roles="ROLE_ADMIN"><g:link controller="userOperate" action="list">日记记录</g:link></sec:ifAllGranted>
                        </div>
                    </g:if>
        </div>

    </body>
</html>
