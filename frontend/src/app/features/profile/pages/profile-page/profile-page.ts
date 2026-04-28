import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../auth/data-access/auth-service';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css',
})
export class ProfilePageComponent implements OnInit {
  private readonly authService = inject(AuthService);

  public user = this.authService.user;

  public loading = signal(true);
  public error = signal(false);

  ngOnInit(): void {
    const user = this.user();

    if (user) {
      this.loading.set(false);
      return;
    }

    this.authService.fetchUser().subscribe({
      next: (user) => {
        this.authService.setUser(user);
        this.loading.set(false);
      },
      error: () => {
        this.error.set(true);
        this.loading.set(false);
      },
    });
  }
}