import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-challenge-page',
  imports: [ReactiveFormsModule],
  templateUrl: './create-challenge-page.html',
  styleUrl: './create-challenge-page.css',
})
export class CreateChallengePage {

  readonly challengeService = inject(ChallengeService)
  readonly router = inject(Router)
  readonly fb = inject(FormBuilder)

  challengeForm = this.fb.group({
    title: [''],
    description: ['']
  })

  onSubmit() {
    if (this.challengeForm.valid) {
      const newChallenge = this.challengeForm.value as IChallengeRequest

      this.challengeService.create(newChallenge).subscribe({
      next: () => {
        this.goChallenges();
      }
    });
    }
  }

  goChallenges() {
    this.router.navigate(['/challenges']);
  }
}
