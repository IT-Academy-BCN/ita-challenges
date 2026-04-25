import { Component, inject } from '@angular/core';
import { AuthService } from '../../../auth/data-access/auth-service';

@Component({
  selector: 'app-profile-page',
  imports: [],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css',
})
export class ProfilePageComponent {
  private readonly authService = inject(AuthService);
  public user = this.authService.user;
}
