import { Routes } from '@angular/router';
import { ChallengesListPage } from './pages/challenges-list-page.component';
import { ChallengeDetailPage } from './pages/challenge-detail-page.component';
import { DeleteChallengePage } from './pages/delete-challenge-page/delete-challenge-page';

export const CHALLENGES_ROUTES: Routes = [
  {
    path: '',
    component: ChallengesListPage,
  },
  {
    path: 'delete',
    component: DeleteChallengePage,
  },
  {
    path: ':id',
    component: ChallengeDetailPage,
  },
];
