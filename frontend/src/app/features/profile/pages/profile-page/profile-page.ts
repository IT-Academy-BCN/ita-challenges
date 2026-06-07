import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../auth/data-access/auth-service';
import { LogoutButton } from '../../../../shared/components/logout-button/logout-button';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [LogoutButton],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css',
})
export class ProfilePageComponent implements OnInit {

  private readonly authService = inject(AuthService);

  public user = this.authService.user;

  public loading = signal(true);
  public error = signal(false);

  ngOnInit(): void {
    const user = this.authService.getUser();

        if (user) {
          this.loading.set(false);
          return;
        }

    this.authService.fetchUser().subscribe({
      next: (fetchedUser) => {
        this.authService.setUser(fetchedUser);
        this.loading.set(false);
      },
      error: () => {
        this.error.set(true);
        this.loading.set(false);
      }
    });
  }
}

