import { Component } from '@angular/core';
import { IChallenge } from '../../models/ichallenge.interface';
import { CHALLENGES_MOCK } from '../../models/challenges.mock';

@Component({
  selector: 'app-challenge-detail-page',
  imports: [],
  templateUrl: './challenge-detail-page.html',
  styleUrl: './challenge-detail-page.css',
})
export class ChallengeDetailPage {

  challenge: IChallenge = CHALLENGES_MOCK[0];

}
