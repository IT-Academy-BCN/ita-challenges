import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ChallengeService } from '../../services/challenge.service';

@Component({
  selector: 'app-delete-challenge-page',
  imports: [ReactiveFormsModule],
  templateUrl: './delete-challenge-page.html',
  styleUrl: './delete-challenge-page.css',
})
export class DeleteChallengePage {

  readonly fb = inject(FormBuilder);
  challengeService = inject(ChallengeService)

  deleteChallengeForm = this.fb.group({
    challengeId: ['']
  })

  onSubmit(){
    const challengeId = this.deleteChallengeForm.value.challengeId ?? ''
    this.challengeService.delete(challengeId).subscribe({
      next(value) {
        console.log("OK")
      },
    })
  }
}
