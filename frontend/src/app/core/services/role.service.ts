import { computed, Injectable, signal } from '@angular/core';
import { Role } from '../models/role.model';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private readonly _role = signal<Role>('student');

  role = this._role.asReadonly();
  isMentor = computed(() => this._role() === 'mentor');

  setRole(role: Role): void {
    this._role.set(role);
  }
}
