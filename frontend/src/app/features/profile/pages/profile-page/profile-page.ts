import { Component, inject, OnInit, signal } from '@angular/core';
import { AuthService } from '../../../auth/data-access/auth-service';

@Component({
  selector: 'app-profile-page',
  imports: [],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css',
})
export class ProfilePageComponent implements OnInit {
  private readonly authService = inject(AuthService);
  
  public user = this.authService.user;
  error = signal(false);

  ngOnInit(): void {
    this.authService.fetchUser().subscribe({
      next: (user) => this.authService.setUser(user),
      error: () => this.error.set(true),
    });
  }
}