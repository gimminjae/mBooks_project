package com.example.keyword;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordDto {
    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String content;

}
