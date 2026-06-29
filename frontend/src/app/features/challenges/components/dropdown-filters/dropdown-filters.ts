import { Component, signal } from '@angular/core';

type Difficulty = 'easy' | 'mid' | 'hard';

@Component({
  selector: 'app-challenges-filter-dropdown',
  imports: [],
  templateUrl: './dropdown-filters.html',
  styleUrl: './dropdown-filters.css',
})
export class DropdownComponent {
  isOpen = signal(false);

  selectedDifficulty = signal<Difficulty | null>(null);

  toggleDropdown(): void {
    this.isOpen.set(!this.isOpen());
  }

  closeDropdown(): void {
    this.isOpen.set(false);
  }

  selectDifficulty(level: Difficulty): void { this.selectedDifficulty.set(level); }

  confirm(): void { this.closeDropdown(); }
}
