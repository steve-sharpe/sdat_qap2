// src/main/java/com/java/services/MemberService.java
package com.java.services;

import com.java.entities.Member;
import com.java.entities.Tournament;
import com.java.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TournamentService tournamentService;

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> searchByName(String name) {
        return memberRepository.findByNameContaining(name);
    }

    public List<Member> getMembersByIds(List<Long> ids) {
        return memberRepository.findAllById(ids);
    }

    public List<Member> addMembersToTournament(Long memberId, List<Long> tournamentIds) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            List<Tournament> tournaments = tournamentService.getTournamentsByIds(tournamentIds);
            if (!tournaments.isEmpty()) {
                member.setTournament(tournaments.get(0)); // Assuming one tournament per member
                memberRepository.save(member);
            }
            return List.of(member);
        } else {
            throw new RuntimeException("Member not found");
        }
    }

    public List<Member> getMembersByIdsForTournament(List<Long> memberIds) {
        return memberRepository.findAllById(memberIds);
    }
}