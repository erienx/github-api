package com.example.githubapi.controller;

import com.example.githubapi.dto.RepoResponseDto;
import com.example.githubapi.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GitHubController {
    private final GitHubService gitHubService;

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<List<RepoResponseDto>> getUserRepos(@PathVariable String username){
        List<RepoResponseDto> repos = gitHubService.getUserRepos(username);
        return ResponseEntity.ok(repos);
    }
}
