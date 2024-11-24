// src/main/java/com/java/controllers/TournamentController.java
package com.java.controllers;

import com.java.entities.Member;
import com.java.entities.Tournament;
import com.java.services.MemberService;
import com.java.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/{tournamentId}/members")
    public ResponseEntity<Tournament> addMembersToTournament(@PathVariable Long tournamentId, @RequestBody List<Long> memberIds) {
        return tournamentService.getTournamentById(tournamentId)
                .map(tournament -> {
                    List<Member> members = memberService.getMembersByIds(memberIds);
                    for (Member member : members) {
                        member.setTournament(tournament); // Set the tournament for each member
                    }
                    tournament.getMembers().addAll(members);
                    memberService.saveAll(members); // Save the members to update the relationship
                    tournamentService.updateTournament(tournament); // Save the tournament
                    return ResponseEntity.ok(tournament);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}