import { Component, inject, signal } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { ActivatedRoute } from '@angular/router';
import { IChallenge } from '../../models/ichallenge.interface';
import { AuthUser } from '../../../auth/models/auth-user.model';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ChallengeApiService } from '../../data-access/challenge-api.service';
import { AuthService } from '../../../auth/data-access/auth-service';
import { IChallengeSubmission } from '../../models/ichallenge-submission.interface';
import { ChallengeLanguage } from '../../models/challenge-language.type';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';
import { RoleSelectorComponent } from '../../components/role-selector/role-selector';

interface AuthUserWithId extends AuthUser {
  id: string;
}

@Component({
  selector: 'app-challenge-detail-page',
  imports: [ReactiveFormsModule, RoleSelectorComponent],
  templateUrl: './challenge-detail-page.html',
  styleUrl: './challenge-detail-page.css',
})
export class ChallengeDetailPage {
  private readonly challengesService = inject(ChallengeService);
  private readonly route = inject(ActivatedRoute);
  private readonly challengeApiService = inject(ChallengeApiService)
  private readonly fb = inject(FormBuilder)
  private readonly authService = inject(AuthService)

  challenge = signal<IChallenge | undefined>(undefined);
  showModal = signal(false);
  isMentor = signal(false);
  programmingMode = signal(false);
  solutionRevealed = signal(false);

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

  codeSolutionForm = this.fb.group({
    code: ['']
  });
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.challengesService.getById(id).subscribe((selectedChallenge) => {
      this.challenge.set(selectedChallenge);
    });
  }

  getLanguageLabel(lang?: ChallengeLanguage): string {
    return lang ? this.languageLabels[lang] : '';
  }

  getDifficultyLabel(diff?: ChallengeDifficulty): string {
    return diff ? this.difficultyLabels[diff] : '';
  }


    saveSolution(): void {
      const currentChallenge = this.challenge();
      const currentUser = this.authService.user() as AuthUserWithId;

      if (this.codeSolutionForm.valid && currentChallenge?.id) {
        const challengeSolution : IChallengeSubmission = {
          challengeId: currentChallenge.id,
          userId: currentUser?.id || '550e8400-e29b-41d4-a716-446655440000',
          code: this.codeSolutionForm.value.code ?? '',
          revealOfficialSolution: false
          };

        this.challengeApiService.saveSolution(challengeSolution).subscribe({
        next: () => {
          alert('Solució guardada!');
        }
      });
    }
  }
  openFinishModal(): void {
      this.showModal.set(true);
    }

    closeModal(): void {
      this.showModal.set(false);
    }

    finishWithSolution(): void {
      this.closeModal();
      this.submitSolution(true);
    }

    finishWithoutSolution(): void {
      this.closeModal();
      this.submitSolution(false);
    }

   submitSolution(revealOfficialSolution: boolean): void {
      const currentChallenge = this.challenge();
      const currentUser = this.authService.user() as AuthUserWithId;

      if (this.codeSolutionForm.valid && currentChallenge?.id) {
        const challengeSolution: IChallengeSubmission = {
          challengeId: currentChallenge.id,
          userId: currentUser?.id || '550e8400-e29b-41d4-a716-446655440000',
          code: this.codeSolutionForm.value.code ?? '',
          revealOfficialSolution
        };
        if(revealOfficialSolution) { 
        this.challengeApiService.publishSolution(challengeSolution).subscribe({
          next: () => {
            alert('Solució enviada!');
            this.solutionRevealed.set(true);
          },
          error: (err) => {
            console.error('Error en enviar la solució:', err);
          }
        });
      }
    }
  }

  onRoleChange(value: boolean): void {
    this.isMentor.set(value);
  }

  setProgrammingMode(): void {
    this.programmingMode.set(true);
  }

}
