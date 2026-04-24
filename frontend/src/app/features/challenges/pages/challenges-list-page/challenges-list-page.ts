import { Component } from '@angular/core';
import { IChallenge } from '../../models/ichallenge.interface';
import { CHALLENGES_MOCK } from '../../models/challenges.mock';

@Component({
  selector: 'app-challenges-list-page',
  imports: [],
  templateUrl: './challenges-list-page.html',
  styleUrl: './challenges-list-page.css',
})
export class ChallengesListPage {

  challenges: IChallenge[] = CHALLENGES_MOCK;
}
