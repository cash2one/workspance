//查询有多少条公司信息没有帐号信息
select count(*) from comp_profile cp left join comp_account ca on cp.id=ca.cid where ca.id is null and cp.id < 50000000;
//删除公司信息没有帐号信息
delete cp from comp_profile cp left join comp_account ca on cp.id=ca.cid where ca.id is null and cp.id < 50000000;
//查询有多少条帐号信息没有公司信息
select count(*) from comp_account ca left join comp_profile cp on cp.id=ca.cid where cp.id is null and ca.id < 10000000;
//删除公司信息没有帐号信息
delete ca from comp_account ca left join comp_profile cp on cp.id=ca.cid where cp.id is null and ca.id < 10000000;
//查询有多少条没有公司信息的供应信息
select count(*) from trade_supply ts left join comp_profile cp on cp.id=ts.cid where cp.id is null and ts.id < 10000000;
//删除没有没有公司信息的供应信息
delete ts from trade_supply ts left join comp_profile cp on cp.id=ts.cid where cp.id is null and ts.id < 10000000;
//查询有多少条没有公司信息的求购信息
select count(*) from trade_buy tb left join comp_profile cp on cp.id=tb.cid where cp.id is null and tb.id < 10000000;
//删除没有没有公司信息的求购信息
delete tb from trade_buy tb left join comp_profile cp on cp.id=tb.cid where cp.id is null and tb.id < 10000000;

