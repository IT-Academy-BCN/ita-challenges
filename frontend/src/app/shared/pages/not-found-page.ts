import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-not-found-page',
  imports: [RouterLink],
  template: `
    <h1>404 - Not Found</h1>
    <p>La página que buscas no existe.</p>

    <a routerLink="/challenges">Volver a Challenges</a>
  `,
})
export class NotFoundPage {}
