package com.example.githubapi.dto;

public record GitHubRepositoryResponseDto(String name, GitHubOwnerResponseDto owner , boolean fork) {
}
