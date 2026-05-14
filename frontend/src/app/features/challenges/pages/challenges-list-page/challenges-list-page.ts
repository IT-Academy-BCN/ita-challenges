import { Component, inject, OnInit, signal } from '@angular/core';
import { IChallenge } from '../../models/ichallenge.interface';
import { ChallengeService } from '../../services/challenge.service';
import { RoleSelectorComponent } from "../../components/role-selector/role-selector";
import { CreateButtonComponent } from '../../components/buttons/create-button/create-button';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-challenges-list-page',
  imports: [CreateButtonComponent, RoleSelectorComponent, RouterLink],
  standalone: true,
  templateUrl: './challenges-list-page.html',
  styleUrl: './challenges-list-page.css',
})
export class ChallengesListPage implements OnInit {

  private readonly challengesService = inject(ChallengeService);

  challenges = signal<IChallenge[]>([]);
  isMentor = signal(false);

  ngOnInit(): void {
    this.loadChallenges();
  }

  loadChallenges(): void {
    this.challengesService.loadAll().subscribe({
      next: (result) => {
        this.challenges.set(result);
      }
    });
  }

  onRoleChange(value: boolean): void {
    this.isMentor.set(value);
  }
}
