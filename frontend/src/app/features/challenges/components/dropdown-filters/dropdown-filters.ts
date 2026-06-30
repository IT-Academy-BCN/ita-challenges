import { Component, signal } from '@angular/core';
import { ChallengeDifficulty } from '../../models/challenge-difficulty.type';

@Component({
  selector: 'app-challenges-filter-dropdown',
  imports: [],
  templateUrl: './dropdown-filters.html',
  styleUrl: './dropdown-filters.css',
})
export class DropdownComponent {
  isOpen = signal(false);

  selectedDifficulty = signal<ChallengeDifficulty | null>(null);

  toggleDropdown(): void {
    this.isOpen.set(!this.isOpen());
  }

  closeDropdown(): void {
    this.isOpen.set(false);
  }

  selectDifficulty(level: ChallengeDifficulty): void {
    this.selectedDifficulty.set(level);
  }

  confirm(): void {
    this.closeDropdown();
  }
}
