import { Routes } from '@angular/router';
import { ChallengeDetailPage } from './pages/challenge-detail-page.component';
import { CreateChallengePage } from './pages/create-challenge-page/create-challenge-page';
import { DeleteChallengePage } from './pages/delete-challenge-page/delete-challenge-page';

export const CHALLENGES_ROUTES: Routes = [
  {
    path: '',
    component: ChallengesListPage,
  },
  {
    path: 'create',
    component: CreateChallengePage,
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
