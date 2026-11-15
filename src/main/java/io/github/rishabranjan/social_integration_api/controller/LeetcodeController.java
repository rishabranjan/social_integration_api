package io.github.rishabranjan.social_integration_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/leetcode")
@AllArgsConstructor
public class LeetcodeController {

    @GetMapping("/geUserProfile")
    public JsonNode getUsers(@RequestParam String userName) {

        String query = """
                  query getUserProfile($username: String!) {
                  matchedUser(username: $username) {
                    username
                    githubUrl
                    twitterUrl
                    linkedinUrl
                    profile{
                        realName
                            userAvatar
                            birthday
                            ranking
                            reputation
                            websites
                            countryName
                            company
                            school
                            skillTags
                            aboutMe
                            starRating
                    }
                    submitStats {
                            acSubmissionNum {
                                difficulty
                                count
                                submissions
                            }
                        }
                    activeBadge {
                      displayName
                      icon
                      name
                    }
                  }
                }""";
        return RestClient.create()
                .post().uri("https://leetcode.com/graphql")
                .body(Map.of("query", query, "variables", Map.of("username", userName)))
                .retrieve()
                .body(JsonNode.class);
    }
}