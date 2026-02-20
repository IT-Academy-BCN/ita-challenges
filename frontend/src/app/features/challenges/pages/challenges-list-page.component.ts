import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-challenges-list-page',
  imports: [RouterLink],
  template: `
    <h1>Challenges</h1>
    <p>List page (example).</p>

    <p>
      <a [routerLink]="['/challenges', 'demo-id-123']">Go to demo detail</a>
    </p>
  `,
})
export class ChallengesListPage {}
