package com.kl91.persist.company;

import java.util.List;

import com.kl91.domain.company.CreditFile;
import com.kl91.domain.dto.company.CreditFileSearchDto;

public interface CreditFileDao {

	public Integer insert(CreditFile creditFile);

	public Integer update(CreditFile creditFile);

	public Integer delete(Integer id);

	public CreditFile queryById(Integer id);

	public List<CreditFile> queryFile(CreditFileSearchDto searchDto);
}
