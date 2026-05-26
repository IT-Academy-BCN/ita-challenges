import { ChallengeDifficulty } from "./challenge-difficulty.type";

export interface IChallengeRequest {
    title: string;
    description: string;
    solution?: string;
    difficulty?: ChallengeDifficulty;
}
