package com.example.withdraw.service;

import com.example.member.dto.MemberDto;
import com.example.member.service.MemberService;
import com.example.withdraw.enitty.Withdraw;
import com.example.withdraw.repository.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawService {
    private final WithdrawRepository withdrawRepository;
    private final MemberService memberService;

    @Transactional
    public Withdraw create(String bankName, String bankAccountNo, long price, MemberDto memberDto) {
        Withdraw withdraw = Withdraw.builder()
                .createDate(LocalDateTime.now())
                .bankName(bankName)
                .bankAccountNo(bankAccountNo)
                .price(price)
                .member(memberDto.toEntity())
                .build();
        withdrawRepository.save(withdraw);

        return withdraw;
    }

    public List<Withdraw> getAll() {
        return withdrawRepository.findAll();
    }

    @Transactional
    public boolean withdrawDone(long id) {
        Withdraw withdraw = withdrawRepository.findById(id).orElse(null);
        if(withdraw.isCanceled()) {
            return false;
        } else {
            withdraw.setWithdraw(true);

            memberService.addCashWithdraw(withdraw.getMember(), withdraw.getPrice() * -1, "출금__%d__사용__예치금".formatted(withdraw.getId()));
            withdrawRepository.save(withdraw);
        }
        return true;


    }

    public Withdraw getById(long id) {
        return withdrawRepository.findById(id).orElse(null);
    }

    public List<Withdraw> getByMember(MemberDto memberDto) {
        return withdrawRepository.findAllByMemberId(memberDto.getId());
    }

    @Transactional
    public boolean cancel(Withdraw withdraw) {
        if(withdraw.isWithdraw() == true) {
            return false;
        }
        withdraw.setCanceled(true);
        withdrawRepository.save(withdraw);
        return true;
    }

    @Transactional
    public boolean cancel(Withdraw withdraw, String cancelReason) {
        if(withdraw.isWithdraw() || withdraw.isCanceled()) {
            return false;
        }
        withdraw.setCanceled(true);
        withdraw.setCancelReason(cancelReason);
        withdrawRepository.save(withdraw);
        return true;
    }
}
