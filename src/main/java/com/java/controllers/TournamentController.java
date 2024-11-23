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
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament) {
        return ResponseEntity.ok(tournamentService.addTournament(tournament));
    }

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
                    tournament.getMembers().addAll(members);
                    tournamentService.updateTournament(tournament); // Explicitly save the tournament
                    return ResponseEntity.ok(tournament);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}