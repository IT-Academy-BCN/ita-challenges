import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-delete-challenge-page',
  imports: [ReactiveFormsModule],
  templateUrl: './delete-challenge-page.html',
  styleUrl: './delete-challenge-page.css',
})
export class DeleteChallengePage {

  readonly fb = inject(FormBuilder);

  deleteChallengeForm = this.fb.group({
    challengeId: ['']
  })

  onSubmit(){
    const challengeId = this.deleteChallengeForm.value.challengeId
    console.log(challengeId)
  }
}
