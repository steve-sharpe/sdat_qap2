// src/main/java/com/java/controllers/MemberController.java
package com.java.controllers;

import com.java.entities.Member;
import com.java.entities.Tournament;
import com.java.services.MemberService;
import com.java.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.addMember(member));
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(memberService.searchByName(name));
    }

    @PostMapping("/{memberId}/tournaments")
    public ResponseEntity<Member> addTournamentsToMember(@PathVariable Long memberId, @RequestBody List<Long> tournamentIds) {
        return memberService.getMemberById(memberId)
                .map(member -> {
                    List<Tournament> tournaments = tournamentService.getTournamentsByIds(tournamentIds);
                    if (!tournaments.isEmpty()) {
                        member.setTournament(tournaments.get(0)); // Assuming one tournament per member
                        memberService.updateMember(member); // Explicitly save the member
                    }
                    return ResponseEntity.ok(member);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}