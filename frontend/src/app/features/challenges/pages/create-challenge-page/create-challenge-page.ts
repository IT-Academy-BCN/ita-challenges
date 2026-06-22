import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { IChallengeRequest } from '../../models/ichallenge-request.interface';
import { ChallengeService } from '../../services/challenge.service';
import { Router } from '@angular/router';
import { ChallengeLanguage } from '../../models/challenge-language.type';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';
import { LanguageSelectButton } from '../../components/buttons/language-select-button/language-select-button';

@Component({
  selector: 'app-create-challenge-page',
  imports: [ReactiveFormsModule, LanguageSelectButton],
  templateUrl: './create-challenge-page.html',
  styleUrl: './create-challenge-page.css',
})
export class CreateChallengePage {

  readonly challengeService = inject(ChallengeService)
  readonly router = inject(Router)
  readonly fb = inject(FormBuilder)

  readonly selectedLanguage = signal<ChallengeLanguage | null>(null);
  readonly languages: ChallengeLanguage[] = ['JAVA', 'PHP', 'JAVASCRIPT', 'TYPESCRIPT', 'PYTHON', 'SQL'];
  readonly difficulties: ChallengeDifficulty[] = ['EASY', 'MEDIUM', 'HARD'];

  challengeForm = this.fb.group({
    title: [''],
    description: [''],
    language: [''],
    difficulty: [''],
    solution: [''],
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

  onLanguageSelect(language: ChallengeLanguage) {
    this.challengeForm.get('language')?.setValue(language);
    this.selectedLanguage.set(language);
  }
}
