import { Routes } from '@angular/router';
import { ChallengesListPage } from './pages/challenges-list-page.component';
import { ChallengeDetailPage } from './pages/challenge-detail-page.component';
import { CreateChallengePage } from './pages/create-challenge-page/create-challenge-page';

export const CHALLENGES_ROUTES: Routes = [
  {
    path: '',
    component: ChallengesListPage,
  },
  {
    path: ':id',
    component: ChallengeDetailPage,
  },
  {
    path: 'create',
    component: CreateChallengePage,
  },
];
