import { Component, inject, signal } from '@angular/core';
import { ChallengeService } from '../../services/challenge.service';
import { ActivatedRoute } from '@angular/router';
import { IChallenge } from '../../models/ichallenge.interface';

@Component({
  selector: 'app-challenge-detail-page',
  imports: [],
  templateUrl: './challenge-detail-page.html',
  styleUrl: './challenge-detail-page.css',
})
export class ChallengeDetailPage {

  private readonly challengesService = inject(ChallengeService);
  private readonly route = inject(ActivatedRoute)

  challenge = signal<IChallenge | undefined>(undefined);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.challengesService.getById(id).subscribe( (selectedChallenge) => {
      this.challenge.set(selectedChallenge)
    })
  }
}
