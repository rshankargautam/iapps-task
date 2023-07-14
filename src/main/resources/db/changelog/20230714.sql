DROP TABLE IF EXISTS `epaper`;

CREATE TABLE `epaper` (
                        id BIGINT PRIMARY KEY,
                        newspaper_name VARCHAR(255),
                        width INTEGER,
                        height INTEGER,
                        dpi INTEGER,
                        upload_date DATE,
                        file_name VARCHAR(255)
);