import { Component, inject } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';

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

  editForm = this.fb.group({
    id: [''],
    title: [''],
    description: [''],
    difficulty: ['EASY'],
  });

  onSubmit() {

    const { id, title, description, difficulty} = this.editForm.getRawValue();

    const challengePayload: IChallengeRequest = {
      title: title ?? '',
      description: description ?? '',
      difficulty: difficulty as ChallengeDifficulty,
    };

    this.challengeService.update(id?? '', challengePayload).subscribe({
      next: () => {
        this.goChallenges()
      },
    });
  };

  goChallenges() {
    this.router.navigate(['/challenges']);
  };

}