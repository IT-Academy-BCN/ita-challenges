import { Routes } from '@angular/router';
import { ChallengesListPage } from './pages/challenges-list-page/challenges-list-page';
import { EditChallengePage } from './pages/edit-challenge-page/edit-challenge-page';
import { CreateChallengePage } from './pages/create-challenge-page/create-challenge-page';
import { DeleteChallengePage } from './pages/delete-challenge-page/delete-challenge-page';
import { ChallengeDetailPage } from './pages/challenge-detail-page/challenge-detail-page';


export const CHALLENGES_ROUTES: Routes = [
  {
    path: '',
    component: ChallengesListPage,
  },
  {
    path: 'edit',
    component: EditChallengePage,
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
