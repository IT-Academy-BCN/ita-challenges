import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delete-challenge-page',
  imports: [ReactiveFormsModule],
  templateUrl: './delete-challenge-page.html',
  styleUrl: './delete-challenge-page.css',
})
export class DeleteChallengePage {

  readonly fb = inject(FormBuilder);
  readonly challengeService = inject(ChallengeService)
  readonly router = inject(Router)

  deleteChallengeForm = this.fb.group({
    challengeId: ['']
  })

  onSubmit(){
    const challengeId = this.deleteChallengeForm.value.challengeId ?? ''
    this.challengeService.delete(challengeId).subscribe({
      next: (value) => {
        this.goChallenges()    
      },
    })
  }

  goChallenges(){
    this.router.navigate(['/challenges/'])
  }

}
