import { Routes } from '@angular/router';
import { ChallengeDetailPage } from './pages/challenge-detail-page.component';
import { ChallengesListPage } from './pages/challenges-list-page/challenges-list-page';

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
