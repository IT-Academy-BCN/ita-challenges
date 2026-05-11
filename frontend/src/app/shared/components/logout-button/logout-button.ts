import { Component, inject } from '@angular/core';
import { AuthService } from '../../../features/auth/data-access/auth-service';

@Component({
  selector: 'app-logout-button',
  imports: [],
  templateUrl: './logout-button.html',
  styleUrl: './logout-button.css',
})
export class LogoutButton {

  private authService = inject(AuthService);

  logout(): void {
    this.authService.logout();
  }

}