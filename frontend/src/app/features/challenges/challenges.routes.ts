import { Routes } from '@angular/router';
import { ChallengesListPage } from './pages/challenges-list-page';
import { ChallengeDetailPage } from './pages/challenge-detail-page';

export const CHALLENGES_ROUTES: Routes = [
  {
    path: '',
    component: ChallengesListPage,
  },
  {
    path: ':id',
    component: ChallengeDetailPage,
  },
];
