package com.task.iapps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EPaperDTO {
    private String newspaperName;
    private Integer width;
    private Integer height;
    private Integer dpi;
    private Date uploadDate;
    private String filename;
}
