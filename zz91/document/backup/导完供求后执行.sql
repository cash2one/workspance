#更新是否暂停数据
update ast.products set is_pause=1 where exists(select null from rcu.products_stop where pdt_id=products.pdt_id)
