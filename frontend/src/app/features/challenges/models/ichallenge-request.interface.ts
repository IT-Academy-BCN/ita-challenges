import { ChallengeLanguage } from './challenge-language.type';

export interface IChallengeRequest {
  title: string;
  description: string;
  language?: ChallengeLanguage;
}
