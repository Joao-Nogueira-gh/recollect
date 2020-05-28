# ReCollect

## Description

Marketplace for collectors, ReCollect™️, using the Spring Boot framework and applying consistent SQA methods.

ReCollect is a platform that allows users to sell their old collections, from simple things such as figurines sold some years ago in stores to rarer items like limited editions of a certain product (book, game, etc.). Here, the buyers can find items they need in order to complete their collections or even start brand new ones!

## Team

Team Manager ->  André Amarante 89198

Product Owner -> Alexandre Lopes 88969

DevOps Master -> João Nogueira 89262 ; Tiago Melo 89005

## Relevant Links

Trello Backlog Management -> https://trello.com/b/5TPZlqim

SonarCloud Analysis -> https://sonarcloud.io/dashboard?id=Joao-Nogueira-gh_recollect

## To run
```bash
$ ./start.sh
```

For development and testing the same is ran, to have the DB and Admin containers running and then testing can be done normally. Change

## Unit Tests
```bash
$ mvn test
```

## Integration tests using cucumber (checking Step Definitions)
```bash
$ mvn clean verify -Pacceptance-tests
```

