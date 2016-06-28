package com.zz91.ep.dto.comp;

import java.io.Serializable;
import java.util.List;

import com.zz91.ep.domain.comp.CompTags;

public class CompTagsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CompTags compTags;
	private List<CompTags> grandSon;
	public CompTagsDto() {
		super();
	}
	public CompTagsDto(CompTags compTags, List<CompTags> grandSon) {
		super();
		this.compTags = compTags;
		this.grandSon = grandSon;
	}
	public CompTags getCompTags() {
		return compTags;
	}
	public void setCompTags(CompTags compTags) {
		this.compTags = compTags;
	}
	public List<CompTags> getGrandSon() {
		return grandSon;
	}
	public void setGrandSon(List<CompTags> grandSon) {
		this.grandSon = grandSon;
	}


}
