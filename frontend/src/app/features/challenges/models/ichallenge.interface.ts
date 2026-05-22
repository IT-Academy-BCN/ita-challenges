import { ChallengeLanguage } from './challenge-language.type';

export interface IChallenge {
  id: string;
  title: string;
  description: string;
  language?: ChallengeLanguage;
}
