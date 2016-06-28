Ext.ns("ast.ast1949.auto.caiji");

//定义变量
var _C = new function() {
 this.RESULT_GRID = "resultgrid";
}
ast.ast1949.auto.caiji.CAIJITFIELD=[
             {name:"id",mapping:"id"},
             {name:"title",mapping:"title"},
             {name:"num",mapping:"num"},
             {name:"url",mapping:"url"},
             {name:"defaultTime",mapping:"defaultTime"},
             {name:"earlyTime",mapping:"earlyTime"},
             {name:"lateTime",mapping:"lateTime"}
];
ast.ast1949.auto.caiji.AutoCaijiGrid = Ext.extend(Ext.grid.GridPanel,{
    constructor:function(config){
        config=config||{};
        Ext.apply(this,config);
        
        var _store = new Ext.data.JsonStore({
            root:"records",
            totalProperty: 'totalRecords',
            fields:ast.ast1949.auto.caiji.CAIJITFIELD,
            url:Context.ROOT +Context.PATH+  "/auto/caiji/load_caiji_log.htm",
            autoLoad:true
        });
        
        var _sm=new Ext.grid.CheckboxSelectionModel();
        
        var grid = this;
        
        var _cm=new Ext.grid.ColumnModel( [_sm,{
            header : "编号",
            width : 50,
            sortable : true,
            dataIndex : "id",
            hidden:true
        },{
            header:"名称",
            dataIndex:"title",
            width:200,
            
        },{
            header:"次数",
            dataIndex:"num",
            sortable : true,
            width:100,
            
        },{
            header:"地址",
            dataIndex:"url",
            width:200,
            
        },{
            header:"预设时间",
            dataIndex:"defaultTime",
            width:100,
        },{
            header:"抓取最早时间",
            dataIndex:"earlyTime",
            width:100,
        },{
            header:"抓取最晚时间",
            dataIndex:"lateTime",
            width:100,
        }]);
        
            
        var c={
                id : _C.RESULT_GRID,
                loadMask:Context.LOADMASK,
                store:_store,
                sm:_sm,
                cm:_cm,
                tbar:["->",
                      "开始时间>=", {
                    xtype : "datefield",
                    format : 'Y-m-d',
                    id : "searchStartTime",
                    name:"startTime",
                    width : 90
                }, "-", "结束时间<=", {
                    xtype : "datefield",
                    format : 'Y-m-d',
                    id : "searchEndTime",
                    name:"endTime",
                    width : 90
                },{
                    text : "查询",
                    iconCls : "query",
                    handler :function(){
                    var result_grid = Ext.getCmp(_C.RESULT_GRID);
//                  var _store = Ext.getCmp(_C.RESULT_GRID).getStore();
//                  var B = _store.baseParams;
                     
                        result_grid.store.baseParams = {
                            "from":Ext.get("searchStartTime").dom.value,
                            "to":Ext.get("searchEndTime").dom.value
                            };
                        
                        var grid = Ext.getCmp(_C.RESULT_GRID);
                            grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
                        }},"-", {
                      text:"导出excel",
                      listeners:{
                              "click":function(){
                                   var from = Ext.get("searchStartTime").dom.value;
                                   var to = Ext.get("searchEndTime").dom.value;
                                  Ext.MessageBox.confirm("导出excel表","确定导出数据?",function(btn){
                                  if(btn!="yes"){
                                      return ;
                                  }else{
                                      Ext.Ajax.request({
                                          url: window.open(Context.ROOT +Context.PATH+  "/auto/caiji/exportLogData.htm?from="+from+"&to="+to),
                                      });
                                  }
                              });
                                  var grid = Ext.getCmp(_C.RESULT_GRID);
                                  grid.store.reload({params:{"startIndex":0,"pageSize":Context.PAGE_SIZE}});
                              }
                          }
                      }]
            };
        
        ast.ast1949.auto.caiji.AutoCaijiGrid.superclass.constructor.call(this,c);
    }
});




