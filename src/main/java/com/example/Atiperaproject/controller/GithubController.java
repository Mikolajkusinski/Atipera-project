package com.example.Atiperaproject.controller;

import com.example.Atiperaproject.service.GithubService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @RequestMapping("api/repo")
    public ResponseEntity<String> getRepos(@RequestParam String name, @RequestParam String fileType) throws IOException {


        String response = githubService.getRepoInfo(name,fileType.toLowerCase());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

