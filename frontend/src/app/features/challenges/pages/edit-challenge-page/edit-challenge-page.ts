import { Component, inject, OnInit } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { ActivatedRoute, Router } from '@angular/router';
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
export class EditChallengePage implements OnInit {

  private readonly challengeService = inject(ChallengeService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
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
    solution: [''],
    difficulty: ['EASY'],
    language: ['']
  });

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.challengeService.getById(id).subscribe({
        next: (challenge) => {
          if (!challenge) return;

          this.editForm.patchValue({   
            id:          challenge.id,
            title:       challenge.title,
            description: challenge.description,
            solution:    challenge.solution,
            difficulty:  challenge.difficulty,
            language:    challenge.language,
          });
        },
        error: (error) => {
          console.error('Error loading challenge:', error);
        }
      });
    }
  }

  onSubmit() {

    const { id, title, description, difficulty, language, solution } = this.editForm.getRawValue();

    const challengePayload: IChallengeRequest = {
      title: title ?? '',
      description: description ?? '',
      solution: solution ?? '',
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
