import { Component, signal } from '@angular/core';

type Role = 'mentor' | 'student';

@Component({
  selector: 'app-role-selector',
  standalone: true,
  imports: [],
  templateUrl: './role-selector.html',
  styleUrl: './role-selector.css',
})
export class RoleSelectorComponent {
  private readonly _role = signal<Role>('student');

  currentRole = this._role.asReadonly();

  toggle(): void {
    this._role.set(
      this._role() === 'mentor'
        ? 'student'
        : 'mentor'
    );
  }
}