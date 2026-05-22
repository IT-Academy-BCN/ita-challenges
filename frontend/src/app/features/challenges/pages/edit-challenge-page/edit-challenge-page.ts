import { Component, inject } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';
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
    language: [''],
  });

  onSubmit() {
    const { id, title, description, language } = this.editForm.getRawValue();

    const challengePayload: IChallengeRequest = {
      title: title ?? '',
      description: description ?? '',
      language: language as ChallengeLanguage,
    };

    this.challengeService.update(id ?? '', challengePayload).subscribe({
      next: () => {
        this.goChallenges();
      },
    });
  }

  goChallenges() {
    this.router.navigate(['/challenges']);
  }
}
