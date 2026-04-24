import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';

@Component({
  selector: 'app-create-challenge-page',
  imports: [ReactiveFormsModule],
  templateUrl: './create-challenge-page.html',
  styleUrl: './create-challenge-page.css',
})
export class CreateChallengePage {

  readonly fb = inject(FormBuilder)

  challengeForm = this.fb.group({
    title: [''],
    description: ['']
  })

  onSubmit() {
    const newChallenge = this.challengeForm.value as IChallengeRequest
    console.log('Challenge created:', newChallenge)

}
}
