import { Component, inject, OnInit, signal } from '@angular/core';
import { IChallenge } from '../../models/ichallenge.interface';
import { ChallengeService } from '../../services/challenge.service';
import { RoleSelectorComponent } from "../../components/role-selector/role-selector";

@Component({
  selector: 'app-challenges-list-page',
  standalone: true,
  imports: [RoleSelectorComponent],
  templateUrl: './challenges-list-page.html',
  styleUrl: './challenges-list-page.css',
})
export class ChallengesListPage implements OnInit {

  private readonly challengesService = inject(ChallengeService);
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
