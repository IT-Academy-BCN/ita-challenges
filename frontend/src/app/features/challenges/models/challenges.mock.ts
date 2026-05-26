import { IChallenge } from './ichallenge.interface';

export const CHALLENGES_MOCK: IChallenge[] = [
  {
    id: "1",
    title: "primer",
    description: "descripció 1",
    language: 'TYPESCRIPT',
    difficulty: 'EASY',
    solution: 'solució 1',
  },
  {
    id: "2",
    title: "segon",
    description: "descripció 2",
    language: 'JAVA',
    difficulty: 'MEDIUM',
    solution: 'solució 2',
  },
  {
    id: "3",
    title: "tercer",
    description: "descripció 3",
    language: 'PHP',
    difficulty: 'HARD',
    solution: 'solució 3',
  },
];
