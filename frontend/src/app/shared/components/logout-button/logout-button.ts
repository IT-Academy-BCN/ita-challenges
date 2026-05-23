import { Component, inject } from '@angular/core';
import { AuthService } from '../../../features/auth/data-access/auth-service';

@Component({
  selector: 'app-logout-button',
  imports: [],
  templateUrl: './logout-button.html',
  styleUrl: './logout-button.css',
})
export class LogoutButton {

  private readonly authService = inject(AuthService);

  logout(): void {
    this.authService.logout().subscribe({
      error: (error) => {
        console.error('Error durante el proceso de logout:', error);
      }
    });
  }
}
