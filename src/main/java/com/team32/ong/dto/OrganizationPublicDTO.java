package com.team32.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrganizationPublicDTO {

    private String name;

    private String image;
    
    private Integer phone;

    private String address;

    private String facebookUrl;

    private String linkedinUrl;

    private String instagramUrl;

}