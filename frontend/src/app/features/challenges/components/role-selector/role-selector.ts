import { Component, output, signal } from '@angular/core';

@Component({
  selector: 'app-role-selector',
  standalone: true,
  imports: [],
  templateUrl: './role-selector.html',
  styleUrl: './role-selector.css',
})
export class RoleSelectorComponent {

  isMentor = signal(false);

  roleChange = output<boolean>();

  toggle(): void {
    this.isMentor.update( currentRole => !currentRole);
    this.roleChange.emit(this.isMentor());
  }
}