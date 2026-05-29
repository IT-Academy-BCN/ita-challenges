import { Component, inject } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';


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

  editForm = this.fb.group({
    id: [''],
    title: [''],
    description: [''],
    solution: [''],
  });

  onSubmit() {

    const { id, title, description, solution} = this.editForm.getRawValue();

    const challengePayload: IChallengeRequest = {
      title: title ?? '',
      description: description ?? '',
      solution: solution ?? '',
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