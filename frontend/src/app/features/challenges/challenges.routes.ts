import { Routes } from '@angular/router';
import { ChallengesListPage } from './pages/challenges-list-page.component';
import { ChallengeDetailPage } from './pages/challenge-detail-page.component';
import { EditChallengePage } from './pages/edit-challenge-page/edit-challenge-page';
import { DeleteChallengePage } from './pages/delete-challenge-page/delete-challenge-page';

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
    path: 'delete',
    component: DeleteChallengePage,
  },
  {
    path: ':id',
    component: ChallengeDetailPage,
  },
];
