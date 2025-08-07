package com.example.githubapi;

import com.example.githubapi.dto.BranchResponseDto;
import com.example.githubapi.dto.RepoResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void shouldReturnUserReposWithBranches_whenUserExistsAndReposAreNotForks() {
        //given
        final String username = "octocat";
        // when
        ResponseEntity<RepoResponseDto[]> response = restTemplate.getForEntity(
                "/api/users/" + username + "/repos", RepoResponseDto[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RepoResponseDto[] repos = response.getBody();
        assertThat(repos).isNotNull();
        assertThat(repos.length).isGreaterThan(0);

        for (RepoResponseDto repo : repos) {
            assertThat(repo.name()).isNotEmpty();
            assertThat(repo.ownerLogin()).isEqualTo(username);
            assertThat(repo.branches()).isNotNull();
            assertThat(repo.branches().size()).isGreaterThan(0);

            for (BranchResponseDto branch : repo.branches()) {
                assertThat(branch.name()).isNotEmpty();
                assertThat(branch.lastSha()).isNotEmpty();
            }
        }
    }
}
