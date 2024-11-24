package com.java.services;

import com.java.entities.Member;
import com.java.entities.Tournament;
import com.java.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {
    @Autowired
    private TournamentRepository tournamentRepository;

    public Tournament addTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public List<Tournament> getTournamentsByIds(List<Long> ids) {
        return tournamentRepository.findAllById(ids);
    }

    public void updateTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }


    public Optional<Tournament> getTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    public Tournament addMembersToTournament(Long tournamentId, List<Long> memberIds, MemberService memberService) {
        Optional<Tournament> tournamentOpt = tournamentRepository.findById(tournamentId);
        if (tournamentOpt.isPresent()) {
            Tournament tournament = tournamentOpt.get();
            List<Member> members = memberService.getMembersByIdsForTournament(memberIds);
            tournament.getMembers().addAll(members);
            return tournamentRepository.save(tournament);
        } else {
            throw new RuntimeException("Tournament not found");
        }
    }
}