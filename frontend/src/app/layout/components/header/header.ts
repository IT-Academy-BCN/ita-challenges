import { Component, inject } from '@angular/core';
import { LogoutButton } from '../../../shared/components/logout-button/logout-button';
import { AuthService } from '../../../features/auth/data-access/auth-service';

@Component({
  selector: 'app-header',
  imports: [LogoutButton],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  private readonly authService = inject(AuthService);

  public user = this.authService.user;
}
