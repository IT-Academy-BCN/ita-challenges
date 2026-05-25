import { Component, inject, signal } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { ActivatedRoute } from '@angular/router';
import { IChallenge } from '../../models/ichallenge.interface';
import { AuthUser } from '../../../auth/models/auth-user.model';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ChallengeApiService } from '../../data-access/challenge-api.service';
import { AuthService } from '../../../auth/data-access/auth-service';
import { IChallengeSubmission } from '../../models/ichallenge-submission.interface';

interface AuthUserWithId extends AuthUser {
  id: string;
}

@Component({
  selector: 'app-challenge-detail-page',
  imports: [ReactiveFormsModule],
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

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.challengesService.getById(id).subscribe((selectedChallenge) => {
      this.challenge.set(selectedChallenge);
    });
  }

  codeSolutionForm = this.fb.group({
      code: ['']
    })

    saveSolution(): void {
      const currentChallenge = this.challenge();
      const currentUser = this.authService.user() as AuthUserWithId;

      if (this.codeSolutionForm.valid && currentChallenge?.id) {
        const challengeSolution : IChallengeSubmission = {
          challengeId: currentChallenge.id,
          userId: currentUser?.id || '550e8400-e29b-41d4-a716-446655440000',
          code: this.codeSolutionForm.value.code ?? ''
          };

        this.challengeApiService.saveSolution(challengeSolution).subscribe({
        next: () => {
          alert('Solució guardada!');
        }
      });
      }
    }
}
