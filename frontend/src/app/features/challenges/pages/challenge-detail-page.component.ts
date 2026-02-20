import { Component, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-challenge-detail-page',
  imports: [RouterLink],
  template: `
    <h1>Challenge detail</h1>
    <p><strong>id:</strong> {{ id() }}</p>

    <p>
      <a routerLink="/challenges">Back to list</a>
    </p>
  `,
})
export class ChallengeDetailPage {
  private readonly route = inject(ActivatedRoute);

  readonly id = computed(() => this.route.snapshot.paramMap.get('id') ?? '');
}
