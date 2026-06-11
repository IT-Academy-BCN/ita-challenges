package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.seed;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ChallengeSeedLoader implements ApplicationRunner {
    private final ChallengeRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repository.findAll().isEmpty()) {
            List<Challenge> initialChallenges = List.of(
                    Challenge.restore(
                            ChallengeId.of("11111111-1111-1111-1111-111111111111"),
                            "FizzBuzz Avançat",
                            "Escriu un programa que iteri dels números de l'1 al 100. Per als múltiples de 3, ha d'imprimir 'Fizz'; per als múltiples de 5, 'Buzz'; i per als múltiples de tots dos, 'FizzBuzz'. Aquest exercici és fonamental per comprendre les estructures de control de flux i la lògica condicional en entorns de producció, on la neteja del codi és tan important com la seva funcionalitat.",
                            ChallengeLanguage.JAVA,
                            ChallengeDifficulty.EASY,
                            "for(int i=1;i<=100;i++){System.out.println(i%15==0?\"FizzBuzz\":i%3==0?\"Fizz\":i%5==0?\"Buzz\":i);}"
                    ),
                    Challenge.restore(
                            ChallengeId.of("22222222-2222-2222-2222-222222222222"),
                            "Validació de Palíndroms",
                            "Crea una funció robusta que verifiqui si una cadena de text és un palíndrom. Has de tenir en compte que el text pot contenir espais, signes de puntuació i lletres en majúscula o minúscula que han de ser ignorades o normalitzades. L'objectiu és demostrar la teva habilitat manipulant cadenes de caràcters i utilitzant expressions regulars per a la neteja de dades.",
                            ChallengeLanguage.JAVASCRIPT,
                            ChallengeDifficulty.MEDIUM,
                            "const isPalindrome = str => { const clean = str.toLowerCase().replace(/[^a-z0-9]/g, ''); return clean === clean.split('').reverse().join(''); };"
                    ),
                    Challenge.restore(
                            ChallengeId.of("33333333-3333-3333-3333-333333333333"),
                            "Disseny d'API REST",
                            "Desenvolupa una API REST completa per gestionar un inventari de productes. Hauràs d'implementar els mètodes HTTP estàndard (GET, POST, PUT, DELETE) i garantir que el servidor retorni els codis d'estat correctes segons el resultat de l'operació. Aquest repte se centra en les bones pràctiques d'arquitectura backend i la gestió de recursos a través de endpoints ben definits.",
                            ChallengeLanguage.TYPESCRIPT,
                            ChallengeDifficulty.HARD,
                            "app.get('/items', (req, res) => res.json(db)); app.post('/items', (req, res) => { db.push(req.body); res.status(201).send(); });"
                    ),
                    Challenge.restore(
                            ChallengeId.of("44444444-4444-4444-4444-444444444444"),
                            "Mitjana amb Pandas",
                            "Calcula la mitjana d'una llista de valors utilitzant la llibreria Pandas de Python.",
                            ChallengeLanguage.PYTHON,
                            ChallengeDifficulty.MEDIUM,
                            "import pandas as pd; def get_avg(data): return pd.Series(data).mean()"
                    ),
                    Challenge.restore(
                            ChallengeId.of("55555555-5555-5555-5555-555555555555"),
                            "Consultes SQL Joins",
                            "Realitza una unió (JOIN) entre dues taules: Usuaris i Comandes per obtenir les dades relacionades.",
                            ChallengeLanguage.SQL,
                            ChallengeDifficulty.EASY,
                            "SELECT * FROM Users JOIN Orders ON Users.id = Orders.user_id;"
                    ),
                    Challenge.restore(
                            ChallengeId.of("66666666-6666-6666-6666-666666666666"),
                            "Filtre d'Arrays en PHP",
                            "Utilitza array_filter per filtrar números parells d'un array donat.",
                            ChallengeLanguage.PHP,
                            ChallengeDifficulty.HARD,
                            "$evens = array_filter($array, fn($n) => $n % 2 === 0);"
                    )
            );
                    initialChallenges.forEach(repository::save);
        }
    }
}
