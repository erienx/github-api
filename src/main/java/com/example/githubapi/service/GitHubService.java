package com.example.githubapi.service;

import com.example.githubapi.dto.BranchResponseDto;
import com.example.githubapi.dto.GitHubBranchResponseDto;
import com.example.githubapi.dto.GitHubRepositoryResponseDto;
import com.example.githubapi.dto.RepoResponseDto;
import com.example.githubapi.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final RestTemplate restTemplate;
    private static final String GITHUB_API_URL = "https://api.github.com";

    public List<RepoResponseDto> getUserRepos(String username) {
        String reposApiUrl = String.format("%s/users/%s/repos", GITHUB_API_URL, username);
        ResponseEntity<List<GitHubRepositoryResponseDto>> response =
                restTemplate.exchange(reposApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserNotFoundException("User " + username + " not found");
        }
        List<GitHubRepositoryResponseDto> repos = Optional.ofNullable(response.getBody()).orElse(List.of());

        return repos.stream().filter(repo -> !repo.fork()).map(this::mapToDto).collect(Collectors.toList());
    }

    public List<BranchResponseDto> getUserBranches(String username, String repo) {
        String branchApiUrl = String.format("%s/repos/%s/%s/branches", GITHUB_API_URL, username, repo);
        ResponseEntity<List<GitHubBranchResponseDto>> response =
                restTemplate.exchange(branchApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserNotFoundException("User " + username + " not found");
        }
        List<GitHubBranchResponseDto> branches = Optional.ofNullable(response.getBody()).orElse(List.of());
        return branches.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private RepoResponseDto mapToDto(GitHubRepositoryResponseDto repo) {
        List<BranchResponseDto> branches = getUserBranches(repo.owner().login(), repo.name());
        return new RepoResponseDto(repo.name(), repo.owner().login(), branches);
    }

    private BranchResponseDto mapToDto(GitHubBranchResponseDto dto) {
        return new BranchResponseDto(dto.name(), dto.commit().sha());
    }
}
