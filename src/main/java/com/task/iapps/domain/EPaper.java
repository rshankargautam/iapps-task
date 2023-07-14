package com.task.iapps.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "epaper")
public class EPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "newspaper_name")
    private String newspaperName;
    private Integer width;
    private Integer height;
    private Integer dpi;
    @Column(name = "upload_date")
    private Date uploadDate;
    @Column(name = "file_name")
    private String filename;
}
