package com.punchlistusa.baseball.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(title = "Baseball REST APIs", version = "1.0",
        description = "Baseball REST API Documentation",
        contact = @Contact(email = "@gmail.com", name = "Breymer Robles")),
    servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
public class BaseballLeagueApplication {

  public static void main(String[] args) {
    SpringApplication.run(BaseballLeagueApplication.class, args);
  }

}
