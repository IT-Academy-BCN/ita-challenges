import { ChallengeLanguage } from './challenge-language.type';
import { ChallengeDifficulty } from './challenge-difficulty.type';

export interface IChallenge {
  id: string;
  title: string;
  description: string;
  solution?: string;
  language?: ChallengeLanguage;
  difficulty?: ChallengeDifficulty;
}