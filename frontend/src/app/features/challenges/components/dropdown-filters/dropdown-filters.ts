import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-challenges-filter-dropdown',
  imports: [],
  templateUrl: './dropdown-filters.html',
  styleUrl: './dropdown-filters.css',
})
export class DropdownComponent {
  isOpen = signal(false);

  toggleDropdown(): void {
    this.isOpen.set(!this.isOpen());
  }

  closeDropdown(): void {
    this.isOpen.set(false);
  }
}
