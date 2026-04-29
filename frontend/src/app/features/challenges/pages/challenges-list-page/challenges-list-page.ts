import { Component, inject, OnInit, signal } from '@angular/core';
import { IChallenge } from '../../models/ichallenge.interface';
import { ChallengeService } from '../../services/challenge.service';

@Component({
  selector: 'app-challenges-list-page',
  imports: [],
  templateUrl: './challenges-list-page.html',
  styleUrl: './challenges-list-page.css',
})
export class ChallengesListPage implements OnInit {

  challengesService = inject(ChallengeService);
  challenges = signal<IChallenge[]>([]);

  ngOnInit(): void {
    this.loadChallenges();
  }
  
  loadChallenges(){
    this.challengesService.loadAll().subscribe({
      next: (result) => {
        this.challenges.set(result);
      }
    });
  }
}
