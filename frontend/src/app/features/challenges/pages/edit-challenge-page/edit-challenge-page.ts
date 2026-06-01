import { Component, inject } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';
import { ChallengeLanguage } from '../../models/challenge-language.type';

@Component({
  selector: 'app-edit-challenge-page',
  imports: [ReactiveFormsModule],
  templateUrl: './edit-challenge-page.html',
  styleUrl: './edit-challenge-page.css',
})
export class EditChallengePage {

  private readonly challengeService = inject(ChallengeService);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  readonly difficulties: ChallengeDifficulty[] = ['EASY', 'MEDIUM', 'HARD'];
  readonly languages: ChallengeLanguage[] = [
    'JAVA',
    'PHP',
    'JAVASCRIPT',
    'TYPESCRIPT',
    'PYTHON',
    'SQL',
  ];

  editForm = this.fb.group({
    id: [''],
    title: [''],
    description: [''],
    difficulty: ['EASY'],
    language: ['']
  });

  onSubmit() {

    const { id, title, description, difficulty, language } = this.editForm.getRawValue();

    const challengePayload: IChallengeRequest = {
      title: title ?? '',
      description: description ?? '',
      difficulty: difficulty as ChallengeDifficulty,
      language: language as ChallengeLanguage
    };

    this.challengeService.update(id?? '', challengePayload).subscribe({
      next: () => {
        this.goChallenges()
      },
      error: (error) => {
        console.error('Error updating challenge:', error);
      },
    })
  }

  goChallenges() {
    this.router.navigate(['/challenges']);
  };

}
