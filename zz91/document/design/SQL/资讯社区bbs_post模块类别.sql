/*��ҵ��̬*/
UPDATE bbs_post SET bbs_post_category_id=1
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('���ϰ�̬','�������� ','�Ͻ�����̳ ','������̸','���̹���','��Ƹר��','��Ѷ����','��������','��������','�Ͻ�������','������̳','�����Ͻ���','���Ͽ���̳','PET����ר��',
'�ۺϷ�����̳','��ֽ��̳','������̳','��������')));

/*��ҵ֪ʶ*/
UPDATE bbs_post SET bbs_post_category_id=2
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('����ר��','��ҵ.�ɹ�','���ϲ�ҵ֮��','�ط�֮��','��ҵ��ƭ��̳','��ƭ����','�ع�̨','��������')));

/*ASTO��̬*/
UPDATE bbs_post SET bbs_post_category_id=3
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('ZZ91ѧ��','ZZ91��Ƶ����','ZZ91������','�ɹ�����','ZZ91��վ����','�����','��վ�¹���',
 '���»','��վ����', '������̨','�����¿�','������̬','������·','��������')));

/* 30-40 */
UPDATE bbs_post SET bbs_post_category_id=4
WHERE id IN(SELECT bbs_post_id FROM bbs_post_relate_tags 
WHERE bbs_tags_id IN(SELECT id FROM bbs_tags 
WHERE NAME IN('��������','����һ��','���ʶ','���ְ���')));

/* ���е���Ѷ�������ע�鵽��ҵ��̬*/
UPDATE bbs_post SET bbs_post_category_id=1 where old_news_id is not null;