import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../data-access/auth-service';

@Component({
  selector: 'app-auth-page',
  standalone: true,
  imports: [],
  templateUrl: './auth-page.html',
  styleUrl: './auth-page.css',
})
export class AuthPageComponent {
  private readonly authService = inject(AuthService);
  loading = signal(false);

  login(): void {
    if (this.loading()) return;

    this.loading.set(true);
    this.authService.loginWithGithub();
  }
}