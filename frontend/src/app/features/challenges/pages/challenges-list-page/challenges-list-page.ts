import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ChallengeLanguage } from '../../models/challenge-language.type';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';
import { IChallenge } from '../../models/ichallenge.interface';
import { ChallengeService } from '../../services/challenge.service';
import { RoleSelectorComponent } from "../../components/role-selector/role-selector";
import { CreateButtonComponent } from '../../components/buttons/create-button/create-button';
import { DeleteButtonComponent } from "../../components/buttons/delete-button/delete-button";

@Component({
  selector: 'app-challenges-list-page',
  imports: [
    RouterLink,
    RoleSelectorComponent,
    CreateButtonComponent,
    DeleteButtonComponent
  ],
  standalone: true,
  templateUrl: './challenges-list-page.html',
  styleUrl: './challenges-list-page.css',
})
export class ChallengesListPage implements OnInit {

  private readonly challengesService = inject(ChallengeService);

  challenges = signal<IChallenge[]>([]);
  isMentor = signal(false);
  selectedLanguage = signal<ChallengeLanguage | null>(null);

  languageLabels: Record<ChallengeLanguage, string> = {
    JAVA: 'Java',
    PHP: 'PHP',
    JAVASCRIPT: 'JavaScript',
    TYPESCRIPT: 'TypeScript',
    PYTHON: 'Python',
    SQL: 'SQL',
  };

  difficultyLabels: Record<ChallengeDifficulty, string> = {
    EASY: 'Fàcil',
    MEDIUM: 'Mitjana',
    HARD: 'Difícil',
  };

  ngOnInit(): void {
    this.loadChallenges();
  }

  getLanguageLabel(lang?: ChallengeLanguage): string {
    return lang ? this.languageLabels[lang] : '';
  }

  getDifficultyLabel(diff?: ChallengeDifficulty): string {
    return diff ? this.difficultyLabels[diff] : '';
  }

  loadChallenges(language?: ChallengeLanguage | null): void {
    this.challengesService.loadAll(language).subscribe({
      next: (result) => {
        this.challenges.set(result);
      }
    });
  }

  onRoleChange(value: boolean): void {
    this.isMentor.set(value);
  }

  onLanguageChipClick(lang: ChallengeLanguage): void {
    const next = this.selectedLanguage() === lang ? null : lang;
    this.selectedLanguage.set(next);
    
    this.loadChallenges(next);
  }

  handleDelete(id: string): void {
    this.challengesService.delete(id).subscribe({
      next: () => {
        this.challenges.update((current) =>
          current.filter((challenge) => challenge.id !== id)
        );
      },
      error: (err) => {
        console.error('Error deleting challenge', err);
      }
    });
  }

  getDifficultyClass(diff?: ChallengeDifficulty): string {
    return diff ? `tag tag--difficulty-${diff.toLowerCase()}` : 'tag';
  }
}
