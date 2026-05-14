import { computed, Injectable, signal } from '@angular/core';
import { Role } from '../models/role.model';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private readonly _role = signal<Role>('guest');

  role = this._role.asReadonly();

  isMentor = computed(() => this._role() === 'mentor');
  isStudent = computed(() => this._role() === 'student');
  isGuest = computed(() => this._role() === 'guest');

  setRole(role: Role): void {
    this._role.set(role);
  }
}
