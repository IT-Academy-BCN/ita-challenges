import { Routes } from '@angular/router';
import { CreateChallengePage } from './pages/create-challenge-page/create-challenge-page';
import { DeleteChallengePage } from './pages/delete-challenge-page/delete-challenge-page';
import { ChallengeDetailPage } from './pages/challenge-detail-page.component';
import { ChallengesListPage } from './pages/challenges-list-page/challenges-list-page';


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
