package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlideDto {
	private String imageUrl;
	private String text;
	private int order;
	private OrganizationDTO organization;
}
