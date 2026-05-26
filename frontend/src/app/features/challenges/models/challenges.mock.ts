import { IChallenge } from './ichallenge.interface';

export const CHALLENGES_MOCK: IChallenge[] = [
  {
    id: "1",
    title: "primer",
    description: "descripció 1",
    solution: 'solució 1',
    difficulty: 'EASY',

  },
  {
    id: "2",
    title: "segon",
    description: "descripció 2",
    solution: 'solució 2',
    difficulty: 'MEDIUM',
  },
  {
    id: "3",
    title: "tercer",
    description: "descripció 3",
    solution: 'solució 3',
    difficulty: 'HARD',
  },
];
