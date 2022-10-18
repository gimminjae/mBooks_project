package com.example.post;

import com.example.member.Member;
import com.example.member.MemberDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String subject;

    private String content;

    private String contentHtml;

    private MemberDto member;
}