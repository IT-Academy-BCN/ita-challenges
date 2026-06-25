import { Component, computed, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogoutButton } from '../../../shared/components/logout-button/logout-button';
import { AuthService } from '../../../features/auth/data-access/auth-service';
import {RoleTranslatorPipe} from '../../../shared/pipes/role-translator-pipe';

@Component({
  selector: 'app-header',
  imports: [LogoutButton, RoleTranslatorPipe],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  public user = computed(() => this.authService.user());

  ngOnInit(): void {
    if (this.authService.getUser()) return;

    this.authService.fetchUser().subscribe({
      next: (user) => {
        this.authService.setUser(user);
      },
    });
  }
}
