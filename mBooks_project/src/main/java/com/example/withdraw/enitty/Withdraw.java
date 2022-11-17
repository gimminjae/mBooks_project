package com.example.withdraw.enitty;

import com.example.member.enitty.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String bankName;

    private String bankAccountNo;

    private long price;

    @ManyToOne
    private Member member;

    private boolean isWithdraw; //출금 처리 여부

    private boolean isCanceled; //취소 여부

    private String cancelReason; //취소 사유 - 관리자만
}
